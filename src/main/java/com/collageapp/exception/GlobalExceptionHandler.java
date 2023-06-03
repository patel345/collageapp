package com.collageapp.exception;

import com.collageapp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.ReflectPermission;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //(b)if you want to handle resourcenotfoundexception then use
    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<ErrorDetails>handleResourceNotFoundException(
        ResourceNotFoundException exception,
        WebRequest webRequest
    ){
      ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
      return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }
//(A):for global exception :-it will handle any exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception,
            WebRequest webRequest
    ) {
ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false) );
return  new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}