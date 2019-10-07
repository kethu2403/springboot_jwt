package com.hexaware.jumbo.exception;

import org.springframework.http.HttpStatus;

/**
 * ErrorDetails.
 */
public class ErrorDetails {
    /**
     * Error Message.
     */
    private String message;
    /**
     * Http Status Code.
     */
    private HttpStatus code;

    /**
     * @return Error Message
     */
    public final String getMessage() {
        return message;
    }

    /**
     * @param messages - Error Message
     */
    public final void setMessage(final String messages) {
        this.message = messages;
    }

    /**
     * @return HttpStatus Code
     */
    public final HttpStatus getCode() {
        return code;
    }

    /**
     * @param codes - HttpStatus Code
     */
    public final void setCode(final HttpStatus codes) {
        this.code = codes;
    }

    /**
     * @param statuss  - HttpStatus
     * @param messages - Error Message
     */
    public ErrorDetails(final HttpStatus statuss, final String messages) {
        this.code = statuss;
        this.message = messages;
    }
}
