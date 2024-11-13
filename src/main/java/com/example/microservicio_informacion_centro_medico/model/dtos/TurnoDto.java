package com.example.microservicio_informacion_centro_medico.model.dtos;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDto {
    private int idTurno;
    private String nombre;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public TurnoDto convertirTurnoEntityTurnoDto(TurnoEntity turnoEntity) {
        TurnoDto turnoDto = new TurnoDto();
        turnoDto.setIdTurno(turnoEntity.getIdTurno());
        turnoDto.setNombre(turnoEntity.getNombre());
        turnoDto.setHoraInicio(turnoEntity.getHoraInicio());
        turnoDto.setHoraFin(turnoEntity.getHoraFin());
        return turnoDto;
    }
}
