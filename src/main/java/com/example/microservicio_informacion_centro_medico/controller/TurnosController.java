package com.example.microservicio_informacion_centro_medico.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
import com.example.microservicio_informacion_centro_medico.repository.TurnosRepositoryJPA;


@RestController
@RequestMapping(path = "/turnos")
public class TurnosController {
    @Autowired
    TurnosRepositoryJPA turnosRepositoryJPA;
    @GetMapping("")
    public List<TurnoEntity> getTurnos() {
        return turnosRepositoryJPA.findAll();
        //return Collections.emptyList();
    }
    
}
