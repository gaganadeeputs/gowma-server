/*
 * Copyright 2014 Mobile Iron, Inc.
 * All rights reserved.
 */

package org.mihy.gowma.exception;

import org.apache.http.Header;

/**
 * Exception thrown by RESTClient etc.
 */
public class HttpRequestException extends GowmaServiceRuntimeException {

    private static final long serialVersionUID = 1L;
    private final int status;
    private final Header[] responseHeaders;

    public HttpRequestException(final int status, final Header[] responseHeaders, 
                                final String defaultMessage, final ExceptionCode exceptionCode) {
        super(defaultMessage, exceptionCode);
        this.status = status;
        this.responseHeaders = responseHeaders;
    }

    public int getStatus() {
        return this.status;
    }

    public Header[] getResponseHeaders() {
        return this.responseHeaders;
    }
}
