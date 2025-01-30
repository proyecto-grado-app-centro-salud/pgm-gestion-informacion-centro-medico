package com.example.microservicio_informacion_centro_medico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.RequisitoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.ImagenDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.RequisitoDto;
import com.example.microservicio_informacion_centro_medico.repository.PasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.RequisitosRepositoryJPA;

@Service
public class RequisitosService {
    @Autowired
    private RequisitosRepositoryJPA requisitosRepositoryJPA;

    @Autowired
    private ImagenesService imagenesService;
    public List<RequisitoDto>  obtenerRequisitos() {
        List<RequisitoEntity> requisitosEntities = requisitosRepositoryJPA.findAllByDeletedAtIsNull();
        List<RequisitoDto> requisitosDtos = new ArrayList<>();
        for (RequisitoEntity requisitoEntity : requisitosEntities) {
            RequisitoDto requisitoDto = new RequisitoDto().convertirRequisitoEntityARequisitoDto(requisitoEntity);
            List<ImagenDto> imagenes = imagenesService.obtenerImagenes("requisitos", requisitoEntity.getIdRequisito()+"");
            requisitoDto.setImagenes(imagenes);
            requisitosDtos.add(requisitoDto);
        }
        return requisitosDtos;
    }

    public RequisitoDto obtenerRequisitoPorId(int id) {
        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findByIdRequisitoAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));
        RequisitoDto requisitoDto = new RequisitoDto().convertirRequisitoEntityARequisitoDto(requisitoEntity);
        List<ImagenDto> imagenes = imagenesService.obtenerImagenes("requisitos", requisitoEntity.getIdRequisito()+"");
        requisitoDto.setImagenes(imagenes);
        return requisitoDto;    
    }

    public RequisitoDto crearRequisito(RequisitoDto requisitoDto, Map<String, MultipartFile> allFiles) {
        RequisitoEntity requisitoEntity = new RequisitoEntity();
        requisitoEntity.setTitulo(requisitoDto.getTitulo());
        requisitoEntity.setDescripcion(requisitoDto.getDescripcion());

        RequisitoEntity savedEntity = requisitosRepositoryJPA.save(requisitoEntity);

        List<MultipartFile> imagenes = imagenesService.obtenerImagenesDeArchivos(allFiles);
        imagenesService.guardarImagenes(imagenes, "requisitos", savedEntity.getIdRequisito()+"");

        return new RequisitoDto().convertirRequisitoEntityARequisitoDto(savedEntity);
    }

    public RequisitoDto actualizarRequisito(int id, RequisitoDto requisitoDto, Map<String, MultipartFile> allFiles,
        Map<String, String> params) {
        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findByIdRequisitoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));

        requisitoEntity.setTitulo(requisitoDto.getTitulo());
        requisitoEntity.setDescripcion(requisitoDto.getDescripcion());

        RequisitoEntity updatedEntity = requisitosRepositoryJPA.save(requisitoEntity);
        imagenesService.actualizarImagenes(allFiles, params, "requisitos", requisitoEntity.getIdRequisito()+"");

        return new RequisitoDto().convertirRequisitoEntityARequisitoDto(updatedEntity);
    }

    public void eliminarRequisito(int id) {
        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findByIdRequisitoAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));
        requisitosRepositoryJPA.delete(requisitoEntity);
    }
        
}
