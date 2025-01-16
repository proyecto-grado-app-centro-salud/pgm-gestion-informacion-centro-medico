package com.example.microservicio_informacion_centro_medico.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.microservicio_informacion_centro_medico.model.ComunicadoEntity;
import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;

public interface TurnosAtencionMedicaRepositoryJPA extends JpaRepository<TurnosAtencionMedicaEntity, Integer>, JpaSpecificationExecutor<TurnosAtencionMedicaEntity> {
    // @Query(value="SELECT tam.id_turno_atencion_medica,c.nombre,t.nombre,m.nombre,e.nombre,tam.fecha FROM turnos_atencion_medica tam"+
    // " INNER JOIN consultorios c ON tam.id_consultorio =c.id_consultorio"+
    // " INNER JOIN turnos t ON tam.id_turno =t.id_turno"+
    // " INNER JOIN medicos m ON tam.id_medico =m.id_medico"+
    // " INNER JOIN especialidades e ON c.id_especialidad  =e.id_especialidad",nativeQuery=true)
    // List<Object> obtenerListaHorariosAtencionDetalle();

    // @Query(value="SELECT tam.id_turno_atencion_medica,c.nombre,t.nombre,m.nombre,e.nombre FROM turnos_atencion_medica tam"+
    // " INNER JOIN consultorios c ON tam.id_consultorio =c.id_consultorio"+
    // " INNER JOIN turnos t ON tam.id_turno =t.id_turno"+
    // " INNER JOIN medicos m ON tam.id_medico =m.id_medico"+
    // " INNER JOIN especialidades e ON c.id_especialidad  =e.id_especialidad"+
    // " WHERE tam.id_turno_atencion_medica =?1",nativeQuery=true)
    // List<Object> obtenerHorarioAtencionDetalle(int idHorarioAtencion);

    
   //@Query(value = "SELECT tam FROM TurnosAtencionMedicaEntity tam"+
    // " INNER JOIN tam.consultorio c"+
    // " INNER JOIN c.especialidad e"+
    // " WHERE e.idEspecialidad=?1"
    // )
    // List<TurnosAtencionMedicaEntity> findAllByIdEspecialidad(int idEspecialidad);

     @Query(value = "SELECT m FROM TurnosAtencionMedicaEntity tam"+
     " INNER JOIN tam.consultorio c"+
     " INNER JOIN tam.medico m"+
     " INNER JOIN c.especialidad e"+
     " WHERE e.idEspecialidad=?1 "+
     " AND tam.fecha>=?2"+
     " AND tam.fecha<=?3"+
     " AND tam.deletedAt IS NULL"
     )
     List<UsuarioEntity> findAllMedicosDeEspecialidadEnTurnosDeAtencionMedica(int idEspecialidad, LocalDate fecha, LocalDate fin);

    List<TurnosAtencionMedicaEntity> findAllByConsultorioAndDeletedAtIsNull(ConsultorioEntity consultorio);

    Optional<TurnosAtencionMedicaEntity> findByIdTurnoAtencionMedicaAndDeletedAtIsNull(int idHorariosAtencionMedica);
}