package com.example.microservicio_informacion_centro_medico.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_informacion_centro_medico.model.dtos.ComunicadoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoElementoDto;
import com.example.microservicio_informacion_centro_medico.services.PasosProcedimientosElementosService;
import com.example.microservicio_informacion_centro_medico.services.ProcedimientosElementosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/v1.0/procedimientos")
public class ProcedimientosElementosController {
    @Autowired
    private ProcedimientosElementosService procedimientosElementosService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(PasosProcedimientosElementosController.class);
    @GetMapping(value = "/elementos/{idElemento}/tipo-elemento/{tipoElemento}")
    public ResponseEntity<List<ProcedimientoDto>> getProcedimientosDeElemento(@PathVariable int idElemento,@PathVariable String tipoElemento) {
        try {
            return new ResponseEntity<>(procedimientosElementosService.obtenerProcedimientosDeElemento(idElemento,tipoElemento), HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            new RuntimeException(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{idProcedimiento}/elementos/{idElemento}/tipo-elemento/{tipoElemento}")
    public ResponseEntity<ProcedimientoDto> getProcedimientoDeElemento(@PathVariable int idProcedimiento,@PathVariable int idElemento,@PathVariable String tipoElemento) {
        try {
            return new ResponseEntity<>(procedimientosElementosService.obtenerProcedimientoDeElemento(idProcedimiento,idElemento,tipoElemento), HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            new RuntimeException(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/{idProcedimiento}/tipo-elemento/{tipoElemento}/elementos/{idElemento}")
    public ResponseEntity<Void> createProcedimientoElemento(@PathVariable int idProcedimiento,@PathVariable int idElemento,@PathVariable String tipoElemento, @RequestParam("data") String data) {
        try {
            ProcedimientoElementoDto procedimientoElementoDto = objectMapper.readValue(data, ProcedimientoElementoDto.class);
            procedimientosElementosService.crearProcedimientoElemento(idProcedimiento, idElemento, tipoElemento,procedimientoElementoDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idProcedimiento}/tipo-elemento/{tipoElemento}/elementos/{idElemento}")
    public ResponseEntity<Void> deleteProcedimientoElemento(@PathVariable int idProcedimiento,@PathVariable int idElemento,@PathVariable String tipoElemento) {
        try {
            procedimientosElementosService.eliminarProcedimientoElemento(idProcedimiento, idElemento, tipoElemento);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
