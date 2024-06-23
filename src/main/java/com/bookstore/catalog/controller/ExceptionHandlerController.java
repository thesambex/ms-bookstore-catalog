package com.bookstore.catalog.controller;

import com.bookstore.catalog.domain.dtos.ErrorResponse;
import com.bookstore.catalog.domain.exceptions.ConflictException;
import com.bookstore.catalog.domain.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception e, WebRequest request) {
        logger.error("Error: {}", e.getMessage(), e);
        var error = new ErrorResponse(e.getMessage(), request.getDescription(false), null, new Date());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e, WebRequest request) {
        var error = new ErrorResponse(e.getMessage(), request.getDescription(false), null, new Date());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e, WebRequest request) {
        var error = new ErrorResponse(e.getMessage(), request.getDescription(false), e.getExtra(), new Date());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
