package com.university.lms.StudyBuddy.module.controller;

import com.university.lms.StudyBuddy.module.dto.AddModuleItemRequest;
import com.university.lms.StudyBuddy.module.dto.CourseModuleResponse;
import com.university.lms.StudyBuddy.module.dto.CreateModuleRequest;
import com.university.lms.StudyBuddy.module.dto.ModuleItemResponse;
import com.university.lms.StudyBuddy.module.model.ModuleItemType;
import com.university.lms.StudyBuddy.module.service.CourseModuleService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/modules")
public class TeacherModuleController {

    private final CourseModuleService service;

    public TeacherModuleController(CourseModuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CourseModuleResponse> createModule(
            @RequestBody CreateModuleRequest req,
            @AuthenticationPrincipal User teacher) {
        return ResponseEntity.ok(service.createModule(req, teacher));
    }

    @PostMapping(value = "/{moduleId}/items", consumes = { "multipart/form-data" })
    public ResponseEntity<ModuleItemResponse> addItem(
            @PathVariable Long moduleId,
            @RequestPart(required = false) MultipartFile file,
            @RequestParam("type") String typeStr,
            @RequestParam("content") String content,
            @AuthenticationPrincipal User teacher) {
        // Convert string to enum
        ModuleItemType type;
        try {
            type = ModuleItemType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid module item type: " + typeStr);
        }

        return ResponseEntity.ok(service.addItemFile(moduleId, type, content, file, teacher));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<List<CourseModuleResponse>> viewModules(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User teacher) {
        return ResponseEntity.ok(service.teacherViewModules(courseId, teacher));
    }
}
