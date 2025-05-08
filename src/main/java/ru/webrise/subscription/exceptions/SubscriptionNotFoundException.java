package ru.webrise.subscription.exceptions;

public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
