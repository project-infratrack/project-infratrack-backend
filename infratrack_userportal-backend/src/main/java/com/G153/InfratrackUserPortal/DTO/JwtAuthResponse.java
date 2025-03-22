package com.G153.InfratrackUserPortal.DTO;

/**
 * Response object for JWT authentication.
 */
public class JwtAuthResponse {
    private String token;
    private String type = "Bearer";
    private String accessToken;



    /**
     * Constructs a JwtAuthResponse with the specified access token.
     *
     * @param accessToken the JWT access token
     */
    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
        this.token = accessToken; // Optionally set token to the same value
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

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}