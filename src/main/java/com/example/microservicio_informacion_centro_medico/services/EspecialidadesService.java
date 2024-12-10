package com.example.microservicio_informacion_centro_medico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.EspecialidadDto;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.util.specifications.EspecialidadesSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class EspecialidadesService {
    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    @Autowired
    ImagenesService imagenesService;
    public List<EspecialidadDto> obtenerEspecialidades(String nombreEspecialidad,Integer page, Integer size) throws JsonProcessingException {
        List<EspecialidadEntity>especialidadesEntities=new ArrayList<>();
        Specification<EspecialidadEntity> spec = Specification.where(EspecialidadesSpecification.obtenerEspecialidadesPorParametros(nombreEspecialidad));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<EspecialidadEntity> especialidadesEntitiesPage=especialidadesRepositoryJPA.findAll(spec,pageable);
            especialidadesEntities=especialidadesEntitiesPage.getContent();
        }else{
            especialidadesEntities=especialidadesRepositoryJPA.findAll(spec);
        }   
        
        List<EspecialidadDto> especialidadesDtos = new ArrayList<>();
        for (EspecialidadEntity especialidadEntity : especialidadesEntities) {
            EspecialidadDto especialidadDto=new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(especialidadEntity);
            especialidadDto.setImagenes(imagenesService.obtenerImagenes("especialidades", especialidadEntity.getIdEspecialidad()+""));
            especialidadesDtos.add(especialidadDto);
        }
        return especialidadesDtos;
    }

    public EspecialidadDto obtenerEspecialidadPorId(int id) throws JsonProcessingException {
        EspecialidadEntity especialidadEntity = especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        EspecialidadDto especialidadDto = new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(especialidadEntity);
        especialidadDto.setImagenes(imagenesService.obtenerImagenes("especialidades", especialidadEntity.getIdEspecialidad()+""));
        return especialidadDto;
        
    }

    public EspecialidadDto crearEspecialidad(EspecialidadDto especialidadDto, Map<String, MultipartFile> allFiles) throws JsonProcessingException {
        EspecialidadEntity especialidadEntity = new EspecialidadEntity();
        especialidadEntity.setNombre(especialidadDto.getNombre());
        especialidadEntity.setDescripcion(especialidadDto.getDescripcion());
        especialidadEntity.setFechaCreacion(especialidadDto.getFechaCreacion());
        logger.info(especialidadDto.getPermisos().toString());
        especialidadEntity.setPermisosJson(especialidadDto.getPermisos());
        EspecialidadEntity savedEntity = especialidadesRepositoryJPA.save(especialidadEntity);
        List<MultipartFile> imagenes=imagenesService.obtenerImagenesDeArchivos(allFiles);
        imagenesService.guardarImagenes(imagenes, "especialidades", savedEntity.getIdEspecialidad()+"");
        return new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(savedEntity);
    }
    Logger logger = LoggerFactory.getLogger(EspecialidadesService.class);

    public EspecialidadDto actualizarEspecialidad(int id, EspecialidadDto especialidadDto, Map<String, MultipartFile> allFiles,Map<String, String> params) throws JsonProcessingException {
        EspecialidadEntity especialidadEntity = especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        especialidadEntity.setNombre(especialidadDto.getNombre());
        especialidadEntity.setDescripcion(especialidadDto.getDescripcion());
        especialidadEntity.setFechaCreacion(especialidadDto.getFechaCreacion());
        logger.info(especialidadDto.getPermisos().toString());
        especialidadEntity.setPermisosJson(especialidadDto.getPermisos());
        EspecialidadEntity updatedEntity = especialidadesRepositoryJPA.save(especialidadEntity);
        imagenesService.actualizarImagenes(allFiles,params, "especialidades", updatedEntity.getIdEspecialidad()+"");
        return new EspecialidadDto().convertirEspecialidadEntityAEspecialidadDto(updatedEntity);
    }

    public void deleteEspecialidad(int id) {
        EspecialidadEntity especialidadEntity = especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        especialidadEntity.markAsDeleted();
        especialidadesRepositoryJPA.save(especialidadEntity);
    }

}
