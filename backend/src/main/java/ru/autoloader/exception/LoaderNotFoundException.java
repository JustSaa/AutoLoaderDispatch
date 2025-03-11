package ru.autoloader.exception;

public class LoaderNotFoundException extends RuntimeException {
    public LoaderNotFoundException(String message) {
        super(message);
    }
}