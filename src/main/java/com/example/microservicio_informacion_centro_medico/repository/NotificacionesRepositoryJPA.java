package com.example.microservicio_informacion_centro_medico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio_informacion_centro_medico.model.NotificacionEntity;

@Repository
public interface NotificacionesRepositoryJPA extends JpaRepository<NotificacionEntity, Integer> {    
}
