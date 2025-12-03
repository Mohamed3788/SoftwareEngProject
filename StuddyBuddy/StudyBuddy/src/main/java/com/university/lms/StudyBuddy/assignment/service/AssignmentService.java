package com.university.lms.StudyBuddy.assignment.service;

import com.university.lms.StudyBuddy.assignment.dto.*;
import com.university.lms.StudyBuddy.assignment.model.Assignment;
import com.university.lms.StudyBuddy.assignment.model.AssignmentSubmission;
import com.university.lms.StudyBuddy.assignment.repository.AssignmentRepository;
import com.university.lms.StudyBuddy.assignment.repository.AssignmentSubmissionRepository;
import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.course.repository.CourseRepository;
import com.university.lms.StudyBuddy.dashboard.model.EventType;
import com.university.lms.StudyBuddy.dashboard.service.DashboardEventService;
import com.university.lms.StudyBuddy.file.FileStorageService;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final DashboardEventService eventService;
    private final FileStorageService fileStorageService;

    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentSubmissionRepository submissionRepository, CourseRepository courseRepository, UserRepository userRepository, DashboardEventService eventService, FileStorageService fileStorageService) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.fileStorageService = fileStorageService;
    }

    // ----------------------------------------------------
    // CREATE ASSIGNMENT (Teacher)
    // ----------------------------------------------------
    public AssignmentResponse createAssignment(CreateAssignmentRequest req, User teacher) {

        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("You are not the teacher of this course");
        }

        Assignment assignment = Assignment.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .dueDate(req.getDueDate())
                .course(course)
                .createdBy(teacher)
                .build();

        assignment = assignmentRepository.save(assignment);

        // Notify all students
        List<User> students = userRepository.findByStudentClass(course.getAssignedClass());
        Assignment finalAssignment = assignment;

        students.forEach(s -> eventService.pushEventToUser(
                course.getId(),
                finalAssignment.getId(),
                s,
                EventType.NEW_ASSIGNMENT,
                "New assignment in " + course.getTitle() + ": " + finalAssignment.getTitle()
        ));

        return AssignmentResponse.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .dueDate(assignment.getDueDate())
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .createdBy(teacher.getId())
                .build();
    }

    // ----------------------------------------------------
    // LIST ASSIGNMENTS FOR STUDENT
    // ----------------------------------------------------
    public List<AssignmentResponse> listAssignmentsForStudent(User student) {

        return courseRepository.findByAssignedClass(student.getStudentClass())
                .stream()
                .flatMap(c -> assignmentRepository.findByCourse(c).stream())
                .map(a -> AssignmentResponse.builder()
                        .id(a.getId())
                        .title(a.getTitle())
                        .description(a.getDescription())
                        .dueDate(a.getDueDate())
                        .courseId(a.getCourse().getId())
                        .courseTitle(a.getCourse().getTitle())
                        .createdBy(a.getCreatedBy().getId())
                        .build())
                .toList();
    }

    // ----------------------------------------------------
    // LIST ASSIGNMENTS FOR TEACHER (for one course)
    // ----------------------------------------------------
    public List<AssignmentResponse> listAssignmentsForTeacher(Long courseId, User teacher) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("Not your course");
        }

        return assignmentRepository.findByCourse(course)
                .stream()
                .map(a -> AssignmentResponse.builder()
                        .id(a.getId())
                        .title(a.getTitle())
                        .description(a.getDescription())
                        .dueDate(a.getDueDate())
                        .courseId(course.getId())
                        .courseTitle(course.getTitle())
                        .createdBy(a.getCreatedBy().getId())
                        .build())
                .toList();
    }

    // ----------------------------------------------------
    // STUDENT SUBMISSION (TEXT / LINK SUBMISSION)
    // ----------------------------------------------------
    public SubmissionResponse submitAssignment(Long assignmentId, SubmitAssignmentRequest req, User student) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        if (!student.getStudentClass().equals(assignment.getCourse().getAssignedClass())) {
            throw new IllegalArgumentException("You are not in this course's class");
        }

        AssignmentSubmission submission =
                submissionRepository.findByAssignmentAndStudent(assignment, student)
                        .orElse(AssignmentSubmission.builder()
                                .assignment(assignment)
                                .student(student)
                                .build());

        submission.setContent(req.getContent());
        submission.setSubmittedAt(LocalDateTime.now());

        submission = submissionRepository.save(submission);

        // Notify teacher
        eventService.pushEventToUser(
                assignment.getCourse().getId(),
                submission.getId(),
                assignment.getCourse().getTeacher(),
                EventType.NEW_SUBMISSION,
                "New submission from " + student.getFirstName() + " in " + assignment.getCourse().getTitle()
        );

        return toSubmissionResponse(submission);
    }

    // ----------------------------------------------------
    // STUDENT SUBMISSION (FILE SUBMISSION)
    // ----------------------------------------------------
    public SubmissionResponse submitAssignmentFile(Long assignmentId, MultipartFile file, User student) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        if (!student.getStudentClass().equals(assignment.getCourse().getAssignedClass())) {
            throw new IllegalArgumentException("You are not in this course's class");
        }

        String fileUrl = fileStorageService.saveFile(file);

        AssignmentSubmission submission =
                submissionRepository.findByAssignmentAndStudent(assignment, student)
                        .orElse(AssignmentSubmission.builder()
                                .assignment(assignment)
                                .student(student)
                                .build());

        submission.setContent(fileUrl);
        submission.setSubmittedAt(LocalDateTime.now());

        submission = submissionRepository.save(submission);

        // Notify teacher
        eventService.pushEventToUser(
                assignment.getCourse().getId(),
                submission.getId(),
                assignment.getCourse().getTeacher(),
                EventType.NEW_SUBMISSION,
                "New submission from " + student.getFirstName() + " in " + assignment.getCourse().getTitle()
        );

        return toSubmissionResponse(submission);
    }

    // ----------------------------------------------------
    // TEACHER VIEW SUBMISSIONS FOR ASSIGNMENT
    // ----------------------------------------------------
    public List<SubmissionResponse> listSubmissionsForAssignment(Long assignmentId, User teacher) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        if (!assignment.getCourse().getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("Not your assignment");
        }

        return submissionRepository.findByAssignment(assignment)
                .stream()
                .map(this::toSubmissionResponse)
                .toList();
    }

    // ----------------------------------------------------
    // TEACHER GRADES SUBMISSION
    // ----------------------------------------------------
    public SubmissionResponse gradeSubmission(Long submissionId, GradeRequest req, User teacher) {

        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found"));

        Assignment assignment = submission.getAssignment();

        if (!assignment.getCourse().getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("You are not this course's teacher");
        }

        submission.setGrade(req.getGrade());
        submission.setFeedback(req.getFeedback());

        submission = submissionRepository.save(submission);

        // Notify the student
        eventService.pushEventToUser(
                assignment.getCourse().getId(),
                submission.getId(),
                submission.getStudent(),
                EventType.NEW_GRADE,
                "You received a new grade in " + assignment.getCourse().getTitle()
        );

        return toSubmissionResponse(submission);
    }

    // ----------------------------------------------------
    // INTERNAL MAPPER
    // ----------------------------------------------------
    private SubmissionResponse toSubmissionResponse(AssignmentSubmission s) {
        return SubmissionResponse.builder()
                .id(s.getId())
                .content(s.getContent())
                .submittedAt(s.getSubmittedAt())
                .studentName(s.getStudent().getFirstName() + " " + s.getStudent().getLastName())
                .grade(s.getGrade())
                .feedback(s.getFeedback())
                .build();
    }

    // --------------------------------------
// STUDENT: GET ALL GRADES
// --------------------------------------
    public List<SubmissionResponse> listGradesForStudent(User student) {
        List<AssignmentSubmission> submissions =
                submissionRepository.findByStudent(student);

        return submissions.stream()
                .filter(s -> s.getGrade() != null)
                .map(this::toSubmissionResponse)
                .toList();
    }

    // --------------------------------------
// TEACHER: GET ALL GRADES FOR A COURSE
// --------------------------------------
    public List<SubmissionResponse> listGradesForCourse(Long courseId, User teacher) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("Not your course");
        }

        List<Assignment> assignments = assignmentRepository.findByCourse(course);

        List<AssignmentSubmission> submissions =
                assignments.stream()
                        .flatMap(a -> submissionRepository.findByAssignment(a).stream())
                        .filter(s -> s.getGrade() != null) // only graded work
                        .toList();

        return submissions.stream()
                .map(this::toSubmissionResponse)
                .toList();
    }

    public List<StudentGradeItemResponse> getGradesForStudentInCourse(Long courseId, User student) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Verify student is enrolled
        if (student.getRole() != Role.STUDENT || 
            !student.getStudentClass().equals(course.getAssignedClass())) {
            throw new IllegalArgumentException("You are not enrolled in this course");
        }

        // Get all assignments for this course
        List<Assignment> assignments = assignmentRepository.findByCourse(course);

        return assignments.stream()
                .map(assignment -> {
                    // Find submission for this student and assignment
                    AssignmentSubmission submission = submissionRepository
                            .findByAssignmentAndStudent(assignment, student)
                            .orElse(null);

                    // Determine status
                    String status;
                    if (submission == null) {
                        status = "NOT_SUBMITTED";
                    } else if (submission.getGrade() != null) {
                        status = "GRADED";
                    } else {
                        status = "SUBMITTED";
                    }

                    return StudentGradeItemResponse.builder()
                            .assignmentId(assignment.getId())
                            .assignmentTitle(assignment.getTitle())
                            .assignmentDescription(assignment.getDescription())
                            .dueDate(assignment.getDueDate())
                            .submissionId(submission != null ? submission.getId() : null)
                            .submittedAt(submission != null ? submission.getSubmittedAt() : null)
                            .grade(submission != null ? submission.getGrade() : null)
                            .feedback(submission != null ? submission.getFeedback() : null)
                            .status(status)
                            .build();
                })
                .toList();
    }
}
