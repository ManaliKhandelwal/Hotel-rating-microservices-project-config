package com.example.user.service.exception;

import com.example.user.service.exception.handler.GenericExceptionHandler;
import com.example.user.service.exception.handler.ResourceNotFoundExceptionHandler;
import com.example.user.service.payload.ErrorResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class GlobalException {

    List<ExceptionHandlerStrategy> strategyList = List.of(
            new ResourceNotFoundExceptionHandler() ,
            new GenericExceptionHandler()
    );

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponses> handleAllExceptions(Exception ex , WebRequest request){

        for(ExceptionHandlerStrategy strategy : strategyList){
            if(strategy.canHandle(ex)){
                    ErrorResponses errorResponse = strategy.handler(ex,request);
                    return  ResponseEntity.status(errorResponse.getStatus())
                            .body(errorResponse);
            }
        }

        ErrorResponses fallbackError = ErrorResponses.builder()
                .status(500)
                .error("Internal Server Error")
                .message("An unexpected error occurred: " + ex.getMessage())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(500).body(fallbackError);
    }



}
