package com.example.microservicio_informacion_centro_medico.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.microservicio_informacion_centro_medico.model.RolEntity;
import com.example.microservicio_informacion_centro_medico.model.RolUsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;


public interface RolesUsuariosRepositoryJPA extends JpaRepository<RolUsuarioEntity, Integer>,JpaSpecificationExecutor<RolUsuarioEntity>{
    List<RolUsuarioEntity> findByUsuario(UsuarioEntity usuarioEntity);

    List<RolUsuarioEntity> findByRol(RolEntity rolEntity);

    Optional<RolUsuarioEntity> findOneByUsuarioAndRol(UsuarioEntity usuarioEntity, RolEntity rolEntity);
}
