package com.studynest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    private String email;

    private String password = "password123";

    @Column(nullable = false)
    private String role = "STUDENT"; // "STUDENT" or "ADMIN"

    private String studentIdCard;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Student() {}

    public Student(String name, String phone, String email, String studentIdCard) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.studentIdCard = studentIdCard;
        this.role = "STUDENT";
        this.password = "password123";
    }

    public Student(String name, String phone, String email, String password, String role, String studentIdCard) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role != null ? role.toUpperCase() : "STUDENT";
        this.studentIdCard = studentIdCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role != null ? role.toUpperCase() : "STUDENT";
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

    public String getStudentIdCard() {
        return studentIdCard;
    }

    public void setStudentIdCard(String studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
