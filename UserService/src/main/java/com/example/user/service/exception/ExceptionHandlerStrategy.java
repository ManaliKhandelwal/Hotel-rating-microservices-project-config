package com.example.user.service.exception;


import com.example.user.service.payload.ErrorResponses;
import org.springframework.web.context.request.WebRequest;

public interface ExceptionHandlerStrategy {
    boolean canHandle(Exception ex);
    ErrorResponses handler(Exception ex , WebRequest request);

}
