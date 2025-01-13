package com.example.microservicio_informacion_centro_medico.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_informacion_centro_medico.model.MedicoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.EspecialidadDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.MedicoDto;
import com.example.microservicio_informacion_centro_medico.repository.MedicoRepository;
import com.example.microservicio_informacion_centro_medico.services.MedicosService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping(path="/medicos")
public class MedicosController {
    Logger logger = LoggerFactory.getLogger(MedicosController.class);
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    MedicosService medicosService;
    @GetMapping
    @PermitAll
    public ResponseEntity<List<MedicoDto>> listadoMedicos(@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
             return new ResponseEntity<>(medicosService.obtenerEquipoMedico(nombreMedico,page, size), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/especialidades/{idEspecialidad}")
    @PermitAll
    public ResponseEntity<List<MedicoDto>> listadoMedicosPorEspecialidad(@PathVariable(value = "idEspecialidad") Integer idEspecialidad,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin) {
        try{
            fechaInicio=fechaInicio==null?LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):fechaInicio;
            fechaFin=fechaFin==null?LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):fechaFin;
            logger.info(fechaInicio+" "+fechaFin);
            List<MedicoDto> listadoMedico=medicosService.obtenerMedicosEspecialitasPorEspecialidad(idEspecialidad,fechaInicio,fechaFin);
            return new ResponseEntity<List<MedicoDto>>(listadoMedico, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
