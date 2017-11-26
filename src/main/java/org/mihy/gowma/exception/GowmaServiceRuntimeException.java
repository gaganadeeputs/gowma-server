/* Copyright 2017 Mihy, org.
* All rights reserved.
 */
package org.mihy.gowma.exception;

import org.springframework.context.MessageSourceResolvable;

import java.util.ArrayList;
import java.util.List;


public class GowmaServiceRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final ExceptionCode exceptionCode;
    private final String defaultMessage;
    private final Object[] messageArguments;
    private final List<ChildMessage> childMessages;

    public GowmaServiceRuntimeException(String defaultMessage, ExceptionCode exceptionCode) {
        this(defaultMessage, exceptionCode, new Object[0]);
    }

    public GowmaServiceRuntimeException(String defaultMessage, ExceptionCode exceptionCode, Object... args) {
        super(defaultMessage);
        this.childMessages = new ArrayList();
        this.defaultMessage = defaultMessage;
        this.exceptionCode = exceptionCode;
        this.messageArguments = args;
    }

    public GowmaServiceRuntimeException(ExceptionCode exceptionCode, Object... args) {
        super(exceptionCode.getCode());
        this.childMessages = new ArrayList();
        this.defaultMessage = exceptionCode.getCode();
        this.exceptionCode = exceptionCode;
        this.messageArguments = args;
    }

    public GowmaServiceRuntimeException(Throwable throwable, ExceptionCode exceptionCode, Object... args) {
        super(exceptionCode.getCode(), throwable);
        this.childMessages = new ArrayList();
        this.defaultMessage = exceptionCode.getCode();
        this.exceptionCode = exceptionCode;
        this.messageArguments = args;
    }

    public GowmaServiceRuntimeException(String defaultMessage, Throwable throwable, ExceptionCode exceptionCode) {
        this(defaultMessage, throwable, exceptionCode, new Object[0]);
    }

    public GowmaServiceRuntimeException(String defaultMessage, Throwable throwable, ExceptionCode exceptionCode, Object... args) {
        super(defaultMessage, throwable);
        this.childMessages = new ArrayList();
        this.defaultMessage = defaultMessage;
        this.exceptionCode = exceptionCode;
        this.messageArguments = args;
    }

    public Object[] getArguments() {
        return this.messageArguments;
    }

    public String[] getCodes() {
        return new String[]{this.exceptionCode.getMessage(), this.exceptionCode.getCode()};
    }

    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    public String getDisplayErrorCode() {
        return this.exceptionCode.getCode();
    }

    public ExceptionCode getExceptionCode() {
        return this.exceptionCode;
    }

    public List<GowmaServiceRuntimeException.ChildMessage> getChildMessages() {
        return this.childMessages;
    }

    public void addChildMessage(GowmaServiceRuntimeException.ChildMessage childMessage) {
        this.childMessages.add(childMessage);
    }

    public String toString() {
        String message = this.getLocalizedMessage() == null ? "" : this.getLocalizedMessage();
        return String.format("%s - %s: %s", new Object[]{this.getClass().getName(), this.getDisplayErrorCode(), message});
    }

    public static final class ChildMessage implements MessageSourceResolvable {
        private final String defaultMessage;
        private final ExceptionCode exceptionCode;
        private final Object[] messageArguments;

        public ChildMessage(String defaultMessage, ExceptionCode exceptionCode, Object... messageArguments) {
            this.defaultMessage = defaultMessage;
            this.exceptionCode = exceptionCode;
            this.messageArguments = messageArguments;
        }

        public ExceptionCode getExceptionCode() {
            return this.exceptionCode;
        }

        public Object[] getMessageArguments() {
            return this.messageArguments;
        }

        public String getDisplayErrorCode() {
            return this.exceptionCode.getCode();
        }

        public String[] getCodes() {
            return new String[]{this.exceptionCode.getMessage(), this.exceptionCode.getCode()};
        }

        public Object[] getArguments() {
            return this.messageArguments;
        }

        public String getDefaultMessage() {
            return this.defaultMessage;
        }
    }
}


