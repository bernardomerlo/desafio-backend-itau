package com.bernardomerlo.exceptions;

public class InvalidTimeRangeException extends RuntimeException{

    public InvalidTimeRangeException(String message) {
        super(message);
    }

    @Override
    public String getMessage(){
        return "A faixa de tempo deve ser positiva e inteira.";
    }
}
