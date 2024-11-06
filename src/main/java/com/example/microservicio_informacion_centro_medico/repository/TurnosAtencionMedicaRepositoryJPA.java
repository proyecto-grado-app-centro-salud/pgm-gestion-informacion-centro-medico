package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;

public interface TurnosAtencionMedicaRepositoryJPA extends JpaRepository<TurnosAtencionMedicaEntity, Integer> {
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
}