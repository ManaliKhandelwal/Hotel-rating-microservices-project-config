package com.example.user.service.exception;

public class GenericException extends RuntimeException{

    public GenericException(){
        super("Something went wrong");
    }

    public GenericException(String message){
        super(message);
    }
}
