package com.example.microservicio_informacion_centro_medico.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimientoDto {
    private Integer idProcedimiento;
    private String nombreProcedimiento;
    private String descripcionProcedimiento;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private List<PasoDto> pasos;
    private List<RequisitoDto> requisitos;
    private List<ImagenDto> imagenes;

    public ProcedimientoDto convertirProcedimientoEntityAProcedimientoDto(ProcedimientoEntity procedimientoEntity) {
        ProcedimientoDto procedimientoDto = new ProcedimientoDto();
        procedimientoDto.setIdProcedimiento(procedimientoEntity.getIdProcedimiento());
        procedimientoDto.setNombreProcedimiento(procedimientoEntity.getNombreProcedimiento());
        procedimientoDto.setDescripcionProcedimiento(procedimientoEntity.getDescripcionProcedimiento());
        procedimientoDto.setCreatedAt(procedimientoEntity.getCreatedAt());
        procedimientoDto.setUpdatedAt(procedimientoEntity.getUpdatedAt());
        procedimientoDto.setDeletedAt(procedimientoEntity.getDeletedAt());
        return procedimientoDto;
    }
}