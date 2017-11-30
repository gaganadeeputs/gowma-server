/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.exception.handlers;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.catalina.connector.ClientAbortException;
import org.mihy.gowma.exception.CommonExceptionCode;
import org.mihy.gowma.exception.ExceptionCode;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.exception.ValidationException;
import org.mihy.gowma.exception.WebResponse;
import org.mihy.gowma.exception.WebResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains common exception handlers that can be used outside of a controller inheritance structure.
 */
@RestControllerAdvice
public class CommonExceptionHandlers extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CommonExceptionHandlers.class);


    /**
     * Handles all unhandled exceptions for the controller.
     *
     * @param t Throwable.
     * @return WebResponse object with a status 500
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity handleException(final Throwable t) {
        final WebResponse response = WebResponseBuilder.aGlobalErrorResponse(CommonExceptionCode.HTTP_INTERNAL_SERVER_ERROR
                , new Object[0]).build();
        return handleThrowableInternal(t, response, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(final ValidationException ex) {
        final WebResponse.Errors errors = new WebResponse.Errors(ex.getErrors());
        final WebResponse wr = new WebResponse<>(errors);
        return handleExceptionInternal(ex, wr, null, HttpStatus.BAD_REQUEST, null);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        final WebResponse response = WebResponseBuilder.aGlobalErrorResponse(CommonExceptionCode.HTTP_FORBIDDEN, new Object[0]).build();
        return handleExceptionInternal(ex, response, null, HttpStatus.FORBIDDEN, null);
    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity handleEmptyResult(final EmptyResultDataAccessException e) {
        final WebResponse response = WebResponseBuilder.aGlobalErrorResponse(CommonExceptionCode.HTTP_NOT_FOUND, new Object[0]).build();
        return handleExceptionInternal(e, response, null, HttpStatus.NOT_FOUND, null);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(final IllegalArgumentException ex) {
        String msg = ex.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            msg = getMessage(CommonExceptionCode.HTTP_INVALID_PARAMETER_VALUE);
        }

        final WebResponse response = WebResponseBuilder.aGlobalErrorResponse(CommonExceptionCode.HTTP_INVALID_PARAMETER_VALUE, msg).build();
        return handleExceptionInternal(ex, response, null, HttpStatus.BAD_REQUEST, null);
    }

    /**
     * Handles a Polaris runtime exception.
     *
     * @param e       the thrown exception
     * @param request the request
     * @return Web response with details of the error.
     */
    @ExceptionHandler(GowmaServiceRuntimeException.class)
    public ResponseEntity handlePolarisRuntimeException(final GowmaServiceRuntimeException e, final WebRequest request) {
        final WebResponse response = WebResponseBuilder.aGlobalErrorResponse(e.getExceptionCode(), e.getArguments()).build();

        if (!CollectionUtils.isEmpty(e.getChildMessages())) {
            for (final GowmaServiceRuntimeException.ChildMessage child : e.getChildMessages()) {
                if (child.getExceptionCode() != null) {
                    final WebResponse childResponse = WebResponseBuilder.aGlobalErrorResponse(child.getExceptionCode(), child.getArguments()).build();
                    addGlobalErrors(response, childResponse);
                }
            }
        }

        return handleExceptionInternal(e, response, null, HttpStatus.BAD_REQUEST, request);
    }


    /**
     * Client abort exception handler.
     *
     * @param ex Client aborted
     */
    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException(final ClientAbortException ex) {
        LOG.trace("Client aborted", ex);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body,
                                                             final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleThrowableInternal(ex, body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
                                                         final HttpStatus status, final WebRequest request) {
        final WebResponse.Errors errors = new WebResponse.Errors(ex.getBindingResult());
        final WebResponse wr = new WebResponse<>(errors);
        return handleExceptionInternal(ex, wr, null, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        final String supportedMethods = ex.getSupportedMethods().toString();
        final WebResponse response = WebResponseBuilder.aGlobalErrorResponse(
                CommonExceptionCode.HTTP_METHOD_NOT_ALLOWED, new Object[]{ex.getMethod(), supportedMethods}).build();
        return handleExceptionInternal(ex, response, null, HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException ex,
                                                                      final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final WebResponse response = WebResponseBuilder.aGlobalErrorResponse(CommonExceptionCode.HTTP_NOT_ACCEPTABLE,
                new Object[]{ex.getSupportedMediaTypes().toArray()}).build();
        return handleExceptionInternal(ex, response, null, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException e,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final List<WebResponse.FieldError> listErrors = new ArrayList<>();
        final WebResponse.Errors webResponseErrors = new WebResponse.Errors();
        final BindingResult result = e.getBindingResult();

        LOG.error("Invalid parameter. name: [{}] message: [{}]", e.getParameter().getParameterName(), e.getMessage());

        if (result != null && result.hasFieldErrors()) {
            for (org.springframework.validation.FieldError fe : result.getFieldErrors()) {
                final WebResponse.FieldError er = new WebResponse.FieldError(CommonExceptionCode.HTTP_INVALID_PARAMETER.getCode(),
                        CommonExceptionCode.HTTP_INVALID_PARAMETER.getMessage(), fe.getField());
                listErrors.add(er);
            }
        } else {
            final String message = getMessage(
                    CommonExceptionCode.HTTP_INVALID_PARAMETER_VALUE,
                    e.getMessage(),
                    new Object[]{e.getParameter().getParameterName(), e.getParameter().getParameterType().getSimpleName()});

            listErrors.add(new WebResponse.FieldError(CommonExceptionCode.HTTP_INVALID_PARAMETER_VALUE.getCode(),
                    message, e.getParameter().getParameterName()));
        }

        webResponseErrors.setFieldErrors(listErrors);
        final WebResponse response = new WebResponse<>(webResponseErrors);
        return handleExceptionInternal(e, response, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        final String message = getMessage(CommonExceptionCode.HTTP_MISSING_PARAMETER, ex.getParameterName());
        final WebResponse.Errors errors = new WebResponse.Errors();
        errors.setFieldErrors(Arrays.asList(new WebResponse.FieldError(CommonExceptionCode.HTTP_MISSING_PARAMETER
                .getCode(), message, ex.getParameterName())));
        final WebResponse wr = new WebResponse<>(errors);
        return handleExceptionInternal(ex, wr, null, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
                                                        final HttpStatus status, final WebRequest request) {
        final WebResponse response = WebResponseBuilder.aGlobalErrorResponse(
                CommonExceptionCode.HTTP_INVALID_PARAMETER_VALUE,
                new Object[]{ex.getValue(), ClassUtils.getQualifiedName(ex.getRequiredType())}).build();
        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Gets a localized message for the specified exception code.
     *
     * @param exceptionCode the exception code to get a message for
     * @param arguments     optional templated arguments for the message
     * @return The message.
     */
    private String getMessage(final ExceptionCode exceptionCode, final Object... arguments) {
        return String.format(exceptionCode.getMessage(), arguments);
    }

    /**
     * Add the global errors from one WebResponse to another.
     *
     * @param masterWebResponse The WebResponse we add global errors to.
     * @param toAddWebResponse  The WebResponse possibly containing the global errors we need to add.
     */
    private void addGlobalErrors(final WebResponse masterWebResponse, final WebResponse toAddWebResponse) {
        if (toAddWebResponse.getErrors() == null || CollectionUtils.isEmpty(toAddWebResponse.getErrors().getGlobalErrors())) {
            return;  // nothing to add/merge
        }

        if (masterWebResponse.getErrors() == null) {
            masterWebResponse.setErrors(new WebResponse.Errors());
        }
        if (CollectionUtils.isEmpty(masterWebResponse.getErrors().getGlobalErrors())) {
            masterWebResponse.getErrors().setGlobalErrors(new ArrayList<WebResponse.Error>());
        }

        masterWebResponse.getErrors().getGlobalErrors().addAll(toAddWebResponse.getErrors().getGlobalErrors());
    }

    private ResponseEntity<Object> handleThrowableInternal(final Throwable t, final Object body,
                                                           final HttpHeaders headers, final HttpStatus status) {
        LOG.error(this.getClass().getSimpleName(), t);

        return new ResponseEntity<>(body, headers, status);
    }
}
