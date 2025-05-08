package ru.webrise.subscription.exceptions;

public class UserSavingException extends RuntimeException {
    public UserSavingException(String message, Throwable cause) {
        super(message, cause);
    }
}
