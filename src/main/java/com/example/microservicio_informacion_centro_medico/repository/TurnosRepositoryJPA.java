package com.example.microservicio_informacion_centro_medico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;

public interface TurnosRepositoryJPA extends JpaRepository<TurnoEntity, Integer>,JpaSpecificationExecutor<TurnoEntity> {

    Optional<TurnoEntity> findByIdTurnoAndDeletedAtIsNull(Integer idTurno);
}