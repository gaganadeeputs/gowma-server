/*
 * Copyright 2014 Mobile Iron, Inc.
 * All rights reserved.
 */

package org.mihy.gowma.exception;

/**
 * Exception thrown by IdValidator etc.
 */
public class InvalidIdException extends GowmaServiceRuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidIdException(final String defaultMessage, final ExceptionCode exceptionCode) {
        super(defaultMessage, exceptionCode);
    }
}
