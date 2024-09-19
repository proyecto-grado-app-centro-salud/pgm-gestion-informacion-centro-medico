package com.example.microservicio_informacion_centro_medico.model.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultorioDto {
    private int idConsultorio;
    private String nombre;
    private String direccion;
    private String equipamiento;
    private int idEspecialidad;
    private String nombreEspecialidad;
    private String codigoConsultorio;
    private String descripcion;
    private String numeroTelefono;
    private int capacidad;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    public ConsultorioDto ObjectoAConsultorio(Object object){
        ConsultorioDto consultoriosDto=new ConsultorioDto();
        return consultoriosDto;
    }
    public ConsultorioDto convertirConsultorioEntityAConsultorioDto(ConsultorioEntity consultorioEntity){
        ConsultorioDto consultorioDto=new ConsultorioDto();
        consultorioDto.idConsultorio=consultorioEntity.getIdConsultorio();
        consultorioDto.nombre=consultorioEntity.getNombre();
        consultorioDto.direccion=consultorioEntity.getDireccion();
        consultorioDto.equipamiento=consultorioEntity.getEquipamiento();
        consultorioDto.idEspecialidad=consultorioEntity.getEspecialidad().getIdEspecialidad();
        consultorioDto.nombreEspecialidad=consultorioEntity.getEspecialidad().getNombre();
        consultorioDto.codigoConsultorio=consultorioEntity.getCodigoConsultorio();
        consultorioDto.descripcion=consultorioEntity.getDescripcion();
        consultorioDto.numeroTelefono=consultorioEntity.getNumeroTelefono();
        consultorioDto.capacidad=consultorioEntity.getCapacidad()!=null?consultorioEntity.getCapacidad().intValue():0;
        consultorioDto.createdAt=consultorioEntity.getCreatedAt();
        consultorioDto.updatedAt=consultorioEntity.getUpdatedAt();
        consultorioDto.deletedAt=consultorioEntity.getDeletedAt();
        return consultorioDto;
    }
}
