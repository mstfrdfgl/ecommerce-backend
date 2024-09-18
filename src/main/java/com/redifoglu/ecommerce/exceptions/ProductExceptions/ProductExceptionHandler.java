package com.redifoglu.ecommerce.exceptions.ProductExceptions;

import com.redifoglu.ecommerce.exceptions.ErrorResponse;
import com.redifoglu.ecommerce.exceptions.InsufficientStockException;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InsufficientStockException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
