package com.example.microservicio_informacion_centro_medico.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.ComunicadoEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.RolUsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.ComunicadoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ConsultorioDto;
import com.example.microservicio_informacion_centro_medico.repository.ComunicadosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.util.specifications.ComunicadosSpecification;
import com.example.microservicio_informacion_centro_medico.util.specifications.MedicosSpecification;


@Service
public class ComunicadosService {

    @Autowired
    private ComunicadosRepositoryJPA comunicadosRepositoryJPA;

    @Autowired
    ImagenesService imagenesService;

    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;

    public List<ComunicadoDto> obtenerComunicados(String tituloCommunicado, Integer page, Integer size) {
        List<ComunicadoEntity> comunicadosEntities = new ArrayList<>();
        Specification<ComunicadoEntity> spec = Specification.where(ComunicadosSpecification.obtenerComunicadosPorParametros(tituloCommunicado));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<ComunicadoEntity> comunicadosEntitiesPage=comunicadosRepositoryJPA.findAll(spec,pageable);
            comunicadosEntities=comunicadosEntitiesPage.getContent();
        }else{
            comunicadosEntities=comunicadosRepositoryJPA.findAll(spec);
        }   
        List<ComunicadoDto> comunicadosDtos = new ArrayList<>();
        for (ComunicadoEntity comunicadoEntity : comunicadosEntities) {
            ComunicadoDto comunicadoDto = new ComunicadoDto().convertirComunicadoEntityAComunicadoDto(comunicadoEntity);
            comunicadoDto.setImagenes(imagenesService.obtenerImagenes("comunicados", Integer.toString(comunicadoEntity.getIdComunicado())));
            comunicadosDtos.add(comunicadoDto);
        }
        return comunicadosDtos;
    }

    public ComunicadoDto obtenerComunicadoPorId(int id) {
        ComunicadoEntity comunicadoEntity = comunicadosRepositoryJPA.findByIdComunicadoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Comunicado no encontrado"));
        ComunicadoDto comunicadoDto = new ComunicadoDto().convertirComunicadoEntityAComunicadoDto(comunicadoEntity);
        comunicadoDto.setImagenes(imagenesService.obtenerImagenes("comunicados", Integer.toString(comunicadoEntity.getIdComunicado())));
        return comunicadoDto;
    }

    public ComunicadoDto crearComunicado(ComunicadoDto comunicadoDto,Map<String,MultipartFile> allFiles) {
        ComunicadoEntity comunicadoEntity = new ComunicadoEntity();
        comunicadoEntity.setTitulo(comunicadoDto.getTitulo());
        comunicadoEntity.setLugar(comunicadoDto.getLugar());
        comunicadoEntity.setIntroduccion(comunicadoDto.getIntroduccion());
        comunicadoEntity.setCuerpo(comunicadoDto.getCuerpo());
        comunicadoEntity.setCitas(comunicadoDto.getCitas());
        comunicadoEntity.setDatosContacto(comunicadoDto.getDatosContacto());
        ComunicadoEntity savedEntity = comunicadosRepositoryJPA.save(comunicadoEntity);
        List<MultipartFile> imagenes=imagenesService.obtenerImagenesDeArchivos(allFiles);
        imagenesService.guardarImagenes(imagenes, "comunicados", Integer.toString(savedEntity.getIdComunicado()));
        return new ComunicadoDto().convertirComunicadoEntityAComunicadoDto(savedEntity);
    }

    public ComunicadoDto actualizarComunicado(int id, ComunicadoDto comunicadoDto,Map<String, MultipartFile> allFiles,Map<String, String> params) {
        ComunicadoEntity comunicadoEntity = comunicadosRepositoryJPA.findByIdComunicadoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Comunicado no encontrado"));

        comunicadoEntity.setTitulo(comunicadoDto.getTitulo());
        comunicadoEntity.setLugar(comunicadoDto.getLugar());
        comunicadoEntity.setIntroduccion(comunicadoDto.getIntroduccion());
        comunicadoEntity.setCuerpo(comunicadoDto.getCuerpo());
        comunicadoEntity.setCitas(comunicadoDto.getCitas());
        comunicadoEntity.setDatosContacto(comunicadoDto.getDatosContacto());
        ComunicadoEntity updatedEntity = comunicadosRepositoryJPA.save(comunicadoEntity);
        imagenesService.actualizarImagenes(allFiles, params, "comunicados",Integer.toString(updatedEntity.getIdComunicado()));
        return new ComunicadoDto().convertirComunicadoEntityAComunicadoDto(updatedEntity);
    }

    public void eliminarComunicado(int id) {
        ComunicadoEntity comunicadoEntity = comunicadosRepositoryJPA.findByIdComunicadoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Comunicado no encontrado"));
        comunicadoEntity.markAsDeleted();
        comunicadosRepositoryJPA.save(comunicadoEntity);
    }
}