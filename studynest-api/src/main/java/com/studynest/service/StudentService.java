package com.studynest.service;

import com.studynest.dto.LoginRequestDTO;
import com.studynest.model.Student;
import com.studynest.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student authenticate(LoginRequestDTO dto) {
        String identifier = dto.getIdentifier();
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new IllegalArgumentException("Username, phone number, or email is required.");
        }
        identifier = identifier.trim();

        String targetRole = (dto.getRole() != null && !dto.getRole().trim().isEmpty()) 
                ? dto.getRole().trim().toUpperCase() 
                : "STUDENT";

        Optional<Student> existingOpt = studentRepository.findByIdentifier(identifier);

        if ("ADMIN".equals(targetRole)) {
            if (existingOpt.isEmpty()) {
                throw new IllegalArgumentException("Administrator account not found with identifier: " + identifier);
            }
            Student admin = existingOpt.get();
            if (!admin.isAdmin()) {
                throw new SecurityException("Unauthorized: Account does not have administrative privileges.");
            }
            if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
                if (!admin.getPassword().equals(dto.getPassword().trim())) {
                    throw new IllegalArgumentException("Invalid administrator password.");
                }
            }
            return admin;
        }

        // Student authentication
        if (existingOpt.isPresent()) {
            Student student = existingOpt.get();
            if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
                student.setName(dto.getName().trim());
                studentRepository.save(student);
            }
            return student;
        }

        // Auto-register new student if not exists
        String name = (dto.getName() != null && !dto.getName().trim().isEmpty()) ? dto.getName().trim() : "Student";
        String phone = (dto.getPhone() != null && !dto.getPhone().trim().isEmpty()) ? dto.getPhone().trim() : identifier;
        String email = (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) ? dto.getEmail().trim() : (phone + "@studynest.com");
        String password = (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) ? dto.getPassword().trim() : "pass123";
        String studentIdCard = "SN-" + (1000 + (int)(Math.random() * 9000));

        Student newStudent = new Student(name, phone, email, password, "STUDENT", studentIdCard);
        return studentRepository.save(newStudent);
    }

    @Transactional
    public Student registerStudent(LoginRequestDTO dto) {
        if (dto.getPhone() == null || dto.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required for registration.");
        }
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required for registration.");
        }

        String phone = dto.getPhone().trim();
        if (studentRepository.findByPhone(phone).isPresent()) {
            throw new IllegalArgumentException("A student with this phone number already exists.");
        }

        String name = dto.getName().trim();
        String email = (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) 
                ? dto.getEmail().trim() 
                : (phone + "@studynest.com");
        String password = (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) 
                ? dto.getPassword().trim() 
                : "pass123";
        String studentIdCard = "SN-" + (1000 + (int)(Math.random() * 9000));

        Student student = new Student(name, phone, email, password, "STUDENT", studentIdCard);
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student record not found with ID: " + id));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
