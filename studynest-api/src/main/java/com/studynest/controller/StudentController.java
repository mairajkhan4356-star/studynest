package com.studynest.controller;

import com.studynest.dto.ApiResponseDTO;
import com.studynest.dto.LoginRequestDTO;
import com.studynest.model.Student;
import com.studynest.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<Student>> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            Student user = studentService.authenticate(loginDTO);
            String roleLabel = user.isAdmin() ? "Administrator" : "Student";
            return ResponseEntity.ok(ApiResponseDTO.success("Authenticated successfully as " + roleLabel + ".", user));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponseDTO.error(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<Student>> register(@RequestBody LoginRequestDTO registerDTO) {
        try {
            Student student = studentService.registerStudent(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success("Account created successfully for " + student.getName() + ".", student));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Student>> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(ApiResponseDTO.success("User record retrieved.", student));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Student>>> getAllStudents() {
        return ResponseEntity.ok(ApiResponseDTO.success("All users retrieved.", studentService.getAllStudents()));
    }
}
