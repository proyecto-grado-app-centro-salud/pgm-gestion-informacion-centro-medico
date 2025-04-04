package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoPasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoRequisitoEntity;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoPasoId;

public interface ProcedimientosElementosPasosRepositoryJPA extends JpaRepository<ProcedimientoElementoPasoEntity, Integer> {
    List<ProcedimientoElementoPasoEntity> findByProcedimientoElemento(ProcedimientoElementoEntity procedimientoElemento);
    Optional<ProcedimientoElementoPasoEntity> findOneByProcedimientoElementoAndPaso(ProcedimientoElementoEntity procedimientoElemento,PasoEntity pasoEntity);
}