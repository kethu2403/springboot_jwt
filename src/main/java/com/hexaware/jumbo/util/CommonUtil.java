package com.hexaware.jumbo.util;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * CommonUtil.
 */
@Component
public class CommonUtil {
    /**
     * initializing LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    /**
    * @param authHeader authHeader
    * @return token
    * @throws Exception IllegalArgumentException
    */
    public final String getTokenUsingHeader(final String authHeader) throws Exception {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or Invalid Authorization header");
        }
        try {
            String token = authHeader.substring(7);
            return token;
        } catch (Exception e) {
            LOGGER.info("Invalid Token" + e);
            throw new IllegalArgumentException("Invalid Token");
        }
    }
}
