package com.groupT.Smart.Campus.Services.Portal.restControllerAdvice;

import com.groupT.Smart.Campus.Services.Portal.dtos.response.ResponseObject;
import com.groupT.Smart.Campus.Services.Portal.exception.InvalidLoginException;
import com.groupT.Smart.Campus.Services.Portal.exception.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler({UsernameNotFoundException.class, InvalidLoginException.class})
        public ResponseObject errorHandler(Exception exception){
            return new ResponseObject(exception.getMessage());
        }
}
