package com.example.microservicio_informacion_centro_medico.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.ImagenDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.RequisitoDto;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosPasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosRequisitosRepositoryJPA;

@Service
public class ProcedimientosService {

    @Autowired
    private ProcedimientosRepositoryJPA procedimientosRepositoryJPA;

    @Autowired
    private PasosProcedimientosElementosService pasosProcedimientosService;

    @Autowired
    private RequisitosProcedimientosElementosService requisitosProcedimientosService;

    @Autowired
    ImagenesService imagenesService;

    public List<ProcedimientoDto> obtenerProcedimientos() {
        List<ProcedimientoEntity> procedimientosEntities = procedimientosRepositoryJPA.findAllByDeletedAtIsNull();
        List<ProcedimientoDto> procedimientosDtos = new ArrayList<>();
        for (ProcedimientoEntity procedimientoEntity : procedimientosEntities) {
            ProcedimientoDto procedimientoDto = new ProcedimientoDto().convertirProcedimientoEntityAProcedimientoDto(procedimientoEntity);
            List<ImagenDto> imagenes = imagenesService.obtenerImagenes("procedimientos", procedimientoEntity.getIdProcedimiento());
            procedimientoDto.setImagenes(imagenes);
            procedimientosDtos.add(procedimientoDto);
        }
        return procedimientosDtos;
    }

    public ProcedimientoDto obtenerProcedimientoPorId(int id) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));
        ProcedimientoDto procedimientoDto = new ProcedimientoDto().convertirProcedimientoEntityAProcedimientoDto(procedimientoEntity);
        List<ImagenDto> imagenes = imagenesService.obtenerImagenes("procedimientos", procedimientoEntity.getIdProcedimiento());
        procedimientoDto.setImagenes(imagenes);
        return procedimientoDto;
    }

    public ProcedimientoDto crearProcedimiento(ProcedimientoDto procedimientoDto, Map<String, MultipartFile> allFiles) {
        ProcedimientoEntity procedimientoEntity = new ProcedimientoEntity();
        procedimientoEntity.setNombreProcedimiento(procedimientoDto.getNombreProcedimiento());

        ProcedimientoEntity savedEntity = procedimientosRepositoryJPA.save(procedimientoEntity);

        List<MultipartFile> imagenes = imagenesService.obtenerImagenesDeArchivos(allFiles);
        imagenesService.guardarImagenes(imagenes, "procedimientos", savedEntity.getIdProcedimiento());

        return new ProcedimientoDto().convertirProcedimientoEntityAProcedimientoDto(savedEntity);
    }

    public ProcedimientoDto actualizarProcedimiento(int id, ProcedimientoDto procedimientoDto, Map<String, MultipartFile> allFiles, Map<String, String> params) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        procedimientoEntity.setNombreProcedimiento(procedimientoDto.getNombreProcedimiento());

        ProcedimientoEntity updatedEntity = procedimientosRepositoryJPA.save(procedimientoEntity);
        imagenesService.actualizarImagenes(allFiles, params, "procedimientos", procedimientoEntity.getIdProcedimiento());

        return new ProcedimientoDto().convertirProcedimientoEntityAProcedimientoDto(updatedEntity);
    }

    public void eliminarProcedimiento(int id) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));
        procedimientoEntity.markAsDeleted();
        procedimientosRepositoryJPA.save(procedimientoEntity);
    }
}