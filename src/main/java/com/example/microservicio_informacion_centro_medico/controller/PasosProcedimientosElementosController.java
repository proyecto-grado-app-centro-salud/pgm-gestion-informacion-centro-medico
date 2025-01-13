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
import com.example.microservicio_informacion_centro_medico.services.PasosProcedimientosElementosService;
import com.example.microservicio_informacion_centro_medico.services.ProcedimientosService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/v1.0/procedimientos")
public class PasosProcedimientosElementosController {
    @Autowired
    private PasosProcedimientosElementosService pasosProcedimientosService;
    private static final Logger logger = LoggerFactory.getLogger(PasosProcedimientosElementosController.class);
    @GetMapping(value = "/{idProcedimiento}/elementos/{idElemento}/tipo-elemento/{tipoElemento}/pasos")
    @PermitAll
    public ResponseEntity<List<PasoDto>> getPasosProcedimiento(@PathVariable int idProcedimiento,@PathVariable int idElemento,@PathVariable String tipoElemento) {
        try {
            return new ResponseEntity<>(pasosProcedimientosService.obtenerPasosDeProcedimiento(idProcedimiento,idElemento,tipoElemento), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            new RuntimeException(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/{idProcedimiento}/elementos/{idElemento}/tipo-elemento/{tipoElemento}/pasos/{idPaso}")
    @PermitAll
    public ResponseEntity<Void> createPasoProcedimientoElemento(@PathVariable int idProcedimiento,@PathVariable int idPaso,@PathVariable int idElemento,@PathVariable String tipoElemento) {
        try {
            pasosProcedimientosService.crearPasoProcedimientoElemento(idProcedimiento, idPaso, idElemento, tipoElemento);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idProcedimiento}/elementos/{idElemento}/tipo-elemento/{tipoElemento}/pasos/{idPaso}")
    @PermitAll
    public ResponseEntity<Void> deletePasoProcedimientoElemento(@PathVariable int idProcedimiento,@PathVariable int idPaso,@PathVariable int idElemento,@PathVariable String tipoElemento) {
        try {
            pasosProcedimientosService.eliminarPasoProcedimientoElemento(idProcedimiento,idPaso,idElemento,tipoElemento);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
