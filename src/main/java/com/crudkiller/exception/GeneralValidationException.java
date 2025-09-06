package com.crudkiller.exception;

public class GeneralValidationException extends RuntimeException {
    public GeneralValidationException(String message) {
        super(message);
    }

    public static GeneralValidationException by(String message) {
        return new GeneralValidationException(message);
    }
}