package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoPasoEntity;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoPasoId;

public interface ProcedimientosPasosRepositoryJPA extends JpaRepository<ProcedimientoPasoEntity, ProcedimientoPasoId> {
    List<ProcedimientoPasoEntity> findByProcedimiento(ProcedimientoEntity procedimiento);
}