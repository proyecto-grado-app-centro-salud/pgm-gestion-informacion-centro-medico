package com.example.microservicio_informacion_centro_medico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.microservicio_informacion_centro_medico.model.ConsultoriosEntity;
import com.example.microservicio_informacion_centro_medico.repository.ConsultoriosRepositoryJPA;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/consultorios")
public class ConsultoriosController {

    @Autowired
    ConsultoriosRepositoryJPA consultoriosRepositoryJPA;

    @GetMapping()
    public List<ConsultoriosEntity> getConsultorios() {
        return consultoriosRepositoryJPA.findAll();
    }
    
}
