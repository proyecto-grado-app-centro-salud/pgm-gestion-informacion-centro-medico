package com.example.microservicio_informacion_centro_medico.model.dtos;

import java.util.List;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasoDto {
    private Integer idPaso;
    private String nombre;
    private String descripcion;
    private List<ImagenDto> imagenes;
    public PasoDto convertirPasoEntityAPasoDto(PasoEntity pasoEntity) {
        PasoDto pasoDto = new PasoDto();
        pasoDto.setIdPaso(pasoEntity.getIdPaso());
        pasoDto.setNombre(pasoEntity.getNombre());
        pasoDto.setDescripcion(pasoEntity.getDescripcion());
        return pasoDto;
    }
}
