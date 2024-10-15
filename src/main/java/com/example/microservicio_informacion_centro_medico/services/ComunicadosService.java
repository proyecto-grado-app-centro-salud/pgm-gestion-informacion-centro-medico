package com.example.microservicio_informacion_centro_medico.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.ComunicadoEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.ComunicadoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ConsultorioDto;
import com.example.microservicio_informacion_centro_medico.repository.ComunicadosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.EspecialidadesRepositoryJPA;


@Service
public class ComunicadosService {

    @Autowired
    private ComunicadosRepositoryJPA comunicadosRepositoryJPA;

    @Autowired
    ImagenesService imagenesService;

    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;

    public List<ComunicadoDto> obtenerComunicados() {
        List<ComunicadoEntity> comunicadosEntities = comunicadosRepositoryJPA.findAllByDeletedAtIsNull();
        List<ComunicadoDto> comunicadosDtos = new ArrayList<>();
        for (ComunicadoEntity comunicadoEntity : comunicadosEntities) {
            ComunicadoDto comunicadoDto = new ComunicadoDto().convertirComunicadoEntityAComunicadoDto(comunicadoEntity);
            comunicadoDto.setImagenes(imagenesService.obtenerImagenes("comunicados", comunicadoEntity.getIdComunicado()));
            comunicadosDtos.add(comunicadoDto);
        }
        return comunicadosDtos;
    }

    public ComunicadoDto obtenerComunicadoPorId(int id) {
        ComunicadoEntity comunicadoEntity = comunicadosRepositoryJPA.findByIdComunicadoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Comunicado no encontrado"));
        ComunicadoDto comunicadoDto = new ComunicadoDto().convertirComunicadoEntityAComunicadoDto(comunicadoEntity);
        comunicadoDto.setImagenes(imagenesService.obtenerImagenes("comunicados", comunicadoEntity.getIdComunicado()));
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
        imagenesService.guardarImagenes(imagenes, "comunicados", savedEntity.getIdComunicado());
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
        imagenesService.actualizarImagenes(allFiles, params, "comunicados", comunicadoEntity.getIdComunicado());
        return new ComunicadoDto().convertirComunicadoEntityAComunicadoDto(updatedEntity);
    }

    public void eliminarComunicado(int id) {
        ComunicadoEntity comunicadoEntity = comunicadosRepositoryJPA.findByIdComunicadoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Comunicado no encontrado"));
        comunicadoEntity.markAsDeleted();
        comunicadosRepositoryJPA.save(comunicadoEntity);
    }
}