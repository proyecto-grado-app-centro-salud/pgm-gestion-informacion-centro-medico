package com.example.microservicio_informacion_centro_medico.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.TurnoAtencionMedicaDto;
import com.example.microservicio_informacion_centro_medico.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.services.TurnosAtencionMedicaService;
import com.example.microservicio_informacion_centro_medico.util.ApiResponse;

import jakarta.annotation.security.PermitAll;


@RestController
@RequestMapping(path = "/horarios-atencion-medica")
public class TurnosAtencionMedicaController {
    @Autowired
    TurnosAtencionMedicaService turnosAtencionMedicaService;
    @PostMapping
    @PermitAll
    public ResponseEntity<TurnoAtencionMedicaDto> registroHorarioAtencion(@RequestBody TurnoAtencionMedicaDto horariosAtencionMedicaEntity) throws Exception {
        TurnoAtencionMedicaDto createdEntity = turnosAtencionMedicaService.crearHorarioAtencion(horariosAtencionMedicaEntity);
        return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
    }
    // @GetMapping("/{idHorariosAtencionMedica}")
    // public @ResponseBody HorariosAtencionMedicaEntity obtenerHorarioAtencion(@PathVariable int idHorariosAtencionMedica){
    //     return horariosAtencionMedicaRepositoryJPA.findById(idHorariosAtencionMedica)
    //     .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Horario atencion no encontrado"));
    // }
    @DeleteMapping("/{idHorariosAtencionMedica}")
    @PermitAll
    public ResponseEntity<Void> eliminarHorarioAtencion(@PathVariable int idHorariosAtencionMedica){ 
        try {
            turnosAtencionMedicaService.eliminarHorarioAtencion(idHorariosAtencionMedica);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // @GetMapping()
    // public @ResponseBody List<HorariosAtencionMedicaEntity> obtenerHorariosAtencion(){ 
    //     return horariosAtencionMedicaRepositoryJPA.findAll();
    // }
    @GetMapping()
    @PermitAll
    public ResponseEntity<List<TurnoAtencionMedicaDto>> obtenerHorariosAtencionDetalle(@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size){ 
        try {
            List<TurnoAtencionMedicaDto> detalles = turnosAtencionMedicaService.obtenerHorariosAtencionDetalle(fechaInicio,fechaFin,page,size);
            return new ResponseEntity<>(detalles, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{idHorariosAtencionMedica}")
    @PermitAll
    public ResponseEntity<TurnoAtencionMedicaDto>  obtenerHorarioAtencionDetalle(@PathVariable int idHorariosAtencionMedica){ 
        try {
            TurnoAtencionMedicaDto detalle = turnosAtencionMedicaService.obtenerHorarioAtencionDetalle(idHorariosAtencionMedica);
            return new ResponseEntity<>(detalle, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    Logger logger = LoggerFactory.getLogger(TurnosAtencionMedicaService.class);

    @GetMapping("/especialidades/{idEspecialidad}")
    @PermitAll
    public ResponseEntity<List<TurnoAtencionMedicaDto>> obtenerTurnosAtencionMedicaDeEspecialidad(@PathVariable int idEspecialidad,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size){
        try {

            List<TurnoAtencionMedicaDto> detalles = turnosAtencionMedicaService.obtenerTurnosAtencionMedicaPorEspecialidad(idEspecialidad,fechaInicio,fechaFin,page,size);
            return new ResponseEntity<>(detalles, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
