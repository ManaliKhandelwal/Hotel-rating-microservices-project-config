package com.example.user.service.exception.handler;

import com.example.user.service.exception.ExceptionHandlerStrategy;
import com.example.user.service.exception.ResourceNotFoundException;
import com.example.user.service.payload.ErrorResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public class ResourceNotFoundExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public boolean canHandle(Exception ex) {
        return ex instanceof ResourceNotFoundException;
    }

    @Override
    public ErrorResponses handler(Exception ex, WebRequest request) {
        return new ErrorResponses().builder()
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errorCode("RESOURCE_NOT_FOUND")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
    }
}
