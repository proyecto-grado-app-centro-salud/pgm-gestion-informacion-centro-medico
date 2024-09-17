package com.example.microservicio_informacion_centro_medico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.EspecialidadesEntity;
import com.example.microservicio_informacion_centro_medico.model.ImagenEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.EspecialidadDto;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ImagenesRepositoryJPA;

@Service
public class EspecialidadesService {
    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    @Autowired
    ImagenesService imagenesService;
    public List<EspecialidadDto> obtenerEspecialidades() {
        List<EspecialidadesEntity> especialidadesEntities = especialidadesRepositoryJPA.findAll();
        List<EspecialidadDto> especialidadesDtos = new ArrayList<>();
        for (EspecialidadesEntity especialidadEntity : especialidadesEntities) {
            EspecialidadDto especialidadDto=new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(especialidadEntity);
            especialidadDto.setImagenes(imagenesService.obtenerImagenes("especialidades", especialidadEntity.getIdEspecialidad()));
            especialidadesDtos.add(especialidadDto);
        }
        return especialidadesDtos;
    }

    public EspecialidadDto obtenerEspecialidadPorId(int id) {
        Optional<EspecialidadesEntity> especialidadEntity = especialidadesRepositoryJPA.findById(id);
        if (especialidadEntity.isPresent()) {
            EspecialidadDto especialidadDto = new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(especialidadEntity.get());
            especialidadDto.setImagenes(imagenesService.obtenerImagenes("especialidades", especialidadEntity.get().getIdEspecialidad()));
            return especialidadDto;
        } else {
            throw new RuntimeException("Especialidad no encontrada");
        }
    }

    public EspecialidadDto crearEspecialidad(EspecialidadDto especialidadDto, List<MultipartFile> imagenes) {
        EspecialidadesEntity especialidadEntity = new EspecialidadesEntity();
        especialidadEntity.setIdEspecialidad(especialidadDto.getIdEspecialidad());
        especialidadEntity.setNombre(especialidadDto.getNombre());
        especialidadEntity.setDescripcion(especialidadDto.getDescripcion());
        especialidadEntity.setFechaCreacion(especialidadDto.getFechaCreacion());
        especialidadEntity.setCreatedAt(especialidadDto.getCreatedAt());
        especialidadEntity.setUpdatedAt(especialidadDto.getUpdatedAt());
        especialidadEntity.setDeletedAt(especialidadDto.getDeletedAt());
        EspecialidadesEntity savedEntity = especialidadesRepositoryJPA.save(especialidadEntity);
        imagenesService.guardarImagenes(imagenes, "especialidades", savedEntity.getIdEspecialidad());
        return new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(savedEntity);
    }

    public EspecialidadDto actualizarEspecialidad(int id, EspecialidadDto especialidadDto, Map<String, Object> allFiles) {
        Optional<EspecialidadesEntity> especialidadEntityOptional = especialidadesRepositoryJPA.findById(id);
        if (especialidadEntityOptional.isPresent()) {
            EspecialidadesEntity especialidadEntity = especialidadEntityOptional.get();
            especialidadEntity.setNombre(especialidadDto.getNombre());
            especialidadEntity.setDescripcion(especialidadDto.getDescripcion());
            especialidadEntity.setFechaCreacion(especialidadDto.getFechaCreacion());
            especialidadEntity.setCreatedAt(especialidadDto.getCreatedAt());
            especialidadEntity.setUpdatedAt(especialidadDto.getUpdatedAt());
            especialidadEntity.setDeletedAt(especialidadDto.getDeletedAt());
            EspecialidadesEntity updatedEntity = especialidadesRepositoryJPA.save(especialidadEntity);
            imagenesService.actualizarImagenes(allFiles, "especialidades", updatedEntity.getIdEspecialidad());
            return new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(updatedEntity);
        } else {
            throw new RuntimeException("Especialidad no encontrada");
        }
    }

    public void deleteEspecialidad(int id) {
        especialidadesRepositoryJPA.deleteById(id);
    }

}
