package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoPasoEntity;
import com.example.microservicio_informacion_centro_medico.model.RequisitoEntity;

public interface RequisitosRepositoryJPA extends JpaRepository<RequisitoEntity, Integer> {
    List<RequisitoEntity> findAllByDeletedAtIsNull();
    Optional<RequisitoEntity> findByIdRequisitoAndDeletedAtIsNull(int idRequisito);

}