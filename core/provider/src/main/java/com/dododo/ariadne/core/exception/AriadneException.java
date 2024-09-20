package com.dododo.ariadne.core.exception;

public class AriadneException extends RuntimeException {

    public AriadneException(String message) {
        super(message);
    }

    public AriadneException(Throwable throwable) {
        super(throwable);
    }
}
