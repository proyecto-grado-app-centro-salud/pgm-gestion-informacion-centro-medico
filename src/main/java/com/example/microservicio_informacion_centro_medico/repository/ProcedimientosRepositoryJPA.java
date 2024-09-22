package com.example.microservicio_informacion_centro_medico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import java.util.List;
import java.util.Optional;


public interface ProcedimientosRepositoryJPA extends JpaRepository<ProcedimientoEntity, Integer> {
    List<ProcedimientoEntity> findAllByDeletedAtIsNull();
    Optional<ProcedimientoEntity> findByIdProcedimientoAndDeletedAtIsNull(int idProcedimiento);

}