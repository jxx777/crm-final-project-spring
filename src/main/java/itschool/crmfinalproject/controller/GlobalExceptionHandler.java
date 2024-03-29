package itschool.crmfinalproject.controller;

import itschool.crmfinalproject.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Object> createResponseEntity(HttpStatus status, String errorMessage, String error) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", errorMessage);
        return new ResponseEntity<>(body, status);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return createResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), "User not found");
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<Object> handleContactNotFoundException(ContactNotFoundException ex) {
        return createResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), "Contact not found");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid input for parameter '" + ex.getName() + "'. Expected type is '" + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'.";
        return createResponseEntity(HttpStatus.BAD_REQUEST, message, "Bad Request");
    }

    @ExceptionHandler(UnsupportedEntityTypeException.class)
    public ResponseEntity<Object> handleUnsupportedEntityTypeException(UnsupportedEntityTypeException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "Unsupported Entity Type");
    }

    @ExceptionHandler(UnsupportedFormatException.class)
    public ResponseEntity<Object> handleUnsupportedFormatException(UnsupportedFormatException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "Unsupported Format");
    }

    @ExceptionHandler(AvatarUploadException.class)
    public ResponseEntity<Object> handleAvatarUploadException(AvatarUploadException ex) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Avatar Upload Error");
    }
}