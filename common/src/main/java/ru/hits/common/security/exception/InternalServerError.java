package ru.hits.common.security.exception;

public class InternalServerError extends RuntimeException{
    public InternalServerError(String message){
        super(message);
    }
}
