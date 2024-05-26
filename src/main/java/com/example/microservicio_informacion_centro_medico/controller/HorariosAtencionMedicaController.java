package com.example.microservicio_informacion_centro_medico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.microservicio_informacion_centro_medico.model.HorariosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.repository.HorariosAtencionMedicaRepositoryJPA;


@Controller
@RequestMapping(path = "/horarios-atencion-medica")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HorariosAtencionMedicaController {
    @Autowired
    HorariosAtencionMedicaRepositoryJPA horariosAtencionMedicaRepositoryJPA;
    @PostMapping()
    public String registroHorarioAtencion(@RequestBody HorariosAtencionMedicaEntity horariosAtencionMedicaEntity){ 
        horariosAtencionMedicaRepositoryJPA.save(horariosAtencionMedicaEntity);
        return "OK";
    }
}
