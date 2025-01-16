package com.example.microservicio_informacion_centro_medico.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.MedicoEntity;
import com.example.microservicio_informacion_centro_medico.model.RolEntity;
import com.example.microservicio_informacion_centro_medico.model.RolUsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.EspecialidadDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.MedicoDto;
import com.example.microservicio_informacion_centro_medico.model.util.specifications.TurnosAtencionMedicaSpecification;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.RolesRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.RolesUsuariosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.UsuarioRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.util.specifications.EspecialidadesSpecification;
import com.example.microservicio_informacion_centro_medico.util.specifications.MedicosSpecification;

@Service
public class MedicosService {
    @Autowired
    private EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    @Autowired
    private UsuarioRepositoryJPA usuarioRepositoryJPA;
    @Autowired
    private RolesRepositoryJPA rolesRepositoryJPA;
    @Autowired
    private RolesUsuariosRepositoryJPA rolesUsuariosRepositoryJPA;
    @Autowired
    private ImagenesService imagenesService;
    @Autowired
    private TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;
    public List<MedicoDto> obtenerMedicosEspecialitasPorEspecialidad(Integer idEspecialidad,String fechaInicio,String fechaFin) {
        LocalDate minDate,maxDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            minDate = LocalDate.parse(fechaInicio, formatter);
            maxDate = LocalDate.parse(fechaFin, formatter);
        } catch (Exception e) {
            throw new RuntimeException("Fecha inválida");
        }
        EspecialidadEntity especialidad = especialidadesRepositoryJPA.findByIdEspecialidadAndDeletedAtIsNull(idEspecialidad).orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        List<UsuarioEntity>usuarioEntities=turnosAtencionMedicaRepositoryJPA.findAllMedicosDeEspecialidadEnTurnosDeAtencionMedica(idEspecialidad, minDate, maxDate);
        List<MedicoDto> medicosDtos = usuarioEntities.stream()
        .map(medico -> new MedicoDto().convertirUsuarioEntityAMedicoDto(medico))
        .toList();
        return medicosDtos;
    }
    public List<MedicoDto> obtenerEquipoMedico(String nombreMedico, Integer page, Integer size) {
        List<RolUsuarioEntity>rolesUsuariosEntities=new ArrayList<>();
        Specification<RolUsuarioEntity> spec = Specification.where(MedicosSpecification.obtenerMedicos(nombreMedico));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<RolUsuarioEntity> rolesUsuariosEntitiesPage=rolesUsuariosRepositoryJPA.findAll(spec,pageable);
            rolesUsuariosEntities=rolesUsuariosEntitiesPage.getContent();
        }else{
            rolesUsuariosEntities=rolesUsuariosRepositoryJPA.findAll(spec);
        }   
        
        List<MedicoDto> especialidadesDtos = rolesUsuariosEntities.stream().map((rolUsuarioEntities) -> {
            MedicoDto medicoDto = new MedicoDto().convertirRolUsuarioEntityAMedicoDto(rolUsuarioEntities);
            medicoDto.setImagenes(imagenesService.obtenerImagenes("roles-usuarios", rolUsuarioEntities.getIdUsuarioRol()+""));
            return medicoDto;
        }).toList();
        
        return especialidadesDtos;
    }   
    public MedicoDto obtenerMedicoEspecialitas(String idMedico) {
        RolEntity rolEntity = rolesRepositoryJPA.findById(2)
        .orElseThrow(()-> new RuntimeException("Rol no encontrado"));
        UsuarioEntity usuarioEntity = usuarioRepositoryJPA.findById(idMedico)
        .orElseThrow(()-> new RuntimeException("Medico no encontrado"));
        RolUsuarioEntity rolUsuarioEntity=rolesUsuariosRepositoryJPA.findOneByUsuarioAndRol(usuarioEntity,rolEntity)
        .orElseThrow(()-> new RuntimeException("Rol usuario no encontrado"));
        MedicoDto medicoDto = new MedicoDto().convertirRolUsuarioEntityAMedicoDto(rolUsuarioEntity);
        medicoDto.setImagenes(imagenesService.obtenerImagenes("medicos", rolUsuarioEntity.getUsuario().getIdUsuario()));
        return medicoDto;
    }
    
}
