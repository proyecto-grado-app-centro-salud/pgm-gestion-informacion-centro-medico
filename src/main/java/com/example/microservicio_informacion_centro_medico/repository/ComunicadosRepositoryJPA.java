package com.example.microservicio_informacion_centro_medico.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.microservicio_informacion_centro_medico.model.ComunicadoEntity;
import com.example.microservicio_informacion_centro_medico.model.RolUsuarioEntity;


public interface ComunicadosRepositoryJPA extends JpaRepository<ComunicadoEntity, Integer> ,JpaSpecificationExecutor<ComunicadoEntity>{
    List<ComunicadoEntity> findAllByDeletedAtIsNullOrderByUpdatedAtDesc();
    Optional<ComunicadoEntity> findByIdComunicadoAndDeletedAtIsNull(int idConsultorio);
}