package com.example.microservicio_informacion_centro_medico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
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
    TurnosAtencionMedicaService turnosAtencionMedicaService;
    @Autowired
    ImagenesService imagenesService;
    @Value(value = "${centro.salud.id}")
    private Integer centroSaludId;
    public List<ConsultorioDto> obtenerConsultorios() {
        List<ConsultorioEntity>consultoriosEntities=consultoriosRepositoryJPA.findAllByDeletedAtIsNull();
        List<ConsultorioDto>consultoriosDtos=new ArrayList<ConsultorioDto>();
        for(ConsultorioEntity consultorioEntity:consultoriosEntities){
            ConsultorioDto consultorioDto=new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(consultorioEntity);
            consultorioDto.setImagenes(imagenesService.obtenerImagenes("consultorios", Integer.toString(consultorioDto.getIdConsultorio())));
            consultoriosDtos.add(consultorioDto);
        }
        return consultoriosDtos;
    }

    public ConsultorioDto obtenerConsultorioPorId(int id) {
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Consultorio no encontrada"));
        ConsultorioDto consultorioDto = new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(consultorioEntity);
        consultorioDto.setImagenes(imagenesService.obtenerImagenes("consultorios", Integer.toString(consultorioDto.getIdConsultorio())));
        return consultorioDto;
    }

    public ConsultorioDto crearConsultorio(ConsultorioDto consultorioDto,Map<String,MultipartFile> allFiles) {
        EspecialidadEntity especialidadesEntity=especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(consultorioDto.getIdEspecialidad())
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        ConsultorioEntity consultorioEntity = new ConsultorioEntity();
        consultorioEntity.setNombre(consultorioDto.getNombre());
        consultorioEntity.setDireccion(consultorioDto.getDireccion());
        consultorioEntity.setEquipamiento(consultorioDto.getEquipamiento());
        consultorioEntity.setEspecialidad(especialidadesEntity);
        consultorioEntity.setCodigoConsultorio(consultorioDto.getCodigoConsultorio());
        consultorioEntity.setDescripcion(consultorioDto.getDescripcion());
        consultorioEntity.setNumeroTelefono(consultorioDto.getNumeroTelefono());
        consultorioEntity.setIdCentroSalud(centroSaludId);
        consultorioEntity.setCapacidad(consultorioDto.getCapacidad());
        consultorioEntity.setCreatedAt(consultorioDto.getCreatedAt());
        consultorioEntity.setUpdatedAt(consultorioDto.getUpdatedAt());
        consultorioEntity.setDeletedAt(consultorioDto.getDeletedAt());
        ConsultorioEntity savedEntity = consultoriosRepositoryJPA.save(consultorioEntity);
        List<MultipartFile> imagenes=imagenesService.obtenerImagenesDeArchivos(allFiles);
        imagenesService.guardarImagenes(imagenes, "consultorios", savedEntity.getIdConsultorio()+"");
        return new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(savedEntity);    
    }

    public ConsultorioDto actualizarConsultorio(int id, ConsultorioDto consultorioDto,Map<String, MultipartFile> allFiles,Map<String, String> params) {
        EspecialidadEntity especialidadesEntity=especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(consultorioDto.getIdEspecialidad())
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
        imagenesService.actualizarImagenes(allFiles, params, "consultorios", consultorioEntity.getIdConsultorio()+"");
        return new ConsultorioDto().convertirConsultorioEntityAConsultorioDto(updatedEntity);
    }

    public void eliminarConsultorio(int id) throws Exception {
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Consultorio no encontrado"));
        consultorioEntity.markAsDeleted();
        List<TurnosAtencionMedicaEntity> turnos=turnosAtencionMedicaService.obtenerTurnosAtencionMedicaConsultorio(id);
        for(TurnosAtencionMedicaEntity turno:turnos){
            turnosAtencionMedicaService.eliminarHorarioAtencion(turno.getIdTurnoAtencionMedica());
        }
        consultoriosRepositoryJPA.save(consultorioEntity);
    }

    public List<ConsultorioEntity> obtenerConsultoriosDeEspecialidad(int idEspecialidad) throws Exception {
        EspecialidadEntity especialidadEntity = especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(idEspecialidad).orElseThrow(()->new Exception("Especialidad no encontrada"));
        return consultoriosRepositoryJPA.findAllByEspecialidad(especialidadEntity);
    }
}
