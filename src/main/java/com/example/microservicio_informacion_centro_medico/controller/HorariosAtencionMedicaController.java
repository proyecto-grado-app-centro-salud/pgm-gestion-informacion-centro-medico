package com.example.microservicio_informacion_centro_medico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.microservicio_informacion_centro_medico.model.HorariosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.repository.HorariosAtencionMedicaRepositoryJPA;


@Controller
@RequestMapping(path = "/horarios-atencion-medica")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HorariosAtencionMedicaController {
    @Autowired
    HorariosAtencionMedicaRepositoryJPA horariosAtencionMedicaRepositoryJPA;
    @PostMapping()
    public String registroHorarioAtencion(HorariosAtencionMedicaEntity horariosAtencionMedicaEntity){ 
        horariosAtencionMedicaRepositoryJPA.save(horariosAtencionMedicaEntity);
        return "OK";
    }
    @GetMapping("/{id}")
    public @ResponseBody HorariosAtencionMedicaEntity obtenerHorarioAtencion(@PathVariable int idHorariosAtencionMedica){
        return horariosAtencionMedicaRepositoryJPA.findById(idHorariosAtencionMedica)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Horario atencion no encontrado"));
    }
    @DeleteMapping()
    public String eliminarHorarioAtencion(@PathVariable int idHorariotencionMedica){ 
        horariosAtencionMedicaRepositoryJPA.deleteById(idHorariotencionMedica);
        return "OK";
    }
    @GetMapping()
    public @ResponseBody List<HorariosAtencionMedicaEntity> obtenerHorariosAtencion(){ 
        return horariosAtencionMedicaRepositoryJPA.findAll();
    }
}
