package com.example.microservicio_informacion_centro_medico.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_informacion_centro_medico.model.ConsultoriosEntity;
import com.example.microservicio_informacion_centro_medico.repository.ConsultoriosRepositoryJPA;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/consultorios")
public class ConsultoriosController {

    @Autowired
    ConsultoriosRepositoryJPA consultoriosRepositoryJPA;

    @GetMapping()
    public List<Object> getConsultorios() {
        return consultoriosRepositoryJPA.obtenerConsultorios();
        //return Collections.emptyList();
    }
    
}
