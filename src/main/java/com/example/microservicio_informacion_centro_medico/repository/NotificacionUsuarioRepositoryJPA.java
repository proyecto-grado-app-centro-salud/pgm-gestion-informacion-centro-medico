package com.example.microservicio_informacion_centro_medico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio_informacion_centro_medico.model.NotificacionUsuarioEntity;

@Repository
public interface NotificacionUsuarioRepositoryJPA extends JpaRepository<NotificacionUsuarioEntity, Integer> {
}