package com.groupT.Smart.Campus.Services.Portal.exception;

public class ExpiredTokenException extends RuntimeException{

    public ExpiredTokenException(String message) {
        super(message);
    }
}
