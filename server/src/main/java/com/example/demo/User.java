package com.example.demo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

/**
 * Entity class representing a user in the application.
 */
@Entity(name = "users")
public class User {

    @Id
    private Long id;

    private String email;
    private String handle;
    private String date;
    private String userId;

    /**
     * Constructs a new User entity with the specified details.
     *
     * @param userId The unique identifier for the user.
     * @param handle The user's handle or username.
     * @param email The user's email address.
     * @param date The date when the user entity was created.
     */
    public User(String userId, String handle, String email, String date) {
        this.handle = handle;
        this.userId = userId;
        this.email = email;
        this.date = date;
    }

    /**
     * Gets the unique identifier of the user entity.
     *
     * @return The user entity's unique identifier.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the user entity.
     *
     * @param id The new unique identifier for the user entity.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user's unique identifier.
     *
     * @return The user's unique identifier.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param userId The new unique identifier for the user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The new email address for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the date when the user entity was created.
     *
     * @return The date when the user entity was created.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date when the user entity was created.
     *
     * @param date The new creation date for the user entity.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the user's handle or username.
     *
     * @return The user's handle or username.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the user's handle or username.
     *
     * @param handle The new handle or username for the user.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Generates a string representation of the User object.
     *
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", handle='" + handle + '\'' +
                ", date='" + date + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}