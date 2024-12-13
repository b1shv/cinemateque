package ru.aston.exception;


public class ConsistencyException extends RuntimeException {
    public ConsistencyException() {
    }

    public ConsistencyException(String message) {
        super(message);
    }
}
