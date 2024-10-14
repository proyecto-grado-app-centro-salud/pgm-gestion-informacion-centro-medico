package com.example.microservicio_informacion_centro_medico.controller;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoDto;
import com.example.microservicio_informacion_centro_medico.services.ProcedimientosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/v1.0/procedimientos")
public class ProcedimientosController {

    @Autowired
    private ProcedimientosService procedimientosService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(ProcedimientosController.class);

    @GetMapping
    public ResponseEntity<List<ProcedimientoDto>> getProcedimientos() {
        try {
            return new ResponseEntity<>(procedimientosService.obtenerProcedimientos(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedimientoDto> getProcedimientoById(@PathVariable int id) {
        try {
            ProcedimientoDto procedimientoDto = procedimientosService.obtenerProcedimientoPorId(id);
            return new ResponseEntity<>(procedimientoDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ProcedimientoDto> createProcedimiento(@RequestParam("data") String data,
        @RequestParam Map<String, MultipartFile> allFiles) {
        try {
            ProcedimientoDto procedimientoDto = objectMapper.readValue(data, ProcedimientoDto.class);
            ProcedimientoDto createdProcedimiento = procedimientosService.crearProcedimiento(procedimientoDto, allFiles);
            return new ResponseEntity<>(createdProcedimiento, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcedimientoDto> updateProcedimiento(@PathVariable int id, @RequestParam("data") String data,
        @RequestParam Map<String, MultipartFile> allFiles, @RequestParam Map<String, String> params) {
        try {
            ProcedimientoDto procedimientoDto = objectMapper.readValue(data, ProcedimientoDto.class);
            ProcedimientoDto updatedProcedimiento = procedimientosService.actualizarProcedimiento(id, procedimientoDto, allFiles, params);
            return new ResponseEntity<>(updatedProcedimiento, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcedimiento(@PathVariable int id) {
        try {
            procedimientosService.eliminarProcedimiento(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}