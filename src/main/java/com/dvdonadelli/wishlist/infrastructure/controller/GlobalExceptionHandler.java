package com.dvdonadelli.wishlist.infrastructure.controller;

import com.dvdonadelli.wishlist.domain.exceptions.WishlistItemNotFoundException;
import com.dvdonadelli.wishlist.domain.exceptions.WishlistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        String errorMessage = "An error occurred: " + ex.getMessage();
        HttpStatus status = INTERNAL_SERVER_ERROR;

        if (ex instanceof WishlistNotFoundException || ex instanceof WishlistItemNotFoundException) {
            status = NOT_FOUND;
        }

        return ResponseEntity.status(status).body(errorMessage);
    }
}
