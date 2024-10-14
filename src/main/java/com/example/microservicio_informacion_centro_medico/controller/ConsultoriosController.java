package com.example.microservicio_informacion_centro_medico.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.ConsultorioDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.EspecialidadDto;
import com.example.microservicio_informacion_centro_medico.services.ConsultoriosService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(path = "/consultorios")
public class ConsultoriosController {

    @Autowired
    ConsultoriosService consultoriosService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping()
    public ResponseEntity<List<ConsultorioDto>> getConsultorios() {
        try{
            return new ResponseEntity<List<ConsultorioDto>>(consultoriosService.obtenerConsultorios(),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsultorioDto> getConsultorioById(@PathVariable int id) {
        try {
            ConsultorioDto consultorioDto = consultoriosService.obtenerConsultorioPorId(id);
            return new ResponseEntity<>(consultorioDto, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<ConsultorioDto> createConsultorio(@RequestParam("data") String data,
    @RequestParam Map<String, MultipartFile> allFiles) {
        try{
            ConsultorioDto consultorioDto=objectMapper.readValue(data, ConsultorioDto.class);
            ConsultorioDto createdConsultorio = consultoriosService.crearConsultorio(consultorioDto,allFiles);
            return new ResponseEntity<>(createdConsultorio, HttpStatus.OK);
    
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ConsultorioDto> updateConsultorio(@PathVariable int id, @RequestParam("data") String data,
    @RequestParam Map<String, MultipartFile> allFiles,@RequestParam Map<String, String> params) {
        try{
            ConsultorioDto consultorioDto = objectMapper.readValue(data, ConsultorioDto.class);
            ConsultorioDto updatedConsultorio = consultoriosService.actualizarConsultorio(id, consultorioDto,allFiles,params);
            return new ResponseEntity<>(updatedConsultorio, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
      
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteConsultorio(@PathVariable int id) {
        try{
            consultoriosService.eliminarConsultorio(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
