package com.bernardomerlo.exceptions;

public class InvalidTimeException extends RuntimeException{

    public InvalidTimeException(String message){
        super(message);
    }

    @Override
    public String getMessage(){
        return "Transações só podem ocorrer no passado.";
    }
}
