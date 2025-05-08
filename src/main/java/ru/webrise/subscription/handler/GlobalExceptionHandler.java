package ru.webrise.subscription.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.webrise.subscription.exceptions.*;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserSavingException.class)
    public ResponseEntity<ErrorResponse> handleUserSavingException(UserSavingException ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserDeletingException.class)
    public ResponseEntity<ErrorResponse> handleUserDeletingException(UserDeletingException ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotFoundException(SubscriptionNotFoundException ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubscriptionChangingException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionChangingException(SubscriptionChangingException ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        LOGGER.error("Ошибка валидации: {}", errors);

        return buildValidationErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Ошибка валидации.",
                errors
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        status.value(),
                        status.getReasonPhrase(),
                        ex.getMessage()
                )
        );
    }

    private ResponseEntity<ErrorResponse> buildValidationErrorResponse(
            HttpStatus status,
            String message,
            List<String> errors
    ) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        status.value(),
                        status.getReasonPhrase(),
                        message + " Список ошибок: " + errors
                )
        );
    }


    public record ErrorResponse(
            int status,
            String error,
            String message
    ) {
    }
}