package com.groupT.Smart.Campus.Services.Portal.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidLoginException extends RuntimeException{

    public InvalidLoginException(String message, AuthenticationException ex) {
        super(message);
    }
}
