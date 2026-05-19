package com.example.user.service.exception.handler;

import com.example.user.service.exception.ExceptionHandlerStrategy;
import com.example.user.service.exception.GenericException;
import com.example.user.service.payload.ErrorResponses;
import org.springframework.web.context.request.WebRequest;

public class GenericExceptionHandler implements ExceptionHandlerStrategy {


    @Override
    public boolean canHandle(Exception ex) {
        return true;
    }

    @Override
    public ErrorResponses handler(Exception ex, WebRequest request) {
        return new ErrorResponses().builder()
                .error("Internal Server Error")
                .errorCode("GENERIC_ERROR")
                .message("An unexpected error occurred: " + ex.getMessage())
                .status(500)
                .timestamp(java.time.LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

    }
}
