package com.patiun.library.exception;

public class ConnectionFactoryInitialisationException extends RuntimeException {

    public ConnectionFactoryInitialisationException() {
    }

    public ConnectionFactoryInitialisationException(String message) {
        super(message);
    }

    public ConnectionFactoryInitialisationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionFactoryInitialisationException(Throwable cause) {
        super(cause);
    }

    public ConnectionFactoryInitialisationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
