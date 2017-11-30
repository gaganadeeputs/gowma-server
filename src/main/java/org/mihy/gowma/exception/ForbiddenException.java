/*
 * Copyright 2014 Mobile Iron, Inc.
 * All rights reserved.
 */

package org.mihy.gowma.exception;

/**
 * Exception thrown by when a forbidden request is received.
 */
public class ForbiddenException extends GowmaServiceRuntimeException {

    private static final long serialVersionUID = 1L;


    public ForbiddenException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }


}
