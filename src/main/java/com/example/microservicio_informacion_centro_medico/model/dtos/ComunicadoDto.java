package com.example.microservicio_informacion_centro_medico.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;


import com.example.microservicio_informacion_centro_medico.model.ComunicadoEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComunicadoDto {
    private Integer idComunicado;
    private String titulo;
    private String lugar;
    private String introduccion;
    private String cuerpo;
    private String citas;
    private String datosContacto;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private List<ImagenDto> imagenes;
    public ComunicadoDto convertirComunicadoEntityAComunicadoDto(ComunicadoEntity comunicadoEntity) {
        ComunicadoDto comunicadoDto = new ComunicadoDto();
        comunicadoDto.setIdComunicado(comunicadoEntity.getIdComunicado());
        comunicadoDto.setTitulo(comunicadoEntity.getTitulo());
        comunicadoDto.setLugar(comunicadoEntity.getLugar());
        comunicadoDto.setIntroduccion(comunicadoEntity.getIntroduccion());
        comunicadoDto.setCuerpo(comunicadoEntity.getCuerpo());
        comunicadoDto.setCitas(comunicadoEntity.getCitas());
        comunicadoDto.setDatosContacto(comunicadoEntity.getDatosContacto());
        comunicadoDto.setCreatedAt(comunicadoEntity.getCreatedAt());
        comunicadoDto.setUpdatedAt(comunicadoEntity.getUpdatedAt());
        comunicadoDto.setDeletedAt(comunicadoEntity.getDeletedAt());
        return comunicadoDto;
    }
}