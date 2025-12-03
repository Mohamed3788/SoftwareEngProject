# StudyBuddy Backend - Issues Report

## CRITICAL SECURITY ISSUES

### 1. **Hardcoded Database Password in application.yml**
- **Location**: `src/main/resources/application.yml:13`
- **Issue**: Database password `Simo@database99` is hardcoded in source code
- **Risk**: Exposed in version control, anyone with access can see credentials
- **Fix**: Use environment variables or Spring profiles with externalized configuration

### 2. **Hardcoded JWT Secret Key**
- **Location**: `src/main/resources/application.yml:30`
- **Issue**: JWT secret `my-super-secret-key-that-should-be-long-and-random-1234567890` is hardcoded
- **Risk**: If compromised, all tokens can be forged
- **Fix**: Use environment variable or secure key management service

### 3. **Hardcoded Admin Password**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/auth/service/AuthService.java:23`
- **Issue**: Admin secret `@dmin` is hardcoded and extremely weak
- **Risk**: Anyone can create admin accounts
- **Fix**: Use environment variable or proper admin registration flow

### 4. **Insecure CORS Configuration**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/auth/controller/AuthController.java:16`
- **Issue**: `@CrossOrigin(origins = "*")` allows all origins
- **Risk**: Allows any website to make requests to your API
- **Fix**: Specify allowed origins explicitly

### 5. **Insecure WebSocket CORS**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/config/WebSocketConfig.java:14`
- **Issue**: `.setAllowedOriginPatterns("*")` allows all origins
- **Risk**: Any website can connect to WebSocket endpoints
- **Fix**: Specify allowed origin patterns

### 6. **File Upload Path Traversal Vulnerability**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/file/FileStorageService.java:22`
- **Issue**: `file.getOriginalFilename()` is used directly without sanitization
- **Risk**: Attackers can upload files with paths like `../../../etc/passwd` to write outside upload directory
- **Fix**: Sanitize filename, remove path components, validate extension

### 7. **No File Type Validation**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/file/FileStorageService.java:17`
- **Issue**: No validation of file types or content
- **Risk**: Malicious files (executables, scripts) can be uploaded
- **Fix**: Validate MIME types and file extensions, scan for malicious content

### 8. **No File Size Validation in Code**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/file/FileStorageService.java:17`
- **Issue**: Only configured in YAML, no programmatic validation
- **Risk**: Large files could cause DoS
- **Fix**: Add explicit size checks before processing

### 9. **JWT Secret Key Handling**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/auth/service/JwtService.java:75`
- **Issue**: `jwtSecret.getBytes()` assumes UTF-8, should use proper key derivation
- **Risk**: Inconsistent key encoding could cause issues
- **Fix**: Use `Decoders.BASE64.decode()` if secret is base64, or ensure proper encoding

### 10. **Deprecated JWT API Usage**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/auth/service/JwtService.java:43`
- **Issue**: `SignatureAlgorithm.HS256` is deprecated in newer jjwt versions
- **Risk**: May break with future library updates
- **Fix**: Remove explicit algorithm, let the library choose based on key type

## CONFIGURATION ISSUES

### 11. **Invalid Spring Boot Version**
- **Location**: `pom.xml:8`
- **Issue**: Spring Boot version `4.0.0` doesn't exist (latest stable is 3.x)
- **Risk**: Build will fail
- **Fix**: Use valid version like `3.2.0` or `3.3.0`

### 12. **Invalid Java Version**
- **Location**: `pom.xml:30`
- **Issue**: Java version `25` doesn't exist (latest is Java 21)
- **Risk**: Build will fail
- **Fix**: Use Java 17 or 21

### 13. **Invalid Test Dependencies**
- **Location**: `pom.xml:62-78`
- **Issue**: Dependencies like `spring-boot-starter-data-jpa-test` don't exist
- **Risk**: Build will fail
- **Fix**: Use correct test dependencies: `spring-boot-starter-test` includes all needed test dependencies

### 14. **Duplicate Resource Handler Configuration**
- **Location**: 
  - `src/main/java/com/university/lms/StudyBuddy/config/WebMvcConfig.java:16` (handles `/uploads/**`)
  - `src/main/java/com/university/lms/StudyBuddy/file/FileServeConfig.java:11` (handles `/files/**`)
- **Issue**: Two different handlers for similar purposes, inconsistent paths
- **Risk**: Confusion, potential conflicts
- **Fix**: Consolidate into one configuration

### 15. **Inconsistent Upload Directory Configuration**
- **Location**: 
  - `application.yml:34` uses `file.upload-dir: uploads`
  - `FileStorageService.java:14` uses `app.upload.dir:uploads`
- **Issue**: Property mismatch will cause runtime errors
- **Risk**: File uploads will fail
- **Fix**: Use consistent property names

### 16. **Hibernate DDL Auto Update in Production**
- **Location**: `src/main/resources/application.yml:18`
- **Issue**: `ddl-auto: update` should not be used in production
- **Risk**: Schema changes can cause data loss
- **Fix**: Use `validate` or `none` in production, `update` only in development

## CODE QUALITY ISSUES

### 17. **No Global Exception Handler**
- **Location**: Entire codebase
- **Issue**: No `@ControllerAdvice` or `@ExceptionHandler` for consistent error responses
- **Risk**: Exceptions return raw stack traces, poor user experience
- **Fix**: Create global exception handler with proper error DTOs

### 18. **Generic Exception Messages**
- **Location**: Multiple service classes
- **Issue**: Using `IllegalArgumentException` for business logic errors
- **Risk**: Poor error handling, hard to distinguish error types
- **Fix**: Create custom exceptions (e.g., `CourseNotFoundException`, `UnauthorizedException`)

### 19. **Incomplete Implementation**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/assignment/service/AssignmentService.java:301-302`
- **Issue**: Method `getGradesForStudentInCourse` always returns `null`
- **Risk**: NullPointerException when called
- **Fix**: Implement the method properly

### 20. **Unused Import**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/file/FileStorageService.java:7`
- **Issue**: `import java.io.File;` is imported but never used
- **Risk**: Code clutter
- **Fix**: Remove unused import

### 21. **Wrong Nullable Annotation**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/assignment/service/AssignmentService.java:17`
- **Issue**: Uses `org.jspecify.annotations.Nullable` instead of standard `jakarta.annotation.Nullable` or `org.springframework.lang.Nullable`
- **Risk**: Non-standard dependency
- **Fix**: Use standard nullable annotation

### 22. **Duplicate Security Rule**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/config/SecurityConfig.java:27,38`
- **Issue**: `/api/admin/**` is defined twice with same rule
- **Risk**: Redundant configuration
- **Fix**: Remove duplicate

### 23. **Missing Validation on GradeRequest**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/assignment/controller/TeacherAssignmentController.java:35`
- **Issue**: `GradeRequest` parameter not validated with `@Valid`
- **Risk**: Invalid data can be submitted
- **Fix**: Add `@Valid` annotation

### 24. **Unused Authentication Principal**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/file/FileUploadController.java:20`
- **Issue**: `@AuthenticationPrincipal Object user` is declared but never used
- **Risk**: No user-based access control on file uploads
- **Fix**: Use proper User type and implement access control

### 25. **No Input Sanitization**
- **Location**: Multiple controllers accepting user input
- **Issue**: No sanitization of user-provided strings (XSS risk in stored content)
- **Risk**: Stored XSS if content is displayed without escaping
- **Fix**: Sanitize HTML/script content before storing

### 26. **Potential NullPointerException**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/file/FileStorageService.java:22`
- **Issue**: `file.getOriginalFilename()` can return `null`
- **Risk**: NullPointerException when concatenating
- **Fix**: Handle null case

### 27. **No Transaction Management**
- **Location**: Service classes
- **Issue**: No `@Transactional` annotations on service methods that modify data
- **Risk**: Data inconsistency on failures
- **Fix**: Add `@Transactional` to write operations

### 28. **Error Messages Expose Internal Details**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/file/FileStorageService.java:30`
- **Issue**: Exception message includes original exception message
- **Risk**: Information leakage about internal system
- **Fix**: Use generic error messages, log details separately

### 29. **Missing Password Strength Validation**
- **Location**: `src/main/java/com/university/lms/StudyBuddy/auth/dto/SignupRequest.java:22`
- **Issue**: Only `@NotBlank` validation, no strength requirements
- **Risk**: Weak passwords compromise security
- **Fix**: Add custom validator for password strength

### 30. **No Rate Limiting**
- **Location**: Authentication endpoints
- **Issue**: No protection against brute force attacks
- **Risk**: Account enumeration and brute force attacks
- **Fix**: Implement rate limiting on login/signup endpoints

## ARCHITECTURAL ISSUES

### 31. **Inconsistent URL Patterns**
- **Location**: Various controllers
- **Issue**: Mix of `/api/teacher/`, `/api/student/`, `/api/admin/` patterns
- **Risk**: Inconsistent API design
- **Fix**: Standardize URL patterns

### 32. **No API Versioning**
- **Location**: All controllers
- **Issue**: No version prefix in URLs (e.g., `/api/v1/`)
- **Risk**: Breaking changes affect all clients
- **Fix**: Add versioning strategy

### 33. **Missing Logging**
- **Location**: Service and controller classes
- **Issue**: No logging for important operations
- **Risk**: Difficult to debug and audit
- **Fix**: Add comprehensive logging

### 34. **No Request/Response Logging**
- **Location**: No filter or interceptor
- **Issue**: No logging of incoming requests and responses
- **Risk**: Difficult to debug issues
- **Fix**: Add logging filter or interceptor

## SUMMARY

**Total Issues Found: 34**
- **Critical Security Issues: 10**
- **Configuration Issues: 6**
- **Code Quality Issues: 14**
- **Architectural Issues: 4**

**Priority Actions:**
1. Fix hardcoded credentials (database password, JWT secret, admin password)
2. Fix invalid Spring Boot and Java versions in pom.xml
3. Implement file upload security (path traversal, file type validation)
4. Add global exception handler
5. Fix CORS configuration
6. Implement proper error handling and logging

