package com.example.microservicio_informacion_centro_medico.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoId;

public interface ProcedimientosElementosRepositoryJPA extends JpaRepository<ProcedimientoElementoEntity, Integer> {

    @Query("SELECT pe FROM ProcedimientoElementoEntity pe " +
           "JOIN ProcedimientoEntity pa ON pa.idProcedimiento = pe.procedimiento.idProcedimiento " +
           "WHERE pe.tipoElemento = :tipoElemento " +
           "AND pe.idElemento = :idElemento " +
           "AND pa.deletedAt IS NULL")
    List<ProcedimientoElementoEntity> findByTipoElementoAndIdElemento(
        @Param("tipoElemento") String tipoElemento,
        @Param("idElemento") Integer idElemento);


    Optional<ProcedimientoElementoEntity> findOneByIdElementoAndTipoElementoAndProcedimiento(int idElemento, String tipoElemento, ProcedimientoEntity procedimiento);

}