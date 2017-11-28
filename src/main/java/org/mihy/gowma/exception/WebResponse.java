/* Copyright 2017 Mihy, org.
* All rights reserved.
 */
package org.mihy.gowma.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@JsonInclude(JsonInclude.Include.ALWAYS)
public class WebResponse<T> {
    private WebResponse.Errors errors;
    private T result;


    public WebResponse() {
    }

    public WebResponse(T result) {
        this.result = result;
    }

    public WebResponse(WebResponse.Errors errors) {
        this.errors = errors;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public WebResponse.Errors getErrors() {
        return this.errors;
    }

    public void setErrors(WebResponse.Errors errors) {
        this.errors = errors;
    }

    public static class FieldError extends WebResponse.Error {
        private static final long serialVersionUID = 1L;
        private String field;

        public FieldError() {
        }

        public FieldError(String code, String message, String field) {
            super(code, message);
            this.field = field;
        }

        public String getField() {
            return this.field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }

    public static class Error implements Serializable {
        private static final long serialVersionUID = 1L;
        private String code;
        private String message;

        public Error() {
        }

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class Errors implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<Error> globalErrors;
        private List<WebResponse.FieldError> fieldErrors;

        public Errors() {
        }

        public Errors(org.springframework.validation.Errors result) {
            Iterator var4;
            if (result.hasFieldErrors()) {
                this.fieldErrors = new ArrayList(result.getFieldErrorCount());
                var4 = result.getFieldErrors().iterator();

                while (var4.hasNext()) {
                    org.springframework.validation.FieldError fe = (org.springframework.validation.FieldError) var4.next();
                    WebResponse.FieldError er = new WebResponse.FieldError(fe.getCode(), fe.getDefaultMessage(), fe.getField());
                    this.fieldErrors.add(er);
                }
            }

            if (result.hasGlobalErrors()) {
                this.globalErrors = new ArrayList(result.getGlobalErrorCount());
                var4 = result.getGlobalErrors().iterator();

                while (var4.hasNext()) {
                    ObjectError oe = (ObjectError) var4.next();
                    WebResponse.Error er = new WebResponse.Error(oe.getCode(), oe.getDefaultMessage());
                    this.globalErrors.add(er);
                }
            }

        }

        public List<WebResponse.Error> getGlobalErrors() {
            return this.globalErrors;
        }

        public void setGlobalErrors(List<WebResponse.Error> globalErrors) {
            this.globalErrors = globalErrors;
        }

        public List<WebResponse.FieldError> getFieldErrors() {
            return this.fieldErrors;
        }

        public void setFieldErrors(List<WebResponse.FieldError> fieldErrors) {
            this.fieldErrors = fieldErrors;
        }

        public boolean hasErrors() {
            return !CollectionUtils.isEmpty(this.fieldErrors) || !CollectionUtils.isEmpty(this.globalErrors);
        }
    }
}

