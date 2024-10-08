package com.example.microservicio_informacion_centro_medico.model.dtos;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.RequisitoEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequisitoDto {
    private Integer idRequisito;
    private String titulo;
    private String descripcion;
    private List<ImagenDto> imagenes;

    public RequisitoDto convertirRequisitoEntityARequisitoDto(RequisitoEntity requisitoEntity) {
        RequisitoDto requisitoDto = new RequisitoDto();
        requisitoDto.setIdRequisito(requisitoEntity.getIdRequisito());
        requisitoDto.setTitulo(requisitoEntity.getTitulo());
        requisitoDto.setDescripcion(requisitoEntity.getDescripcion());
        return requisitoDto;
    }
}
