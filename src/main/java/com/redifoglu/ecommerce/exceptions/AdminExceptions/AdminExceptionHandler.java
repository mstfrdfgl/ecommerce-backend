package com.redifoglu.ecommerce.exceptions.AdminExceptions;

import com.redifoglu.ecommerce.exceptions.ErrorResponse;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.exceptions.UnathenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdminExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UnathenticatedException exception){
        ErrorResponse response=new ErrorResponse();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setMessage(exception.getMessage());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }
}

