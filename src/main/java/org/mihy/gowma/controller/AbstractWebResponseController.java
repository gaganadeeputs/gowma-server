/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mihy.gowma.exception.CommonExceptionCode;
import org.mihy.gowma.exception.ForbiddenException;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.InvalidIdException;
import org.mihy.gowma.exception.WebResponse;
import org.mihy.gowma.exception.WebResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for controllers that return WebResponse object.
 */
public class AbstractWebResponseController {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractWebResponseController.class);


    /**
     * Convert ForbiddenEx to 403 in WebResponse object.
     *
     * @param ex ForbiddenEx
     * @return WebResponse object with a status 403
     */
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public WebResponse handleForbiddenEx(final ForbiddenException ex) {
        LOG.error(this.getClass().getSimpleName(), ex);
        return WebResponseBuilder.aGlobalErrorResponse(ex.getExceptionCode(), new Object[0]).build();
    }

    /**
     * Convert InvalidIdEx to 404 in WebResponse object.
     *
     * @param ex      InvalidIdEx
     * @param request {@link HttpServletRequest}
     * @return WebResponse object with a status 404
     */
    @ExceptionHandler(InvalidIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public WebResponse handleEmptyResultDataAccessEx(final InvalidIdException ex, final HttpServletRequest request) {
        LOG.error(this.getClass().getSimpleName(), ex);
        String header = request.getHeader(HttpHeaders.ACCEPT);
        final GowmaServiceExceptionCode exceptionCode = GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID;
        if (header != null && header.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
            return null;
        }
        return WebResponseBuilder.aGlobalErrorResponse(exceptionCode, new Object[0]).build();
    }

    /**
     * Convert EmptyResultDataAccessEx to 404 in WebResponse object.
     *
     * @param ex Data Access exception
     * @return WebResponse object with a status 404
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public WebResponse handleEmptyResultDataAccessEx(final EmptyResultDataAccessException ex) {
        LOG.error(this.getClass().getSimpleName(), ex);

        final GowmaServiceExceptionCode exceptionCode = GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID;

        return WebResponseBuilder.aGlobalErrorResponse(exceptionCode, new Object[0]).build();
    }

    /**
     * Convert data access exceptions to their representation in the WebResponse object.
     *
     * @param ex Data Access exception
     * @return WebResponse object with a status 400
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WebResponse handleDataAccessException(final DataAccessException ex) {
        LOG.error(this.getClass().getSimpleName(), ex);

        final GowmaServiceExceptionCode exceptionCode;
        if (ex instanceof DuplicateKeyException) {
            exceptionCode = GowmaServiceExceptionCode.CFG_GENERIC_DUPLICATE_NAME;
        } else {
            exceptionCode = GowmaServiceExceptionCode.CFG_GENERIC_DATA_ACCESS_EXCEPTION;
        }

        return WebResponseBuilder.aGlobalErrorResponse(exceptionCode, new Object[0]).build();
    }

    /**
     * HttpMessageNotReadableException exception handler.
     *
     * @param ex HttpMessageNotReadableException Exception
     * @return WebResponse object with a status 400
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WebResponse handleInvalidInput(final Exception ex) {
        LOG.warn(this.getClass().getSimpleName(), ex);

        final Throwable th = ExceptionUtils.getRootCause(ex);

        if (th instanceof InvalidFormatException) {
            final InvalidFormatException ife = (InvalidFormatException) th;
            final WebResponse.Errors errs = new WebResponse.Errors();
            final List<WebResponse.FieldError> l = new ArrayList<>();

            for (JsonMappingException.Reference ref : ife.getPath()) {
                l.add(new WebResponse.FieldError(CommonExceptionCode.HTTP_INVALID_PARAMETER.getCode(), ife.getOriginalMessage(), ref.getFieldName()));
            }

            errs.setFieldErrors(l);

            final WebResponse wr = new WebResponse<>();
            wr.setErrors(errs);
            return wr;
        }

        final Object[] args = new Object[1];
        args[0] = ExceptionUtils.getRootCauseMessage(ex);
        return WebResponseBuilder.aGlobalErrorResponse(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_INPUT, args).build();
    }
}
