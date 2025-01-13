package com.example.microservicio_informacion_centro_medico.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.RequisitoDto;
import com.example.microservicio_informacion_centro_medico.services.PasosService;
import com.example.microservicio_informacion_centro_medico.services.RequisitosService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/v1.0/requisitos")
public class RequisitosController {

    @Autowired
    private RequisitosService requisitosService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    @PermitAll
    public ResponseEntity<List<RequisitoDto>> getRequisitos() {
        try {
            return new ResponseEntity<>(requisitosService.obtenerRequisitos(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<RequisitoDto> getRequisitoById(@PathVariable int id) {
        try {
            RequisitoDto requisitoDto = requisitosService.obtenerRequisitoPorId(id);
            return new ResponseEntity<>(requisitoDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<RequisitoDto> createPaso(@RequestParam("data") String data,
        @RequestParam Map<String, MultipartFile> allFiles) {
        try {
            RequisitoDto requisitoDto = objectMapper.readValue(data, RequisitoDto.class);
            RequisitoDto createdRequisito = requisitosService.crearRequisito(requisitoDto, allFiles);
            return new ResponseEntity<>(createdRequisito, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PermitAll
    public ResponseEntity<RequisitoDto> updatePaso(@PathVariable int id, @RequestParam("data") String data,
        @RequestParam Map<String, MultipartFile> allFiles,@RequestParam Map<String, String> params) {
        try {
            RequisitoDto requisitoDto = objectMapper.readValue(data, RequisitoDto.class);
            RequisitoDto updatedRequisito = requisitosService.actualizarRequisito(id, requisitoDto, allFiles,params);
            return new ResponseEntity<>(updatedRequisito, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PermitAll
    public ResponseEntity<Void> deletePaso(@PathVariable int id) {
        try {
            requisitosService.eliminarRequisito(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}