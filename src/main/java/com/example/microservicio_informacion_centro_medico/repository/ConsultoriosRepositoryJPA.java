package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;

public interface ConsultoriosRepositoryJPA extends JpaRepository<ConsultorioEntity, Integer> {
    @Query(value="SELECT c.id_consultorio,c.nombre,c.direccion,c.equipamiento,e.nombre from consultorios c "+
    "INNER JOIN especialidades e ON c.id_especialidad  = e.id_especialidad ",nativeQuery=true)
    List<Object> obtenerConsultorios();
    List<ConsultorioEntity> findAllByDeletedAtIsNull();
    Optional<ConsultorioEntity> findByIdConsultorioAndDeletedAtIsNull(int idConsultorio);
    List<ConsultorioEntity> findAllByEspecialidad(EspecialidadEntity especialidadEntity);
}