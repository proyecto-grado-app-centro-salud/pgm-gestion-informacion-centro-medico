

package com.example.microservicio_informacion_centro_medico.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.TurnoAtencionMedicaDto;
import com.example.microservicio_informacion_centro_medico.repository.ConsultoriosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.TurnosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.UsuarioRepositoryJPA;

@Service
public class TurnosAtencionMedicaService {
    @Autowired
    private TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;
    @Autowired
    private TurnosRepositoryJPA turnosRepositoryJPA;
    @Autowired
    private ConsultoriosRepositoryJPA consultoriosRepositoryJPA;
    @Autowired
    private UsuarioRepositoryJPA usuarioRepositoryJPA;

    public TurnoAtencionMedicaDto crearHorarioAtencion(TurnoAtencionMedicaDto turnoAtencionMedicaDto) {
        TurnoEntity turnoEntity = turnosRepositoryJPA.findById(turnoAtencionMedicaDto.getIdTurno())
        .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdConsultorio())
        .orElseThrow(() -> new RuntimeException("Consultorio no encontrado"));
        UsuarioEntity medicoEntity = usuarioRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        TurnosAtencionMedicaEntity createdEntity = new TurnosAtencionMedicaEntity();
        createdEntity.setFecha(turnoAtencionMedicaDto.getFecha());
        createdEntity.setNumeroFichasDisponible(turnoAtencionMedicaDto.getNumeroFichasDisponible());
        createdEntity.setConsultorio(consultorioEntity);
        createdEntity.setTurno(turnoEntity);
        createdEntity.setMedico(medicoEntity);
        TurnosAtencionMedicaEntity savedEntity = turnosAtencionMedicaRepositoryJPA.save(createdEntity);
        return new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(savedEntity);
    }

    public void eliminarHorarioAtencion(int idHorariosAtencionMedica) {
        turnosAtencionMedicaRepositoryJPA.deleteById(idHorariosAtencionMedica);
    }

    public List<TurnoAtencionMedicaDto> obtenerHorariosAtencionDetalle() {
        List<TurnosAtencionMedicaEntity> turnosAtencionMedicaEntities=turnosAtencionMedicaRepositoryJPA.findAll();
        List<TurnoAtencionMedicaDto> turnosAtencionMedicaDtos = turnosAtencionMedicaEntities.stream()
        .map(turno -> new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(turno))
        .toList();
        return turnosAtencionMedicaDtos;
    }

    public TurnoAtencionMedicaDto obtenerHorarioAtencionDetalle(int idHorariosAtencionMedica) {
        TurnosAtencionMedicaEntity turnosAtencionMedicaEntity = turnosAtencionMedicaRepositoryJPA.findById(idHorariosAtencionMedica)
        .orElseThrow(() -> new RuntimeException("Turno atencion medica no encontrado"));
        return new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(turnosAtencionMedicaEntity);
    }
    
}