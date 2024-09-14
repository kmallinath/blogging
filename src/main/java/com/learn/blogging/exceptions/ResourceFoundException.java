package com.learn.blogging.exceptions;

import com.learn.blogging.beans.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceFoundException extends RuntimeException {

    String resource;
    String filed;
    long value;
    public ResourceFoundException(String resource, String filed, long value) {

        super(String.format("%s already found with %s: %d",resource,filed,value));
        this.resource=resource;
        this.filed=filed;
        this.value=value;
    }
}
