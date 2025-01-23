package com.example.microservicio_informacion_centro_medico.configurations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.microservicio_informacion_centro_medico.model.util.exceptions.BusinessValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<String> handleBusinessValidationException(BusinessValidationException businessValidationException){
        businessValidationException.printStackTrace();
        return new ResponseEntity<>(businessValidationException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>("Ha ocurrido un error inesperado en el servidor.",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
