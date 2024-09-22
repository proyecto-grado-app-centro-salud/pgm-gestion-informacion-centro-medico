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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoDto;
import com.example.microservicio_informacion_centro_medico.services.PasosProcedimientosService;
import com.example.microservicio_informacion_centro_medico.services.ProcedimientosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1.0/procedimientos")
public class PasosProcedimientosController {
    @Autowired
    private PasosProcedimientosService pasosProcedimientosService;
    private static final Logger logger = LoggerFactory.getLogger(PasosProcedimientosController.class);
    @GetMapping(value = "/{idProcedimiento}/pasos")
    public ResponseEntity<List<PasoDto>> getPasosProcedimiento(@PathVariable int idProcedimiento) {
        try {
            return new ResponseEntity<>(pasosProcedimientosService.obtenerPasosDeProcedimiento(idProcedimiento), HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            new RuntimeException(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/{idProcedimiento}/pasos/{idPaso}")
    public ResponseEntity<Void> createPasoProcedimiento(@PathVariable int idProcedimiento,@PathVariable int idPaso) {
        try {
            pasosProcedimientosService.crearPasoProcedimiento(idProcedimiento, idPaso);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idProcedimiento}/pasos/{idPaso}")
    public ResponseEntity<Void> deletePasoProcedimiento(@PathVariable int idProcedimiento,@PathVariable int idPaso) {
        try {
            pasosProcedimientosService.eliminarPasoProcedimiento(idProcedimiento,idPaso);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
