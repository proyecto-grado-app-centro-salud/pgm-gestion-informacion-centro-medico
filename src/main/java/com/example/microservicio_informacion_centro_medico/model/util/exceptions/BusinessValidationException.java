package com.example.microservicio_informacion_centro_medico.model.util.exceptions;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessValidationException extends RuntimeException{
    private String errorMessage;
    public BusinessValidationException(String message){
        super(message);
        setErrorMessage(message);
    }
}