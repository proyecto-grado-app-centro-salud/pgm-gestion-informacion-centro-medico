package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.microservicio_informacion_centro_medico.model.ComunicadoEntity;


public interface ComunicadosRepositoryJPA extends JpaRepository<ComunicadoEntity, Integer> {
    List<ComunicadoEntity> findAllByDeletedAtIsNull();
    Optional<ComunicadoEntity> findByIdComunicadoAndDeletedAtIsNull(int idConsultorio);
}