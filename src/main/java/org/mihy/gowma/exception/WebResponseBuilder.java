/* Copyright 2017 Mihy, org.
* All rights reserved.
 */
package org.mihy.gowma.exception;

import java.util.ArrayList;
import java.util.List;

public final class WebResponseBuilder {
    private ExceptionCode exceptionCode;
    private String errorMessage;

    private WebResponseBuilder(ExceptionCode exceptionCode, String errorMessage) {
        this.exceptionCode = exceptionCode;
        this.errorMessage = errorMessage;
    }

    public static WebResponseBuilder aGlobalErrorResponse(ExceptionCode exceptionCode, Object[] arguments) {
        String message = String.format(exceptionCode.getMessage(), arguments);
        return new WebResponseBuilder(exceptionCode, message);
    }

    public static WebResponseBuilder aGlobalErrorResponse(ExceptionCode exceptionCode, String errorMessage) {
        return new WebResponseBuilder(exceptionCode, errorMessage);
    }

    public WebResponse build() {
        WebResponse response = new WebResponse();
        if (this.exceptionCode != null) {
            WebResponse.Errors errors = new WebResponse.Errors();
            List<WebResponse.Error> errorList = new ArrayList();
            errorList.add(new WebResponse.Error(this.exceptionCode.getCode(), this.errorMessage));
            errors.setGlobalErrors(errorList);
            response.setErrors(errors);
        }

        return response;
    }
}
