package com.dhaanesh.order.service.Order.Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({OrderNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ItemOutOfStockException.class, RestaurantClosedException.class})
    public ResponseEntity<ApiError> handleConflictOrBadRequest(RuntimeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex instanceof ItemOutOfStockException) {
            status = HttpStatus.CONFLICT;
        }
        ApiError apiError = new ApiError(status.value(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElement(NoSuchElementException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}


