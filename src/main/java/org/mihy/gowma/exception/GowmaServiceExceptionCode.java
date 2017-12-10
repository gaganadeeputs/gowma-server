/* Copyright 2017 Mihy, org.
* All rights reserved.
 */

package org.mihy.gowma.exception;

import org.springframework.http.HttpStatus;

/**
 * Enumeration to hold message codes and their respective key to use against a resource bundle. Minor code ranges <br/>
 */
public enum GowmaServiceExceptionCode implements ExceptionCode {


    CFG_GENERIC_INVALID_ID("CFG-101", "CFG_GENERIC_INVALID_ID", "Invalid id is provided"),
    CFG_GENERIC_DUPLICATE_NAME("CFG-102", "CFG_GENERIC_DUPLICATE_NAME", "Name with %s already exists in the system"),
    CFG_GENERIC_DATA_ACCESS_EXCEPTION("CFG-102", "CFG_GENERIC_DATA_ACCESS_EXCEPTION", "Error updating database"),
    CFG_GENERIC_INVALID_INPUT("CFG-106", "CFG_GENERIC_INVALID_INPUT", "The request contains invalid input"),
    // 400 minor codes
    USER_DUPLICATE_EMAIL("USER-101", "USER.EMAIL.DUPLICATE", "User with Email %s already exists in the system"),
    USER_DUPLICATE_MOBILE_NO("USER-102", "USER.MOBILE.NUMBER.DUPLICATE", "User with mobile no %s already exists in the system");



    private final String code;
    private final String key;
    private final String message;
    private HttpStatus httpStatus;

    GowmaServiceExceptionCode(final String code, final String key, final String message) {
        this.code = code;
        this.key = key;
        this.message = message;

        // All non-400 minor codes above must call httpStatus constructor
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    GowmaServiceExceptionCode(final String code, final String key, final String message, final HttpStatus httpStatus) {
        this(code, key, message);
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }


    @Override
    public String getMessage() {
        return this.message;
    }


    @Override
    public String getKey() {
        return this.key;
    }


    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
