package com.example.microservicio_informacion_centro_medico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;

public interface EspecialidadesRepositoryJPA extends JpaRepository<EspecialidadEntity, Integer> ,JpaSpecificationExecutor<EspecialidadEntity>{
    List<EspecialidadEntity> findAllByDeletedAtIsNull();
    Optional<EspecialidadEntity> findByIdEspecialidadAndDeletedAtIsNull(int idEspecialidad);

    // @Query(value="SELECT DISTINCT e FROM TurnoAtencionMedicaEntity tam INNER JOIN tam.consultorio c INNER JOIN c.especialidad e INNER JOIN tam.medico m where m.idUsuario=?1")
    // List<EspecialidadEntity> obtenerEspecialidadesDeMedico(String idMedico);

    @Query(value="SELECT DISTINCT e FROM TurnosAtencionMedicaEntity tam " +
                 "INNER JOIN tam.consultorio c " +
                 "INNER JOIN c.especialidad e " +
                 "INNER JOIN tam.medico m " +
                 "WHERE m.idUsuario=?1 " +
                 "AND tam.fecha>=?2 " +
                 "AND tam.fecha<=?3")
    List<EspecialidadEntity> obtenerEspecialidadesDeMedico(String idMedico, LocalDate fechaInicio, LocalDate fechaFin);

}