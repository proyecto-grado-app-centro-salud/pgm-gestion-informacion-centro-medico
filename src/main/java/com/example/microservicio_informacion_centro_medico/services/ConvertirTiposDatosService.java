package com.example.microservicio_informacion_centro_medico.services;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;

@Service
public class ConvertirTiposDatosService {
    public Date convertirStringADate(String fecha) {
        if (fecha == null) {
            return null;
        }
        Date fechaDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         try {
            fechaDate = format.parse(fecha);
            return fechaDate;

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir la fecha.");
        }
    }
    public LocalDate  convertirStringALocalDate(String fecha) {
        if (fecha == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(fecha, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir la fecha. Formato esperado: yyyy-MM-dd.");
        }
    }
    public String convertirLocalDateAString(LocalDate fecha) {
        if (fecha == null) {
            return null; 
        }
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        return fecha.format(formatter);
    }
    public String convertirLocalTimeAString(LocalTime hora) {
        if (hora == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return hora.format(formatter);
    }
    public LocalTime convertirStringALocalTime(String hora) {
        if (hora == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(hora, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir la hora.");
        }
    }
}
