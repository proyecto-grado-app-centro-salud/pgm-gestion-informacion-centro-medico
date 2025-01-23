package com.example.microservicio_informacion_centro_medico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_informacion_centro_medico.model.NotificacionUsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.NotificacionUsuarioDto;
import com.example.microservicio_informacion_centro_medico.repository.NotificacionUsuarioRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.NotificacionesRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.UsuarioRepositoryJPA;

import jakarta.transaction.Transactional;

@Service
public class NotificacionesUsuariosService {

    @Autowired
    private NotificacionUsuarioRepositoryJPA notificacionUsuarioRepository;

    @Autowired
    private NotificacionesRepositoryJPA notificacionRepository;

    @Autowired
    private UsuarioRepositoryJPA usuarioRepository;

    @Transactional
    public NotificacionUsuarioDto crearNotificacionUsuario(NotificacionUsuarioDto notificacionUsuarioDto) throws Exception {
        NotificacionUsuarioEntity notificacionUsuarioEntity = new NotificacionUsuarioEntity();
        notificacionUsuarioEntity.setNotificacion(notificacionRepository.findById(notificacionUsuarioDto.getIdNotificacion()).orElseThrow(()->new Exception("Usuario no encontrado")));
        notificacionUsuarioEntity.setUsuario(usuarioRepository.findById(notificacionUsuarioDto.getIdUsuario()).orElseThrow(()->new Exception("Usuario no encontrado")));
        notificacionUsuarioEntity.setLeido(notificacionUsuarioDto.getLeido());
        notificacionUsuarioEntity = notificacionUsuarioRepository.save(notificacionUsuarioEntity);

        return new NotificacionUsuarioDto().convertirNotificacionUsuarioEntityANotificacionUsuarioDto(notificacionUsuarioEntity);
    }
}
