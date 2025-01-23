

package com.example.microservicio_informacion_centro_medico.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import com.example.microservicio_informacion_centro_medico.model.util.exceptions.BusinessValidationException;
import com.example.microservicio_informacion_centro_medico.model.util.specifications.TurnosAtencionMedicaSpecification;
import com.example.microservicio_informacion_centro_medico.model.util.specifications.TurnosSpecification;
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
    private ConsultasMedicasService consultasMedicasService;
    @Autowired
    private TurnosRepositoryJPA turnosRepositoryJPA;
    @Autowired
    private ConsultoriosRepositoryJPA consultoriosRepositoryJPA;
    @Autowired
    private UsuarioRepositoryJPA usuarioRepositoryJPA;

    @Autowired
    private ConvertirTiposDatosService convertirTiposDatosService;

    public TurnoAtencionMedicaDto crearHorarioAtencion(TurnoAtencionMedicaDto turnoAtencionMedicaDto) throws Exception {
                // SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TurnoEntity turnoEntity = turnosRepositoryJPA.findByIdTurnoAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdTurno())
        .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdConsultorio())
        .orElseThrow(() -> new RuntimeException("Consultorio no encontrado")); 
        UsuarioEntity medicoEntity = usuarioRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        if(verificarMedicoOcupadoEnHorarioDeAtencion(medicoEntity.getIdUsuario(),convertirTiposDatosService.convertirStringALocalDate(turnoAtencionMedicaDto.getFecha()), turnoEntity.getHoraInicio(), turnoEntity.getHoraFin()))  throw new BusinessValidationException("El horario del medico esta ocupado");
        if(verificarConsultorioOcupadoEnHorarioDeAtencion(consultorioEntity.getIdConsultorio(),convertirTiposDatosService.convertirStringALocalDate(turnoAtencionMedicaDto.getFecha()), turnoEntity.getHoraInicio(), turnoEntity.getHoraFin()))  throw new BusinessValidationException("El consutorio esta ocupado en el horario seleccionado");
        TurnosAtencionMedicaEntity createdEntity = new TurnosAtencionMedicaEntity();
        createdEntity.setFecha(LocalDate.parse(turnoAtencionMedicaDto.getFecha(), formato));
        createdEntity.setNumeroFichasDisponible(turnoAtencionMedicaDto.getNumeroFichasAsignado());
        createdEntity.setNumeroFichasAsignado(turnoAtencionMedicaDto.getNumeroFichasAsignado());
        createdEntity.setConsultorio(consultorioEntity);
        createdEntity.setTurno(turnoEntity);
        createdEntity.setMedico(medicoEntity);
        TurnosAtencionMedicaEntity savedEntity = turnosAtencionMedicaRepositoryJPA.save(createdEntity);
        return new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(savedEntity);
    }
    public boolean verificarConsultorioOcupadoEnHorarioDeAtencion(int idConsultorio,LocalDate fecha, LocalTime horaInicio,LocalTime horaFin){
        List<TurnoAtencionMedicaDto> turnosActualesDeConsultorio=obtenerHorariosAtencionDeConsultorio(idConsultorio,convertirTiposDatosService.convertirLocalDateAString(fecha),convertirTiposDatosService.convertirLocalDateAString(fecha),null,null).getContent();
        for(TurnoAtencionMedicaDto turnoActual:turnosActualesDeConsultorio){
            if((convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraInicio()).isAfter(horaInicio) || 
            convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraInicio()).equals(horaInicio)) && (convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraInicio()).isBefore(horaFin))){
                return true;
            }
            if((convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraFin()).isBefore(horaFin) || 
            convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraFin()).equals(horaFin)) && (convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraFin()).isAfter(horaInicio))){
                return true;
            }
        }
        return false;
    }
    public boolean verificarMedicoOcupadoEnHorarioDeAtencion(String idMedico,LocalDate fecha, LocalTime horaInicio,LocalTime horaFin){
        List<TurnoAtencionMedicaDto> turnosActualesDeMedico=obtenerHorariosAtencionDeMedico(idMedico,convertirTiposDatosService.convertirLocalDateAString(fecha),convertirTiposDatosService.convertirLocalDateAString(fecha),null,null).getContent();
        for(TurnoAtencionMedicaDto turnoActual:turnosActualesDeMedico){
            if((convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraInicio()).isAfter(horaInicio) || 
            convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraInicio()).equals(horaInicio)) && (convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraInicio()).isBefore(horaFin))){
                return true;
            }
            if((convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraFin()).isBefore(horaFin) || 
            convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraFin()).equals(horaFin)) && (convertirTiposDatosService.convertirStringALocalTime(turnoActual.getHoraFin()).isAfter(horaInicio))){
                return true;
            }
        }
        return false;
    }
    public void eliminarHorarioAtencion(int idHorariosAtencionMedica) throws Exception {
        TurnosAtencionMedicaEntity turnosAtencionMedicaEntity = turnosAtencionMedicaRepositoryJPA.findByIdTurnoAtencionMedicaAndDeletedAtIsNull(idHorariosAtencionMedica).
        orElseThrow(()->new Exception("Turno atencion medica no encontrado"));
        
        turnosAtencionMedicaEntity.markAsDeleted();
        consultasMedicasService.eliminarConsultasMedicasDeTurnoAtencionMedica(idHorariosAtencionMedica);
        turnosAtencionMedicaRepositoryJPA.save(turnosAtencionMedicaEntity);
    }
    public Page<TurnoAtencionMedicaDto> obtenerHorariosAtencionDeMedico(String idMedico,String fechaInicio, String fechaFin, Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        }
        Specification<TurnosAtencionMedicaEntity> spec = Specification.where(TurnosAtencionMedicaSpecification.obtenerHorariosAtencionDeMedico(idMedico,convertirTiposDatosService.convertirStringALocalDate(fechaInicio),convertirTiposDatosService.convertirStringALocalDate(fechaFin)));
        Page<TurnosAtencionMedicaEntity> turnosAtencionMedicaEntities=turnosAtencionMedicaRepositoryJPA.findAll(spec,pageable);
        return turnosAtencionMedicaEntities.map(TurnoAtencionMedicaDto::convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto);
    }
    public Page<TurnoAtencionMedicaDto> obtenerHorariosAtencionDeConsultorio(int idConsultorio,String fechaInicio, String fechaFin, Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        }
        Specification<TurnosAtencionMedicaEntity> spec = Specification.where(TurnosAtencionMedicaSpecification.obtenerHorariosAtencionDeConsultorio(idConsultorio,convertirTiposDatosService.convertirStringALocalDate(fechaInicio),convertirTiposDatosService.convertirStringALocalDate(fechaFin)));
        Page<TurnosAtencionMedicaEntity> turnosAtencionMedicaEntities=turnosAtencionMedicaRepositoryJPA.findAll(spec,pageable);
        return turnosAtencionMedicaEntities.map(TurnoAtencionMedicaDto::convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto);
    }
    public List<TurnoAtencionMedicaDto> obtenerHorariosAtencionDetalle(String fechaInicio, String fechaFin, Integer page, Integer size) {
        List<TurnosAtencionMedicaEntity>turnosAtencionMedicaEntities=new ArrayList<>();
        Specification<TurnosAtencionMedicaEntity> spec = Specification.where(TurnosAtencionMedicaSpecification.deletedAtIsNull());
        
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
    }

    public TurnoAtencionMedicaDto obtenerHorarioAtencionDetalle(int idHorariosAtencionMedica) {
        TurnosAtencionMedicaEntity turnosAtencionMedicaEntity = turnosAtencionMedicaRepositoryJPA.findByIdTurnoAtencionMedicaAndDeletedAtIsNull(idHorariosAtencionMedica)
        .orElseThrow(() -> new RuntimeException("Turno atencion medica no encontrado"));
        return new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(turnosAtencionMedicaEntity);
    }
    Logger logger = LoggerFactory.getLogger(TurnosAtencionMedicaService.class);
    public List<TurnoAtencionMedicaDto> obtenerTurnosAtencionMedicaPorEspecialidad(int idEspecialidad,String fechaInicio, String fechaFin,Integer page,Integer size) {
        try{
            List<TurnosAtencionMedicaEntity>turnosAtencionMedicaEntities=new ArrayList<>();
            Specification<TurnosAtencionMedicaEntity> spec = Specification.where(TurnosAtencionMedicaSpecification.hasEspecialidadId(idEspecialidad));
            spec=spec.and(TurnosAtencionMedicaSpecification.deletedAtIsNull());
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
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<TurnosAtencionMedicaEntity> obtenerTurnosAtencionMedicaConsultorio(int idConsultorio) throws Exception {
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(idConsultorio).orElseThrow(()->new Exception("Consultorio no encontrado"));
        return turnosAtencionMedicaRepositoryJPA.findAllByConsultorioAndDeletedAtIsNull(consultorioEntity);
    }

    
}