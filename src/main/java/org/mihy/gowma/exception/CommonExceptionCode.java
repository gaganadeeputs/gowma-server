/* Copyright 2017 Mihy, org.
* All rights reserved.
 */
package org.mihy.gowma.exception;

/**
 * Common exception codes.
 */
public enum CommonExceptionCode implements ExceptionCode {

    HTTP_BAD_REQUEST("HTTP-400-1", "BADRequest", ""),
    /* Same as bad request, but a more verbose message. */
    HTTP_MISSING_PARAMETER("HTTP-400-2", "BADRequest",""),
    /* Same as bad request, but specifies */
    HTTP_INVALID_PARAMETER("HTTP-400-3", "CommonPropertyConstants.Error.GENERIC_FIELD_INVALID",""),
    /* Same as bad request, but specifies invalid value. */
    HTTP_INVALID_PARAMETER_VALUE("HTTP-400-4", "CommonPropertyConstants.Error.GENERIC_VALUE_INVALID",""),

    HTTP_UNAUTHORIZED("HTTP-401", "CommonPropertyConstants.Error.HTTP_UNAUTHORIZED",""),
    HTTP_FORBIDDEN("HTTP-403", "CommonPropertyConstants.Error.HTTP_FORBIDDEN",""),
    HTTP_NOT_FOUND("HTTP-404", "CommonPropertyConstants.Error.HTTP_NOT_FOUND",""),
    HTTP_METHOD_NOT_ALLOWED("HTTP-405", "CommonPropertyConstants.Error.HTTP_METHOD_NOT_ALLOWED",""),
    HTTP_NOT_ACCEPTABLE("HTTP-406", "CommonPropertyConstants.Error.HTTP_NOT_ACCEPTABLE",""),
    HTTP_INTERNAL_SERVER_ERROR("HTTP-500", "CommonPropertyConstants.Error.HTTP_INTERNAL_SERVER_ERROR",""),

    CACHE_NOT_FOUND_EXCEPTION("CACHE-404", "CommonPropertyConstants.Error.CACHE_NOT_FOUND","");

    private final String code;
    private final String key;
    private final String message;

    CommonExceptionCode(final String code, final String key, final String message) {
        this.code = code;
        this.key = key;
        this.message = message;

    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getMessage() {
        return this.key;
    }


    }

