package com.G153.InfratrackUserPortal.DTO;

/**
 * Response object for JWT authentication.
 */
public class JwtAuthResponse {
    private String token;
    private String type = "Bearer";

    /**
     * Constructs a JwtAuthResponse with the specified token.
     *
     * @param token the JWT token
     */
    public JwtAuthResponse(String token) {
        this.token = token;
    }

    /**
     * Gets the JWT token.
     *
     * @return the JWT token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the JWT token.
     *
     * @param token the JWT token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the token type.
     *
     * @return the token type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the token type.
     *
     * @param type the token type
     */
    public void setType(String type) {
        this.type = type;
    }
}