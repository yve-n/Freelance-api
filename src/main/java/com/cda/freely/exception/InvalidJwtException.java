package com.cda.freely.exception;

public class InvalidJwtException extends RuntimeException{
    public InvalidJwtException(String message) {
        super(message);
    }
}
