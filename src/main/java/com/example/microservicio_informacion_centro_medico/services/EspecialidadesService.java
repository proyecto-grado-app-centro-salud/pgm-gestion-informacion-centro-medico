package com.example.microservicio_informacion_centro_medico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.EspecialidadesEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.EspecialidadDto;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;

@Service
public class EspecialidadesService {
    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    @Autowired
    ImagenesService imagenesService;
    public List<EspecialidadDto> obtenerEspecialidades() {
        List<EspecialidadesEntity> especialidadesEntities = especialidadesRepositoryJPA.findAllByDeletedAtIsNull();
        List<EspecialidadDto> especialidadesDtos = new ArrayList<>();
        for (EspecialidadesEntity especialidadEntity : especialidadesEntities) {
            EspecialidadDto especialidadDto=new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(especialidadEntity);
            especialidadDto.setImagenes(imagenesService.obtenerImagenes("especialidades", especialidadEntity.getIdEspecialidad()));
            especialidadesDtos.add(especialidadDto);
        }
        return especialidadesDtos;
    }

    public EspecialidadDto obtenerEspecialidadPorId(int id) {
        EspecialidadesEntity especialidadEntity = especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        EspecialidadDto especialidadDto = new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(especialidadEntity);
        especialidadDto.setImagenes(imagenesService.obtenerImagenes("especialidades", especialidadEntity.getIdEspecialidad()));
        return especialidadDto;
        
    }

    public EspecialidadDto crearEspecialidad(EspecialidadDto especialidadDto, Map<String, MultipartFile> allFiles) {
        EspecialidadesEntity especialidadEntity = new EspecialidadesEntity();
        especialidadEntity.setNombre(especialidadDto.getNombre());
        especialidadEntity.setDescripcion(especialidadDto.getDescripcion());
        especialidadEntity.setFechaCreacion(especialidadDto.getFechaCreacion());
        EspecialidadesEntity savedEntity = especialidadesRepositoryJPA.save(especialidadEntity);
        List<MultipartFile> imagenes=imagenesService.obtenerImagenesDeArchivos(allFiles);
        imagenesService.guardarImagenes(imagenes, "especialidades", savedEntity.getIdEspecialidad());
        return new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(savedEntity);
    }

    public EspecialidadDto actualizarEspecialidad(int id, EspecialidadDto especialidadDto, Map<String, MultipartFile> allFiles,Map<String, String> params) {
        EspecialidadesEntity especialidadEntity = especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        especialidadEntity.setNombre(especialidadDto.getNombre());
        especialidadEntity.setDescripcion(especialidadDto.getDescripcion());
        especialidadEntity.setFechaCreacion(especialidadDto.getFechaCreacion());
        EspecialidadesEntity updatedEntity = especialidadesRepositoryJPA.save(especialidadEntity);
        imagenesService.actualizarImagenes(allFiles,params, "especialidades", updatedEntity.getIdEspecialidad());
        return new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(updatedEntity);
    }

    public void deleteEspecialidad(int id) {
        EspecialidadesEntity especialidadEntity = especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        especialidadEntity.markAsDeleted();
        especialidadesRepositoryJPA.save(especialidadEntity);
    }

}
