package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoRequisitoEntity;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoRequisitoId;

public interface ProcedimientosRequisitosRepositoryJPA extends JpaRepository<ProcedimientoRequisitoEntity, ProcedimientoRequisitoId> {
    List<ProcedimientoRequisitoEntity> findByProcedimiento(ProcedimientoEntity procedimiento);
}