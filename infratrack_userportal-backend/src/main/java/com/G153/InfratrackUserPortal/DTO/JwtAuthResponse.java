package com.G153.InfratrackUserPortal.DTO;

public class JwtAuthResponse {
    private String token;
    private String type = "Bearer";

    public JwtAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}