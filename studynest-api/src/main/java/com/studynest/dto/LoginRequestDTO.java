package com.studynest.dto;

public class LoginRequestDTO {
    private String identifier; // email or phone
    private String password;
    private String role; // "STUDENT" or "ADMIN"
    private String name;
    private String phone;
    private String email;

    public LoginRequestDTO() {}

    public String getIdentifier() {
        return identifier != null ? identifier : (phone != null ? phone : email);
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role != null ? role.toUpperCase() : "STUDENT";
    }

    public void setRole(String role) {
        this.role = role;
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
}
