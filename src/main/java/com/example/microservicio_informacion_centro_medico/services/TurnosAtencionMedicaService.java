

package com.example.microservicio_informacion_centro_medico.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microservicio_informacion_centro_medico.model.ComunicadoEntity;
import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.TurnoAtencionMedicaDto;
import com.example.microservicio_informacion_centro_medico.model.util.specifications.TurnosAtencionMedicaSpecification;
import com.example.microservicio_informacion_centro_medico.repository.ConsultoriosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.TurnosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.UsuarioRepositoryJPA;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

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
    Logger logger = LoggerFactory.getLogger(TurnosAtencionMedicaService.class);
    public List<TurnoAtencionMedicaDto> obtenerTurnosAtencionMedicaPorEspecialidad(int idEspecialidad,String fechaInicio, String fechaFin,Integer page,Integer size) {
        try{
            List<TurnosAtencionMedicaEntity>turnosAtencionMedicaEntities=new ArrayList<>();
            Specification<TurnosAtencionMedicaEntity> spec = Specification.where(TurnosAtencionMedicaSpecification.hasEspecialidadId(idEspecialidad));
            if(fechaInicio!=null){
                spec=spec.and(TurnosAtencionMedicaSpecification.greatherEqualThanFechaInicio(fechaInicio));
            }
            if(fechaFin!=null){
                spec=spec.and(TurnosAtencionMedicaSpecification.lessEqualThanFechaFin(fechaFin));
            }
            if(page!=null && size!=null){
                Pageable pageable = PageRequest.of(page, size);
                Page<TurnosAtencionMedicaEntity> turnosAtencionMedicaEntitiesPage=turnosAtencionMedicaRepositoryJPA.findAll(spec,pageable);
                turnosAtencionMedicaEntities=turnosAtencionMedicaEntitiesPage.getContent();
            }else{
                turnosAtencionMedicaEntities=turnosAtencionMedicaRepositoryJPA.findAll(spec);
            }
    
            List<TurnoAtencionMedicaDto> turnosAtencionMedicaDtos = turnosAtencionMedicaEntities.stream()
            .map(turno -> new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(turno))
            .toList();
            return turnosAtencionMedicaDtos;
        }catch(Exception e){
            throw new RuntimeException("Error en la petición obtenerTurnosAtencionMedicaPorEspecialidad");
        }

    }

    
}