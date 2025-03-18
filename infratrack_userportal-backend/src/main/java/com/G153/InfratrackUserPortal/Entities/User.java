package com.G153.InfratrackUserPortal.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Entity class representing a user in the system.
 */
@Document(collection = "users")
public class User {
    @Id
    private String id;  // MongoDB uses String for IDs

    @Field("idNumber")
    private String idNumber;

    @Field("firstName")
    private String firstName;

    @Field("lastName")
    private String lastName;

    @Field("username")
    private String username;

    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("mobileNumber")
    private String mobileNumber;

    @Field("otp")
    private String otp;

    @Field("otpExpirationTime")
    private LocalDateTime otpExpirationTime;

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the ID.
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID.
     *
     * @param id the ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the ID number.
     *
     * @return the ID number
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Sets the ID number.
     *
     * @param idNumber the ID number
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the OTP.
     *
     * @return the OTP
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Sets the OTP.
     *
     * @param otp the OTP
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * Gets the OTP expiration time.
     *
     * @return the OTP expiration time
     */
    public LocalDateTime getOtpExpirationTime() {
        return otpExpirationTime;
    }

    /**
     * Sets the OTP expiration time.
     *
     * @param otpExpirationTime the OTP expiration time
     */
    public void setOtpExpirationTime(LocalDateTime otpExpirationTime) {
        this.otpExpirationTime = otpExpirationTime;
    }

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Parameterized constructor.
     *
     * @param email the email
     * @param firstName the first name
     * @param id the ID
     * @param idNumber the ID number
     * @param lastName the last name
     * @param mobileNumber the mobile number
     * @param password the password
     * @param username the username
     */
    public User(String email, String firstName, String id, String idNumber, String lastName, String mobileNumber, String password, String username) {
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.idNumber = idNumber;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.username = username;
    }
}