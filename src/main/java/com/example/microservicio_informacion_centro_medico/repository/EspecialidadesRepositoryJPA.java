package com.example.microservicio_informacion_centro_medico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;

public interface EspecialidadesRepositoryJPA extends JpaRepository<EspecialidadEntity, Integer> ,JpaSpecificationExecutor<EspecialidadEntity>{
    List<EspecialidadEntity> findAllByDeletedAtIsNull();
    Optional<EspecialidadEntity> findByIdEspecialidadAndDeletedAtIsNull(int idEspecialidad);
}