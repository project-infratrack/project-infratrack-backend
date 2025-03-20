package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for user registration requests.
 */
public class UserRegistrationRequest {
    @NotBlank(message = "ID number is required")
    @Pattern(regexp = "^[0-9]{8}$", message = "ID number must be 8 digits")
    private String idNumber;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit, one uppercase, one lowercase, and one special character")
    private String password;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Invalid mobile number format")
    private String mobileNumber;

    /**
     * Gets the email.
     *
     * @return the email
     */
    public @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public @NotBlank(message = "First name is required") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(@NotBlank(message = "First name is required") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the ID number.
     *
     * @return the ID number
     */
    public @NotBlank(message = "ID number is required") @Pattern(regexp = "^[0-9]{8}$", message = "ID number must be 8 digits") String getIdNumber() {
        return idNumber;
    }

    /**
     * Sets the ID number.
     *
     * @param idNumber the ID number
     */
    public void setIdNumber(@NotBlank(message = "ID number is required") @Pattern(regexp = "^[0-9]{8}$", message = "ID number must be 8 digits") String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public @NotBlank(message = "Last name is required") @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name
     */
    public void setLastName(@NotBlank(message = "Last name is required") @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Password is required") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit, one uppercase, one lowercase, and one special character") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit, one uppercase, one lowercase, and one special character") String password) {
        this.password = password;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public @NotBlank(message = "Username is required") @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters") String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(@NotBlank(message = "Username is required") @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters") String username) {
        this.username = username;
    }

    /**
     * Gets the mobile number.
     *
     * @return the mobile number
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Sets the mobile number.
     *
     * @param mobileNumber the mobile number
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}