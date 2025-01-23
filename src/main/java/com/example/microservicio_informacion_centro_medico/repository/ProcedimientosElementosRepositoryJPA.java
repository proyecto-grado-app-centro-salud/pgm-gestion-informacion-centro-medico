package com.example.microservicio_informacion_centro_medico.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoElementoDto;

public interface ProcedimientosElementosRepositoryJPA extends JpaRepository<ProcedimientoElementoEntity, Integer> {

    @Query("SELECT pe FROM ProcedimientoElementoEntity pe " +
           "JOIN ProcedimientoEntity pa ON pa.idProcedimiento = pe.procedimiento.idProcedimiento " +
           "WHERE pe.tipoElemento = :tipoElemento " +
           "AND pe.idElemento = :idElemento " +
           "AND pa.deletedAt IS NULL")
    List<ProcedimientoElementoEntity> findByTipoElementoAndIdElemento(
        @Param("tipoElemento") String tipoElemento,
        @Param("idElemento") Integer idElemento);


    @Query("SELECT DISTINCT pa FROM ProcedimientoElementoEntity pe " +
        "INNER JOIN pe.procedimiento pa " +
        "WHERE pe.tipoElemento = :tipoElemento " +
        "AND pa.deletedAt IS NULL")
    List<ProcedimientoEntity> obtenerProcedimientosDeTipoElemento(String tipoElemento);


    Optional<ProcedimientoElementoEntity> findOneByIdElementoAndTipoElementoAndProcedimiento(int idElemento, String tipoElemento, ProcedimientoEntity procedimiento);


    List<ProcedimientoElementoEntity> findAllByProcedimiento(ProcedimientoEntity procedimientoEntity);


    // @Query("SELECT new com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoElementoDto(pe.idProcedimientoElemento, pe.procedimiento.nombreProcedimiento, pe.procedimiento.idProcedimiento, pe.tipoElemento, pe.idElemento, " +
    //    "CASE pe.tipoElemento " +
    //    "WHEN 'especialidades' THEN e.nombre " +
    //    "WHEN 'consultorios' THEN c.nombre " +
    //    "ELSE NULL " +
    //    "END, pe.descripcion) " +
    //    "FROM ProcedimientoElementoEntity pe " +
    //    "LEFT JOIN EspecialidadEntity e ON e.idEspecialidad = pe.idElemento AND pe.tipoElemento = 'especialidades' " +
    //    "LEFT JOIN ConsultorioEntity c ON c.idConsultorio = pe.idElemento AND pe.tipoElemento = 'consultorios' " +
    //    "WHERE pe.idProcedimiento = :idProcedimiento " +
    //    "AND ((pe.tipoElemento = 'especialidades' AND e.idEspecialidad IS NOT NULL) OR " +
    //    "(pe.tipoElemento = 'consultorios' AND c.idConsultorio IS NOT NULL))")
    // List<ProcedimientoElementoDto> findAllProcedimientoElementoByIdProcedimiento(@Param("idProcedimiento") int idProcedimiento);

    @Query("SELECT new com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoElementoDto("+
    "pe.idProcedimientoElemento, pe.procedimiento.nombreProcedimiento, pe.procedimiento.idProcedimiento, pe.tipoElemento, pe.idElemento, "+
    "CASE pe.tipoElemento WHEN 'especialidades' THEN e.nombre WHEN 'consultorios' THEN c.nombre ELSE null END, pe.descripcion) " +
    "FROM ProcedimientoElementoEntity pe " +
    "LEFT JOIN EspecialidadEntity e ON e.idEspecialidad = pe.idElemento AND pe.tipoElemento = 'especialidades' " +
    "LEFT JOIN ConsultorioEntity c ON c.idConsultorio = pe.idElemento AND pe.tipoElemento = 'consultorios' "+
    "WHERE pe.procedimiento.idProcedimiento = :idProcedimiento "+
    "AND ((pe.tipoElemento = 'especialidades' AND e.idEspecialidad IS NOT NULL) OR " +
    "(pe.tipoElemento = 'consultorios' AND c.idConsultorio IS NOT NULL))")

    List<ProcedimientoElementoDto> findAllProcedimientoElementoByIdProcedimiento(@Param("idProcedimiento") int idProcedimiento);


}