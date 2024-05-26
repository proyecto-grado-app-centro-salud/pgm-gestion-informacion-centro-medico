package com.example.microservicio_informacion_centro_medico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.EspecialidadesEntity;

public interface EspecialidadesRepositoryJPA extends JpaRepository<EspecialidadesEntity, Integer> {
}