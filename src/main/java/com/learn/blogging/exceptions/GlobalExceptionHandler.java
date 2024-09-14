package com.learn.blogging.exceptions;

import com.learn.blogging.dao.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundHandler(ResourceNotFoundException ex)
    {
        String messgae=ex.getMessage();
        return new ResponseEntity<>(new ApiResponse(messgae,false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<ApiResponse> resourceAlreadyFound(ResourceFoundException ex)
    {
        String message=ex.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex)
    {
        Map<String,String>errorMap=new HashMap<>();
        List<ObjectError> errors= ex.getAllErrors();
        for(ObjectError o:errors)
        {
            String field=((FieldError)o).getField();
            String message=o.getDefaultMessage();
            errorMap.put(field,message);
        }

        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);

    }
}
