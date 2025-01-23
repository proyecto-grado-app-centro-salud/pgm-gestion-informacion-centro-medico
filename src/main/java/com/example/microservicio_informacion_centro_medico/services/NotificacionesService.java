package com.example.microservicio_informacion_centro_medico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_informacion_centro_medico.model.NotificacionEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.NotificacionDto;
import com.example.microservicio_informacion_centro_medico.repository.NotificacionesRepositoryJPA;

import jakarta.transaction.Transactional;

@Service
public class NotificacionesService {
    @Autowired
    private NotificacionesRepositoryJPA notificacionRepository;

    @Transactional
    public NotificacionDto crearNotificacion(NotificacionDto notificacionDto) {
        NotificacionEntity notificacionEntity = new NotificacionEntity();
        notificacionEntity.setTitulo(notificacionDto.getTitulo());
        notificacionEntity.setDescripcion(notificacionDto.getDescripcion());
        notificacionEntity = notificacionRepository.save(notificacionEntity);
        return new NotificacionDto().convertirNotificacionEntityANotificacionDto(notificacionEntity);
    }

    
}
