package com.hdfc.library.Borrowing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionBorrowingHandler {

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<?> bookNotAvailableExceptionHandler(BookNotAvailableException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BorrowingNotFoundException.class)
    public ResponseEntity<?> borrowingNotFoundExceptionHandler(BorrowingNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBorrowStatusException.class)
    public ResponseEntity<?> InvalidBorrowStatusExceptionHandler(InvalidBorrowStatusException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BorrowingIdAlreadyExistsException.class)
    public ResponseEntity<?> borrowingIdAlreadyExistsExceptionHandler(BorrowingIdAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
