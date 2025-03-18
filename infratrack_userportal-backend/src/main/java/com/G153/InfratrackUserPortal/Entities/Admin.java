package com.G153.InfratrackUserPortal.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class representing an admin in the system.
 */
@Document(collection = "admins")  // MongoDB Collection
public class Admin {
    @Id
    private String id;
    private String adminNo;
    private String password;



    /**
     * Gets the admin number.
     *
     * @return the admin number
     */
    public String getAdminNo() { return adminNo; }

    /**
     * Sets the admin number.
     *
     * @param adminNo the admin number
     */
    public void setAdminNo(String adminNo) { this.adminNo = adminNo; }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) { this.password = password; }
}
