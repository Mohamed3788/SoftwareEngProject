package com.university.lms.StudyBuddy.module.service;

import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.course.repository.CourseRepository;
import com.university.lms.StudyBuddy.file.FileStorageService;
import com.university.lms.StudyBuddy.module.dto.*;
import com.university.lms.StudyBuddy.module.model.*;
import com.university.lms.StudyBuddy.module.repository.*;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CourseModuleService {

    private final CourseRepository courseRepository;
    private final CourseModuleRepository moduleRepository;
    private final ModuleItemRepository itemRepository;

    private final FileStorageService fileStorageService;

    public CourseModuleService(CourseRepository courseRepository, CourseModuleRepository moduleRepository, ModuleItemRepository itemRepository, FileStorageService fileStorageService) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.itemRepository = itemRepository;
        this.fileStorageService = fileStorageService;
    }

    // ----------------
    // CREATE MODULE
    // ----------------
    public CourseModuleResponse createModule(CreateModuleRequest req, User teacher) {

        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("You are not the teacher of this course");
        }

        CourseModule module = CourseModule.builder()
                .title(req.getTitle())
                .course(course)
                .build();

        module = moduleRepository.save(module);

        return toModuleResponse(module);
    }

    // ----------------
    // ADD ITEM (old JSON-only version)
    // ----------------
    public ModuleItemResponse addItem(Long moduleId, AddModuleItemRequest req, User teacher) {

        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        if (!module.getCourse().getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("Not your course");
        }

        int nextPos = itemRepository.findByModuleOrderByPositionAsc(module).size();

        ModuleItem item = ModuleItem.builder()
                .module(module)
                .type(req.getType())
                .content(req.getContent())
                .fileUrl(req.getFileUrl())
                .position(nextPos)
                .build();

        item = itemRepository.save(item);

        return toItemResponse(item);
    }

    // ----------------
    // ADD ITEM WITH FILE
    // ----------------
    public ModuleItemResponse addItemFile(
            Long moduleId,
            ModuleItemType type,
            String content,
            MultipartFile file,
            User teacher
    ) {

        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        if (!module.getCourse().getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("Not your course");
        }

        int nextPos = itemRepository.findByModuleOrderByPositionAsc(module).size();

        String fileUrl = null;

        if (type == ModuleItemType.FILE) {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is required for FILE type module item");
            }
            fileUrl = fileStorageService.saveFile(file);
        }

        ModuleItem item = ModuleItem.builder()
                .module(module)
                .type(type)
                .content(type == ModuleItemType.TEXT ? content : null)
                .fileUrl(fileUrl)
                .position(nextPos)
                .build();

        item = itemRepository.save(item);

        return toItemResponse(item);
    }

    // ----------------
    // STUDENT VIEW MODULES
    // ----------------
    public List<CourseModuleResponse> studentViewModules(Long courseId, User student) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (student.getRole() != Role.STUDENT ||
                student.getStudentClass() != course.getAssignedClass()) {
            throw new IllegalArgumentException("You are not enrolled in this course");
        }

        return moduleRepository.findByCourseOrderByIdAsc(course)
                .stream().map(this::toModuleResponse).toList();
    }

    // ----------------
    // TEACHER VIEW MODULES
    // ----------------
    public List<CourseModuleResponse> teacherViewModules(Long courseId, User teacher) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("Not your course");
        }

        return moduleRepository.findByCourseOrderByIdAsc(course)
                .stream().map(this::toModuleResponse).toList();
    }

    // ----------------
    // DTO MAPPERS
    // ----------------
    private CourseModuleResponse toModuleResponse(CourseModule m) {
        List<ModuleItemResponse> items = itemRepository.findByModuleOrderByPositionAsc(m)
                .stream().map(this::toItemResponse).toList();

        return CourseModuleResponse.builder()
                .id(m.getId())
                .title(m.getTitle())
                .items(items)
                .build();
    }

    private ModuleItemResponse toItemResponse(ModuleItem i) {
        return ModuleItemResponse.builder()
                .id(i.getId())
                .type(i.getType())
                .content(i.getContent())
                .fileUrl(i.getFileUrl())
                .position(i.getPosition())
                .build();
    }
}
