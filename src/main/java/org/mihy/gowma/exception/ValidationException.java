/* Copyright 2017 Mihy, org.
* All rights reserved.
 */
package org.mihy.gowma.exception;

import org.springframework.validation.Errors;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return this.errors;
    }

    public String getMessage() {
        return this.errors.toString();
    }
}
