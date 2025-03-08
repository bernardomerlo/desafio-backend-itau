package com.bernardomerlo.exceptions;


public class InvalidValueException extends RuntimeException{

    public InvalidValueException(String message) {
        super(message);
    }

    @Override
    public String getMessage(){
        return "Valor tem que ser maior ou igual a 0.";
    }
}
