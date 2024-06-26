package com.siemens.SpringBootAPI.AOP;

import com.siemens.SpringBootAPI.models.myCustomException;
import com.siemens.SpringBootAPI.models.myException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(myException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> GlobalExceptionHandler(myException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(myCustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> GlobalExceptionHandle(myCustomException  exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}



