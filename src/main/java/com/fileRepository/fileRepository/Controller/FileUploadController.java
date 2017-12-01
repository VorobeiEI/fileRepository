package com.fileRepository.fileRepository.Controller;

import com.fileRepository.fileRepository.entity.File;
import com.fileRepository.fileRepository.entity.User;
import com.fileRepository.fileRepository.reposervoceimpl.UserRepoServiceImpl;
import com.fileRepository.fileRepository.repository.FileRepository;
import com.fileRepository.fileRepository.service.FileService;
import com.fileRepository.fileRepository.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    private final StorageService storageService;
    private FileRepository fileService;
    private UserRepoServiceImpl userServicel;

    @Autowired
    public FileUploadController(StorageService storageService, FileRepository fileService, UserRepoServiceImpl userServicel) {
        this.storageService = storageService;
        this.fileService = fileService;
        this.userServicel = userServicel;
    }

    @GetMapping
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }


    public String getUrl(MultipartFile file) {
        List<String> list = storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString()
        ).collect(Collectors.toList());

        String temp = null;

        for (String s : list) {
            if (s.contains(file.getOriginalFilename())) {
                temp = s;
            }
        }

        return temp;
    }

    @PostMapping
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes
            redirectAttributes) throws IOException {
        storageService.store(file);
        File fileForDB = new File();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User tempUser = userServicel.findByUsername(authentication.getName());
        tempUser.addFile(fileForDB);
        fileForDB.setLink(getUrl(file));
        fileService.save(fileForDB);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/upload";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadRecource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }
}
