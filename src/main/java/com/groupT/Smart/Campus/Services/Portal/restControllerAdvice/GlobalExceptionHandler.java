package com.groupT.Smart.Campus.Services.Portal.restControllerAdvice;

import com.groupT.Smart.Campus.Services.Portal.dtos.response.ResponseObject;
import com.groupT.Smart.Campus.Services.Portal.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler({
                UsernameNotFoundException.class,
                InvalidLoginException.class,
                NotFoundBookingsException.class,
                BookingNotFoundException.class,
                LecturerNotFoundException.class,
                FieldModificationNotAllowedException.class,
                ExpiredTokenException.class
        })
        public ResponseObject errorHandler(Exception exception){
            return new ResponseObject(exception.getMessage());
        }
}
