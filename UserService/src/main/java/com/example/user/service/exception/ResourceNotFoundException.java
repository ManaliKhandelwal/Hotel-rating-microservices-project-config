package com.example.user.service.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("resource not found");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
