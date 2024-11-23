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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.dtos.ComunicadoDto;
import com.example.microservicio_informacion_centro_medico.services.ComunicadosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping()
public class ComunicadosController {

    @Autowired
    private ComunicadosService comunicadosService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/v1.0/comunicados")
    public ResponseEntity<List<ComunicadoDto>> getComunicados(@RequestParam(required = false) String tituloComunicado,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            return new ResponseEntity<>(comunicadosService.obtenerComunicados(tituloComunicado, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/v1.0/comunicados/{id}")
    public ResponseEntity<ComunicadoDto> getComunicadoById(@PathVariable int id) {
        try {
            ComunicadoDto comunicadoDto = comunicadosService.obtenerComunicadoPorId(id);
            return new ResponseEntity<>(comunicadoDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/v1.0/comunicados")
    public ResponseEntity<ComunicadoDto> createComunicado(@RequestParam("data") String data,@RequestParam Map<String, MultipartFile> allFiles) {
        try {
            ComunicadoDto comunicadoDto = objectMapper.readValue(data, ComunicadoDto.class);
            ComunicadoDto createdComunicado = comunicadosService.crearComunicado(comunicadoDto,allFiles);
            return new ResponseEntity<>(createdComunicado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/v1.0/comunicados/{id}")
    public ResponseEntity<ComunicadoDto> updateComunicado(@PathVariable int id, @RequestParam("data") String data,
    @RequestParam Map<String, MultipartFile> allFiles,@RequestParam Map<String, String> params) {
        try {
            ComunicadoDto comunicadoDto = objectMapper.readValue(data, ComunicadoDto.class);
            ComunicadoDto updatedComunicado = comunicadosService.actualizarComunicado(id, comunicadoDto,allFiles,params);
            return new ResponseEntity<>(updatedComunicado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/v1.0/comunicados/{id}")
    public ResponseEntity<Void> deleteComunicado(@PathVariable int id) {
        try {
            comunicadosService.eliminarComunicado(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}