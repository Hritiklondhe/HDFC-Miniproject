package com.hdfc.library.Report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalReportHandler {

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<?> reportNotFoundExceptionHandler(ReportNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
