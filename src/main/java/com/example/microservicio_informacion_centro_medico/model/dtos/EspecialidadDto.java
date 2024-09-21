package com.example.microservicio_informacion_centro_medico.model.dtos;

import java.util.Date;
import java.util.List;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadesEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadDto {
    private Integer idEspecialidad;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private List<ImagenDto> imagenes;
    public EspecialidadDto convertirEspecialidadEntityAEspecialidadDto(EspecialidadesEntity especialidadEntity){
        EspecialidadDto especialidadDto=new EspecialidadDto();
        especialidadDto.idEspecialidad=especialidadEntity.getIdEspecialidad();
        especialidadDto.nombre=especialidadEntity.getNombre();
        especialidadDto.descripcion=especialidadEntity.getDescripcion();
        especialidadDto.fechaCreacion=especialidadEntity.getFechaCreacion();
        especialidadDto.createdAt=especialidadEntity.getCreatedAt();
        especialidadDto.updatedAt=especialidadEntity.getUpdatedAt();
        especialidadDto.deletedAt=especialidadEntity.getDeletedAt();
        return especialidadDto;
    }  
}
