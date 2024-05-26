package com.example.microservicio_informacion_centro_medico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.microservicio_informacion_centro_medico.model.EspecialidadesEntity;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/especialidades")
public class EspecialidadesController {
    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;;
    @GetMapping()
    public List<EspecialidadesEntity> getEspecialidades() {
        return especialidadesRepositoryJPA.findAll();
    }
    
}
