package com.hexaware.jumbo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * jwtToken POJO.
 */
public class JwtTokenDetails {

    private String jwtAccessToken;

    /**
     * Objectmapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * default constructor.
     */
    public JwtTokenDetails() {

    }

    /**
     * @param paramJwtAccessToken paramJwtAccessToken.
     */
    public void setJwtAccessToken(final String paramJwtAccessToken) {
        this.jwtAccessToken = paramJwtAccessToken;
    }

    /**
     * @return jwtAccessToken.
     */
    public String getJwtAccessToken() {
        return this.jwtAccessToken;
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
