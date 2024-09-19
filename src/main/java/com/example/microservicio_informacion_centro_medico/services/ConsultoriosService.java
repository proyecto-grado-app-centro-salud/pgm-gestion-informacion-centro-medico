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
    public List<ConsultorioDto> obtenerConsultorios() {
        List<ConsultorioEntity>consultoriosEntities=consultoriosRepositoryJPA.findAll();
        List<ConsultorioDto>consultoriosDtos=new ArrayList<ConsultorioDto>();
        for(ConsultorioEntity consultorioEntity:consultoriosEntities){
            ConsultorioDto consultorioDto=new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(consultorioEntity);
            consultoriosDtos.add(consultorioDto);
        }
        return consultoriosDtos;
    }

    public ConsultorioDto obtenerConsultorioPorId(int id) {
        Optional<ConsultorioEntity> consultorioEntity = consultoriosRepositoryJPA.findById(id);
        if (consultorioEntity.isPresent()) {
            return new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(consultorioEntity.get());
        } else {
            throw new RuntimeException("Consultorio no encontrado");
        }
    }

    public ConsultorioDto crearConsultorio(ConsultorioDto consultorioDto,Map<String,MultipartFile> allFiles) {
        Optional<EspecialidadesEntity> especialidadEntityOptional=especialidadesRepositoryJPA.findById(consultorioDto.getIdEspecialidad());
        if (especialidadEntityOptional.isPresent()) {
            EspecialidadesEntity especialidadesEntity = especialidadEntityOptional.get();
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
            // imagenesService.guardarImagenes(imagenes, "especialidades", savedEntity.getIdEspecialidad());
            return new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(savedEntity);    
        }else{
            throw new RuntimeException("Especialidad no encontrada");
        }
        
    }

    public ConsultorioDto actualizarConsultorio(int id, ConsultorioDto consultorioDto) {
        Optional<EspecialidadesEntity> especialidadEntityOptional = especialidadesRepositoryJPA.findById(consultorioDto.getIdEspecialidad());
        Optional<ConsultorioEntity> consultorioEntityOptional = consultoriosRepositoryJPA.findById(id);
        if (especialidadEntityOptional.isPresent() && consultorioEntityOptional.isPresent()) {
            EspecialidadesEntity especialidadesEntity = especialidadEntityOptional.get();
            ConsultorioEntity consultorioEntity = consultorioEntityOptional.get();
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
            return new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(updatedEntity);
        } else {
            throw new RuntimeException("Especialidad no encontrado"); 
        }
    }

    public void eliminarConsultorio(int id) {
        consultoriosRepositoryJPA.deleteById(id);
    }

    
}
