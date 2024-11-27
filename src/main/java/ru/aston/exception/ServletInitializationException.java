package ru.aston.exception;

public class ServletInitializationException extends RuntimeException {
    public ServletInitializationException() {
    }

    public ServletInitializationException(String message) {
        super(message);
    }
}
