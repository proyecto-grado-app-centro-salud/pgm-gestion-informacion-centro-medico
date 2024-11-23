package com.example.microservicio_informacion_centro_medico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;


public interface UsuarioRepositoryJPA extends JpaRepository<UsuarioEntity, String>,JpaSpecificationExecutor<UsuarioEntity> {

    Optional<UsuarioEntity> findByIdUsuarioAndDeletedAtIsNull(String idUsuario);
}
