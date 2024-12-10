package com.oocl.ita.web.core.exception;

public class VerificationCodeMismatchException extends RuntimeException {
    public VerificationCodeMismatchException(String message) {
        super(message);
    }
}
