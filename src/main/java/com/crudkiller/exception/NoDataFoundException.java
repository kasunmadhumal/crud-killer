package com.crudkiller.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String message) {
        super(message);
    }

    public static NoDataFoundException by(String message) {
        return new NoDataFoundException(message);
    }
}