package ru.webrise.subscription.exceptions;

public class UserDeletingException extends RuntimeException {
    public UserDeletingException(String message, Throwable cause) {
        super(message, cause);
    }
}