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
import com.example.microservicio_informacion_centro_medico.services.PasosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/v1.0/pasos")
public class PasosController {

    @Autowired
    private PasosService pasosService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<List<PasoDto>> getPasos() {
        try {
            return new ResponseEntity<>(pasosService.obtenerPasos(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasoDto> getPasoById(@PathVariable int id) {
        try {
            PasoDto pasoDto = pasosService.obtenerPasoPorId(id);
            return new ResponseEntity<>(pasoDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PasoDto> createPaso(@RequestParam("data") String data,
        @RequestParam Map<String, MultipartFile> allFiles) {
        try {
            PasoDto pasoDto = objectMapper.readValue(data, PasoDto.class);
            PasoDto createdPaso = pasosService.crearPaso(pasoDto, allFiles);
            return new ResponseEntity<>(createdPaso, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasoDto> updatePaso(@PathVariable int id, @RequestParam("data") String data,
        @RequestParam Map<String, MultipartFile> allFiles,@RequestParam Map<String, String> params) {
        try {
            PasoDto pasoDto = objectMapper.readValue(data, PasoDto.class);
            PasoDto updatedPaso = pasosService.actualizarPaso(id, pasoDto, allFiles,params);
            return new ResponseEntity<>(updatedPaso, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaso(@PathVariable int id) {
        try {
            pasosService.eliminarPaso(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}