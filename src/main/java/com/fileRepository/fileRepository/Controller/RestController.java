package com.fileRepository.fileRepository.Controller;

import com.fileRepository.fileRepository.entity.File;
import com.fileRepository.fileRepository.entity.User;
import com.fileRepository.fileRepository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/userFiles")
public class RestController {
    private UserService userService;

    @Autowired
    public RestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public List<File>  showUserFiles(@PathVariable Long id) {
        User user = userService.findOneById(id);
        return user.getUserFiles();
    }


}
