package com.example.microservicio_informacion_centro_medico.controller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.EspecialidadDto;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.services.EspecialidadesService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.security.PermitAll;

import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/especialidades")
public class EspecialidadesController {
    
    @Autowired
    EspecialidadesService especialidadesService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    @PermitAll
    public ResponseEntity<List<EspecialidadDto>> getEspecialidades(@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
             return new ResponseEntity<>(especialidadesService.obtenerEspecialidades(nombreEspecialidad,page, size), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @PermitAll
    public ResponseEntity<EspecialidadDto> getEspecialidadById(@PathVariable int id) {
        try {
            EspecialidadDto especialidadDto = especialidadesService.obtenerEspecialidadPorId(id);
            if (especialidadDto != null) {
                return new ResponseEntity<>(especialidadDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping()
    @PermitAll
    public ResponseEntity<EspecialidadDto> createEspecialidad(@RequestParam("data") String data,
    @RequestParam Map<String, MultipartFile> allFiles) {
        try {
            EspecialidadDto especialidadDto = objectMapper.readValue(data, EspecialidadDto.class);
            EspecialidadDto createdEspecialidad = especialidadesService.crearEspecialidad(especialidadDto, allFiles);
            return new ResponseEntity<>(createdEspecialidad, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    @PermitAll
    public ResponseEntity<EspecialidadDto> updateEspecialidad(@PathVariable int id,@RequestParam("data") String data,
    @RequestParam Map<String, MultipartFile> allFiles,@RequestParam Map<String, String> params) {
        try {
            EspecialidadDto especialidadDto = objectMapper.readValue(data, EspecialidadDto.class);
            EspecialidadDto updatedEspecialidad = especialidadesService.actualizarEspecialidad(id,especialidadDto,allFiles,params);
            return new ResponseEntity<>(updatedEspecialidad, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PermitAll
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable int id) {
        try{
            especialidadesService.deleteEspecialidad(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
