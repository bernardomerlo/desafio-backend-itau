package com.bernardomerlo.exceptions;

public class NullValuesException extends RuntimeException{

    public NullValuesException(String message){
        super(message);
    }

    @Override
    public String getMessage(){
        return "Valores não podem ser vazios";
    }
}
