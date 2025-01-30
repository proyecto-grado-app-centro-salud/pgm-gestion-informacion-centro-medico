package com.example.microservicio_informacion_centro_medico.controller.publico;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.dtos.ComunicadoDto;
import com.example.microservicio_informacion_centro_medico.services.ComunicadosService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.security.PermitAll;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
@RequestMapping("/publico/v1.0/comunicados")   
public class ComunicadosPublicController {

    @Autowired      
    private ComunicadosService comunicadosService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping()
    @PermitAll
    public ResponseEntity<List<ComunicadoDto>> getComunicados(@RequestParam(required = false) String tituloComunicado,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            return new ResponseEntity<>(comunicadosService.obtenerComunicados(tituloComunicado, page, size), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{id}")
    @PermitAll
    public ResponseEntity<ComunicadoDto> getComunicadoById(@PathVariable int id) {
        try {
            ComunicadoDto comunicadoDto = comunicadosService.obtenerComunicadoPorId(id);
            return new ResponseEntity<>(comunicadoDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}