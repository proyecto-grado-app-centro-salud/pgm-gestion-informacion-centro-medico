package com.example.microservicio_informacion_centro_medico.services;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.TurnoDto;
import com.example.microservicio_informacion_centro_medico.model.util.specifications.TurnosSpecification;
import com.example.microservicio_informacion_centro_medico.repository.TurnosRepositoryJPA;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service
public class TurnosService {
    @Autowired
    private TurnosRepositoryJPA turnosRepositoryJPA;

    public TurnoDto obtenerTurnoPorId(int idTurno) {
        TurnoEntity turnoEntity = turnosRepositoryJPA.findByIdTurnoAndDeletedAtIsNull(idTurno)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        return new TurnoDto().convertirTurnoEntityTurnoDto(turnoEntity);
    }
    public void eliminarTurno(int idTurno) {
        turnosRepositoryJPA.deleteById(idTurno);
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    public TurnoDto actualizarTurno(int idTurno, TurnoDto turnoDto) {
        TurnoEntity turnoEntity = turnosRepositoryJPA.findByIdTurnoAndDeletedAtIsNull(idTurno)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        
        turnoEntity.setNombre(turnoDto.getNombre());
        turnoEntity.setHoraInicio(LocalTime.parse(turnoDto.getHoraInicio(),formatter));
        turnoEntity.setHoraFin(LocalTime.parse(turnoDto.getHoraFin(),formatter));

        TurnoEntity updatedEntity = turnosRepositoryJPA.save(turnoEntity);
        return new TurnoDto().convertirTurnoEntityTurnoDto(updatedEntity);
    }
    public TurnoDto crearTurno(TurnoDto turnoDto) {
        TurnoEntity turnoEntity = new TurnoEntity();
        turnoEntity.setNombre(turnoDto.getNombre());
        turnoEntity.setHoraInicio(LocalTime.parse(turnoDto.getHoraInicio(),formatter));
        turnoEntity.setHoraFin(LocalTime.parse(turnoDto.getHoraFin(),formatter));
        TurnoEntity turnoCreado = turnosRepositoryJPA.save(turnoEntity);
        return new TurnoDto().convertirTurnoEntityTurnoDto(turnoCreado);
    }
    public List<TurnoDto> obtenerTodosLosTurnos(String horaInicio, String horaFin, Integer page, Integer size) {
        List<TurnoEntity>turnosEntities=new ArrayList<>();
        Specification<TurnoEntity> spec = Specification.where(TurnosSpecification.obtenerTurnosPorParametros(horaInicio, horaFin));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<TurnoEntity> turnosEntitiesPage=turnosRepositoryJPA.findAll(spec,pageable);
            turnosEntities=turnosEntitiesPage.getContent();
        }else{
            turnosEntities=turnosRepositoryJPA.findAll(spec);
        }
        return turnosEntities.stream()
            .map(turnoEntity -> new TurnoDto().convertirTurnoEntityTurnoDto(turnoEntity))
            .collect(Collectors.toList());
        }
}
