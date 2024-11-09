package com.example.microservicio_informacion_centro_medico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_informacion_centro_medico.model.MedicoEntity;
import com.example.microservicio_informacion_centro_medico.repository.MedicoRepository;

@RestController
@RequestMapping(path="/medicos")
public class MedicosController {
    @Autowired
    MedicoRepository medicoRepository;

    @GetMapping
    public ResponseEntity<List<MedicoEntity>> listadoMedicos() {
        List<MedicoEntity> listadoMedico=medicoRepository.findAll();
        return new ResponseEntity<List<MedicoEntity>>(listadoMedico, HttpStatus.OK);
    }
}
