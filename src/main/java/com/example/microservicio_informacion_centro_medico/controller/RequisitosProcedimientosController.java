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
import com.example.microservicio_informacion_centro_medico.model.dtos.RequisitoDto;
import com.example.microservicio_informacion_centro_medico.services.PasosProcedimientosService;
import com.example.microservicio_informacion_centro_medico.services.ProcedimientosService;
import com.example.microservicio_informacion_centro_medico.services.RequisitosProcedimientosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1.0/procedimientos")
public class RequisitosProcedimientosController {
    @Autowired
    private RequisitosProcedimientosService requisitosProcedimientosService;
    private static final Logger logger = LoggerFactory.getLogger(RequisitosProcedimientosController.class);
    @GetMapping(value = "/{idProcedimiento}/requisitos")
    public ResponseEntity<List<RequisitoDto>> getRequisitosProcedimiento(@PathVariable int idProcedimiento) {
        try {
            return new ResponseEntity<>(requisitosProcedimientosService.obtenerRequisitosDeProcedimiento(idProcedimiento), HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            new RuntimeException(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/{idProcedimiento}/requisitos/{idRequisito}")
    public ResponseEntity<Void> createRequisitoProcedimiento(@PathVariable int idProcedimiento,@PathVariable int idRequisito) {
        try {
            requisitosProcedimientosService.crearRequisitoProcedimiento(idProcedimiento, idRequisito);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idProcedimiento}/requisitos/{idRequisito}")
    public ResponseEntity<Void> deleteRequisitoProcedimiento(@PathVariable int idProcedimiento,@PathVariable int idRequisito) {
        try {
            requisitosProcedimientosService.eliminarRequisitoProcedimiento(idProcedimiento,idRequisito);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
