package com.hexaware.jumbo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * user POJO.
 */
@Entity
@Table(name = "user")
public class UserDetails {

    /**
     * Objectmapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String username;
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    /**
     * default constructor.
     */
    public UserDetails() {

    }
    /**
     * @param paramId id.
     * @param paramUsername paramUsername.
     * @param paramUserPassword paramUserPassword.
     */
    public UserDetails(final int paramId, final String paramUsername, final String paramUserPassword) {
        this.id = paramId;
        this.username = paramUsername;
        this.userPassword = paramUserPassword;
    }
    /**
     * @return id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param paramId paramId.
     */
    public void setId(final int paramId) {
        this.id = paramId;
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
