package com.example.microservicio_informacion_centro_medico.controller;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
import com.example.microservicio_informacion_centro_medico.repository.TurnosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.services.TurnosService;

import jakarta.annotation.security.PermitAll;

import com.example.microservicio_informacion_centro_medico.model.dtos.TurnoDto;


@RestController
@RequestMapping(path = "/turnos")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
public class TurnosController {
    @Autowired
    private TurnosService turnosService;

    @PostMapping
    @PermitAll
    public ResponseEntity<TurnoDto> crearTurno(@RequestBody TurnoDto turnoDto) {
        try {
            TurnoDto createdEntity = turnosService.crearTurno(turnoDto);
            return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idTurno}")
    @PermitAll
    public ResponseEntity<Void> eliminarTurno(@PathVariable int idTurno) {
        try {
            turnosService.eliminarTurno(idTurno);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<List<TurnoDto>> obtenerTodosLosTurnos(@RequestParam(required = false) String horaInicio,@RequestParam(required = false) String horaFin,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            List<TurnoDto> turnos = turnosService.obtenerTodosLosTurnos(horaInicio,horaFin,page,size);
            return new ResponseEntity<>(turnos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idTurno}")
    @PermitAll
    public ResponseEntity<TurnoDto> obtenerTurnoPorId(@PathVariable int idTurno) {
        try {
            TurnoDto turno = turnosService.obtenerTurnoPorId(idTurno);
            return new ResponseEntity<>(turno, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idTurno}")
    @PermitAll
    public ResponseEntity<TurnoDto> actualizarTurno(@PathVariable int idTurno, @RequestBody TurnoDto turnoDto) {
        try {
            TurnoDto updatedEntity = turnosService.actualizarTurno(idTurno, turnoDto);
            return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
