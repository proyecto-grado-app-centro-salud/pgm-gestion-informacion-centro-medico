package com.example.microservicio_informacion_centro_medico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadesEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.ConsultorioDto;
import com.example.microservicio_informacion_centro_medico.repository.ConsultoriosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;

@Service
public class ConsultoriosService {
    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    @Autowired
    ConsultoriosRepositoryJPA consultoriosRepositoryJPA;
    @Autowired
    ImagenesService imagenesService;
    public List<ConsultorioDto> obtenerConsultorios() {
        List<ConsultorioEntity>consultoriosEntities=consultoriosRepositoryJPA.findAllByDeletedAtIsNull();
        List<ConsultorioDto>consultoriosDtos=new ArrayList<ConsultorioDto>();
        for(ConsultorioEntity consultorioEntity:consultoriosEntities){
            ConsultorioDto consultorioDto=new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(consultorioEntity);
            consultorioDto.setImagenes(imagenesService.obtenerImagenes("consultorios", consultorioDto.getIdConsultorio()));
            consultoriosDtos.add(consultorioDto);
        }
        return consultoriosDtos;
    }

    public ConsultorioDto obtenerConsultorioPorId(int id) {
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Consultorio no encontrada"));
        ConsultorioDto consultorioDto = new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(consultorioEntity);
        consultorioDto.setImagenes(imagenesService.obtenerImagenes("consultorios", consultorioEntity.getIdConsultorio()));
        return consultorioDto;
    }

    public ConsultorioDto crearConsultorio(ConsultorioDto consultorioDto,Map<String,MultipartFile> allFiles) {
        EspecialidadesEntity especialidadesEntity=especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(consultorioDto.getIdEspecialidad())
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        ConsultorioEntity consultorioEntity = new ConsultorioEntity();
        consultorioEntity.setNombre(consultorioDto.getNombre());
        consultorioEntity.setDireccion(consultorioDto.getDireccion());
        consultorioEntity.setEquipamiento(consultorioDto.getEquipamiento());
        consultorioEntity.setEspecialidad(especialidadesEntity);
        consultorioEntity.setCodigoConsultorio(consultorioDto.getCodigoConsultorio());
        consultorioEntity.setDescripcion(consultorioDto.getDescripcion());
        consultorioEntity.setNumeroTelefono(consultorioDto.getNumeroTelefono());
        consultorioEntity.setCapacidad(consultorioDto.getCapacidad());
        consultorioEntity.setCreatedAt(consultorioDto.getCreatedAt());
        consultorioEntity.setUpdatedAt(consultorioDto.getUpdatedAt());
        consultorioEntity.setDeletedAt(consultorioDto.getDeletedAt());
        ConsultorioEntity savedEntity = consultoriosRepositoryJPA.save(consultorioEntity);
        List<MultipartFile> imagenes=imagenesService.obtenerImagenesDeArchivos(allFiles);
        imagenesService.guardarImagenes(imagenes, "consultorios", savedEntity.getIdConsultorio());
        return new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(savedEntity);    
    }

    public ConsultorioDto actualizarConsultorio(int id, ConsultorioDto consultorioDto,Map<String, MultipartFile> allFiles,Map<String, String> params) {
        EspecialidadesEntity especialidadesEntity=especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(consultorioDto.getIdEspecialidad())
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Consultorio no encontrada"));
        consultorioEntity.setNombre(consultorioDto.getNombre());
        consultorioEntity.setDireccion(consultorioDto.getDireccion());
        consultorioEntity.setEquipamiento(consultorioDto.getEquipamiento());
        consultorioEntity.setEspecialidad(especialidadesEntity);
        consultorioEntity.setCodigoConsultorio(consultorioDto.getCodigoConsultorio());
        consultorioEntity.setDescripcion(consultorioDto.getDescripcion());
        consultorioEntity.setNumeroTelefono(consultorioDto.getNumeroTelefono());
        consultorioEntity.setCapacidad(consultorioDto.getCapacidad());
        consultorioEntity.setCreatedAt(consultorioDto.getCreatedAt());
        consultorioEntity.setUpdatedAt(consultorioDto.getUpdatedAt());
        consultorioEntity.setDeletedAt(consultorioDto.getDeletedAt());
        ConsultorioEntity updatedEntity = consultoriosRepositoryJPA.save(consultorioEntity);
        imagenesService.actualizarImagenes(allFiles, params, "consultorios", consultorioEntity.getIdConsultorio());
        return new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(updatedEntity);
    }

    public void eliminarConsultorio(int id) {
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Consultorio no encontrado"));
        consultorioEntity.markAsDeleted();
        consultoriosRepositoryJPA.save(consultorioEntity);

    }

    
}
