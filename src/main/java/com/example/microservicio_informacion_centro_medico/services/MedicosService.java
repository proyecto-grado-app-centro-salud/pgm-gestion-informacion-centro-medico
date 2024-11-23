package com.example.microservicio_informacion_centro_medico.services;

import java.text.SimpleDateFormat;
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
import com.example.microservicio_informacion_centro_medico.model.RolUsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.EspecialidadDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.MedicoDto;
import com.example.microservicio_informacion_centro_medico.model.util.specifications.TurnosAtencionMedicaSpecification;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;
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
    private RolesUsuariosRepositoryJPA rolesUsuariosRepositoryJPA;
    @Autowired
    private ImagenesService imagenesService;
    @Autowired
    private TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;
    public List<MedicoDto> obtenerMedicosEspecialitasPorEspecialidad(Integer idEspecialidad,String fechaInicio,String fechaFin) {
        Date minDate,maxDate;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            minDate = formato.parse(fechaInicio);
            maxDate = formato.parse(fechaFin);
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
}
