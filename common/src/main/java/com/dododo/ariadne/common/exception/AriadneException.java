package com.dododo.ariadne.common.exception;

public class AriadneException extends RuntimeException {

    public AriadneException(String message) {
        super(message);
    }

    public AriadneException(Throwable throwable) {
        super(throwable);
    }
}
