package ru.aston.exception;

public class DataSourceException extends RuntimeException {
    public DataSourceException() {
    }

    public DataSourceException(String message) {
        super(message);
    }
}
