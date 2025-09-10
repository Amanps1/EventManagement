package com.example.eventmanagement.exceptions;

public class UnAuthorizedAccessException  extends Exception{
    public UnAuthorizedAccessException(String message){
        super(message);
    }
}
