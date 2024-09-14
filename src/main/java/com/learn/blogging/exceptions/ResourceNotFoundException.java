package com.learn.blogging.exceptions;

public class ResourceNotFoundException extends  RuntimeException{

    String resource;
    String filed;
    long value;
    public ResourceNotFoundException(String resource, String filed, long value) {

        super(String.format("%s  not found with %s: %d",resource,filed,value));
        this.resource=resource;
        this.filed=filed;
        this.value=value;
    }
}
