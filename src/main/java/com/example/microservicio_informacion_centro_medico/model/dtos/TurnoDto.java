package com.example.microservicio_informacion_centro_medico.model.dtos;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private String horaInicio;
    private String horaFin;

    public TurnoDto convertirTurnoEntityTurnoDto(TurnoEntity turnoEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        TurnoDto turnoDto = new TurnoDto();
        turnoDto.setIdTurno(turnoEntity.getIdTurno());
        turnoDto.setNombre(turnoEntity.getNombre());
        turnoDto.setHoraInicio(turnoEntity.getHoraInicio().format(formatter));
        turnoDto.setHoraFin(turnoEntity.getHoraFin().format(formatter));
        return turnoDto;
    }
}
