package com.hexaware.jumbo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * user POJO.
 */
public class User {

    /**
     * Objectmapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * username.
     */
    private String username;
    /**
     * userpassword.
     */
    private String userPassword;
    /**
     * default constructor.
     */
    public User() {

    }
    /**
     * @param paramUsername paramUsername.
     * @param paramUserPassword paramUserPassword.
     */
    public User(final String paramUsername, final String paramUserPassword) {
        this.username = paramUsername;
        this.userPassword = paramUserPassword;
    }
    /**
     * @return username.
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param paramUsername paramUsername.
     */
    public void setUsername(final String paramUsername) {
        this.username = paramUsername;
    }
    /**
     * @return password
     */
    public String getPassword() {
        return userPassword;
    }

    /**
     * @param paramUserPassword paramUserPassword.
     */
    public void setPassword(final String paramUserPassword) {
        this.userPassword = paramUserPassword;
    }

    /**
     * @return objectMapper.
     */
    public final String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException j) {
            throw new RuntimeException("Error jsonifying Project Object", j);
        }
    }

}
