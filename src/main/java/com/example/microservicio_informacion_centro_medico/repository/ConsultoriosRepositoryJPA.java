package com.example.microservicio_informacion_centro_medico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.ConsultoriosEntity;

public interface ConsultoriosRepositoryJPA extends JpaRepository<ConsultoriosEntity, Integer> {
}