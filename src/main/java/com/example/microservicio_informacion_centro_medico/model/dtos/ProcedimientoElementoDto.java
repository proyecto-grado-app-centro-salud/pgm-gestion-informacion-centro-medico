package com.example.microservicio_informacion_centro_medico.model.dtos;

import java.util.List;

import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimientoElementoDto {
    Integer idProcedimientoElemento;
    String nombreProcedimiento;
    Integer idProcedimiento;
    String tipoElemento;
    Integer idElemento;
    String nombreElemento;
    String descripcion;
    List<ImagenDto> imagenes;
    ProcedimientoElementoDto(Integer idProcedimientoElemento, String nombreProcedimiento, Integer idProcedimiento, String tipoElemento, Integer idElemento, String nombreElemento, String descripcion) {
        this.idProcedimientoElemento = idProcedimientoElemento;
        this.nombreProcedimiento = nombreProcedimiento;
        this.idProcedimiento = idProcedimiento;
        this.tipoElemento = tipoElemento;
        this.idElemento = idElemento;
        this.nombreElemento = nombreElemento;
        this.descripcion = descripcion;
    }
    public ProcedimientoElementoDto convertirProcedimientoElementoEntityAProcedimientoElementoDto(ProcedimientoElementoEntity procedimientoElementoEntity) {
        ProcedimientoElementoDto procedimientoElementoDto = new ProcedimientoElementoDto();
        procedimientoElementoDto.setIdProcedimientoElemento(procedimientoElementoEntity.getIdProcedimientoElemento());
        procedimientoElementoDto.setIdElemento(procedimientoElementoEntity.getIdElemento());
        procedimientoElementoDto.setTipoElemento(procedimientoElementoEntity.getTipoElemento());
        procedimientoElementoDto.setDescripcion(procedimientoElementoEntity.getDescripcion());
        procedimientoElementoDto.setIdProcedimiento(procedimientoElementoEntity.getProcedimiento().getIdProcedimiento());
        procedimientoElementoDto.setNombreProcedimiento(procedimientoElementoEntity.getProcedimiento().getNombreProcedimiento());   
        procedimientoElementoDto.setImagenes(imagenes);
        return procedimientoElementoDto;
    }
}
