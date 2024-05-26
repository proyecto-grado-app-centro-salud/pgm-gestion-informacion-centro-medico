package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.HorariosAtencionMedicaEntity;

public interface HorariosAtencionMedicaRepositoryJPA extends JpaRepository<HorariosAtencionMedicaEntity, Integer> {
}