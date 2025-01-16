package com.example.microservicio_informacion_centro_medico.repository;


import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;



public interface UsuariosRepositoryJPA extends JpaRepository<UsuarioEntity, String>{
    List<UsuarioEntity> findAllByDeletedAtIsNull();
    Optional<UsuarioEntity> findByCiAndDeletedAtIsNull(String ci);
    Optional<UsuarioEntity> findByIdUsuarioAndDeletedAtIsNull(String idUsuario);
    Optional<UsuarioEntity> findByCi(String ci);

}
