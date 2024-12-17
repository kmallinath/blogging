package com.learn.blogging.exceptions;

import org.springframework.http.HttpStatusCode;

public class InvalidUserException extends RuntimeException {

     String message;


    public InvalidUserException(String message) {
        super(String.format("User details are not valid", message));
        this.message = message;
    }
}
