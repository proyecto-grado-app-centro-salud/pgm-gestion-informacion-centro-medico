package com.example.microservicio_informacion_centro_medico.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.ImagenDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.repository.PasosRepositoryJPA;

@Service
public class PasosService {

    @Autowired
    private PasosRepositoryJPA pasosRepositoryJPA;

    @Autowired
    private ImagenesService imagenesService;

    public List<PasoDto> obtenerPasos() {
        List<PasoEntity> pasosEntities = pasosRepositoryJPA.findAllByDeletedAtIsNull();
        List<PasoDto> pasosDtos = new ArrayList<>();
        for (PasoEntity pasoEntity : pasosEntities) {
            PasoDto pasoDto = new PasoDto().convertirPasoEntityAPasoDto(pasoEntity);
            List<ImagenDto> imagenes = imagenesService.obtenerImagenes("pasos", pasoEntity.getIdPaso());
            pasoDto.setImagenes(imagenes);
            pasosDtos.add(pasoDto);
        }
        return pasosDtos;
    }

    public PasoDto obtenerPasoPorId(int id) {
        PasoEntity pasoEntity = pasosRepositoryJPA.findByIdPasoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Paso no encontrado"));
        PasoDto pasoDto = new PasoDto().convertirPasoEntityAPasoDto(pasoEntity);
        List<ImagenDto> imagenes = imagenesService.obtenerImagenes("pasos", pasoEntity.getIdPaso());
        pasoDto.setImagenes(imagenes);
        return pasoDto;
    }

    public PasoDto crearPaso(PasoDto pasoDto, Map<String, MultipartFile> allFiles) {
        PasoEntity pasoEntity = new PasoEntity();
        pasoEntity.setNombre(pasoDto.getNombre());
        pasoEntity.setDescripcion(pasoDto.getDescripcion());

        PasoEntity savedEntity = pasosRepositoryJPA.save(pasoEntity);

        List<MultipartFile> imagenes = imagenesService.obtenerImagenesDeArchivos(allFiles);
        imagenesService.guardarImagenes(imagenes, "pasos", savedEntity.getIdPaso());

        return new PasoDto().convertirPasoEntityAPasoDto(savedEntity);
    }

    public PasoDto actualizarPaso(int id, PasoDto pasoDto, Map<String, MultipartFile> allFiles, Map<String, String> params) {
        PasoEntity pasoEntity = pasosRepositoryJPA.findByIdPasoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Paso no encontrado"));

        pasoEntity.setNombre(pasoDto.getNombre());
        pasoEntity.setDescripcion(pasoDto.getDescripcion());

        PasoEntity updatedEntity = pasosRepositoryJPA.save(pasoEntity);
        imagenesService.actualizarImagenes(allFiles, params, "pasos", pasoEntity.getIdPaso());

        return new PasoDto().convertirPasoEntityAPasoDto(updatedEntity);
    }

    public void eliminarPaso(int id) {
        PasoEntity pasoEntity = pasosRepositoryJPA.findByIdPasoAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Paso no encontrado"));
        pasoEntity.markAsDeleted();
        pasosRepositoryJPA.save(pasoEntity);
    }
}