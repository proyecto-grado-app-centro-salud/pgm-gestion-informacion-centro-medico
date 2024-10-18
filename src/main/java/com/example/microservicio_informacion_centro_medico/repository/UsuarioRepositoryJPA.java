package com.example.microservicio_informacion_centro_medico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;


public interface UsuarioRepositoryJPA extends JpaRepository<UsuarioEntity, Integer>{

    Optional<UsuarioEntity> findByIdUsuarioAndDeletedAtIsNull(int idUsuario);
}
