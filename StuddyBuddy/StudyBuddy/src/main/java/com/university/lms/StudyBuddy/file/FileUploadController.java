package com.university.lms.StudyBuddy.file;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileStorageService storageService;

    public FileUploadController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    // any authenticated user can upload (teacher/student)
    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal Object user // not used here
    ) {
        String url = storageService.saveFile(file);
        return ResponseEntity.ok(url);
    }
}
