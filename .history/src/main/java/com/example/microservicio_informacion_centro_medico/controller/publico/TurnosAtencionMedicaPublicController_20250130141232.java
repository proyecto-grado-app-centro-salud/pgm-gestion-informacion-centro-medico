package com.example.microservicio_informacion_centro_medico.controller.publico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_informacion_centro_medico.model.dtos.TurnoAtencionMedicaDto;
import com.example.microservicio_informacion_centro_medico.services.TurnosAtencionMedicaService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping(path = "/publico/horarios-atencion-medica")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
public class TurnosAtencionMedicaPublicController {
    @Autowired
    private TurnosAtencionMedicaService turnosAtencionMedicaService;

    @GetMapping()
    @PermitAll
    public ResponseEntity<List<TurnoAtencionMedicaDto>> obtenerHorariosAtencionDetalle(@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size){ 
        try {
            List<TurnoAtencionMedicaDto> detalles = turnosAtencionMedicaService.obtenerHorariosAtencionDetalle(fechaInicio,fechaFin,page,size);
            return new ResponseEntity<>(detalles, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/especialidades/{idEspecialidad}")
    @PermitAll
    public ResponseEntity<List<TurnoAtencionMedicaDto>> obtenerTurnosAtencionMedicaDeEspecialidad(@PathVariable int idEspecialidad,@RequestParam(required = false) String fechaInicio,@RequestParam(required = false) String fechaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size){
        try {

            List<TurnoAtencionMedicaDto> detalles = turnosAtencionMedicaService.obtenerTurnosAtencionMedicaPorEspecialidad(idEspecialidad,fechaInicio,fechaFin,page,size);
            return new ResponseEntity<>(detalles, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
