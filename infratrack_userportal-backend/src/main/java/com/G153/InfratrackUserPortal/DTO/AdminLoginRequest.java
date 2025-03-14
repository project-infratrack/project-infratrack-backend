package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.NotBlank;

public class AdminLoginRequest {
    @NotBlank(message = "Admin No is required")
    private String adminNo;

    @NotBlank(message = "Password is required")
    private String password;

    public String getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
