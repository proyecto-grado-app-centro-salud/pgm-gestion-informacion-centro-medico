package com.example.microservicio_informacion_centro_medico.repository;

import com.example.microservicio_informacion_centro_medico.model.ImagenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenesRepositoryJPA extends JpaRepository<ImagenEntity, Integer> {
    List<ImagenEntity> findByImageableTypeAndImageableIdAndDeletedAtIsNull(String imageableType, Integer imageableId);
}