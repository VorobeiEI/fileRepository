package com.fileRepository.fileRepository.Controller;

import com.fileRepository.fileRepository.entity.User;
import com.fileRepository.fileRepository.service.SecurityService;
import com.fileRepository.fileRepository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private UserService userService;
    private SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {

        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/user")
    public String register(Model model) {
        return "uploadForm";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, Model model) {

        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/user";
    }

    @GetMapping("/login")
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid." +
                    "Please go to registration page, or try other credentials.");
        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "uploadForm";
    }
}
