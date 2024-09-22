package com.example.microservicio_informacion_centro_medico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoPasoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.RequisitoDto;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoPasoId;
import com.example.microservicio_informacion_centro_medico.repository.PasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosPasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosRepositoryJPA;

@Service
public class PasosProcedimientosService {
    @Autowired
    private ProcedimientosPasosRepositoryJPA procedimientosPasosRepositoryJPA;

    @Autowired
    private ProcedimientosRepositoryJPA procedimientosRepositoryJPA;

    @Autowired
    private PasosRepositoryJPA pasosRepositoryJPA;

    @Autowired
    ImagenesService imagenesService;

    public List<PasoDto> obtenerPasosDeProcedimiento(int idProcedimiento) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
            .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        List<PasoDto> pasosDtos = procedimientosPasosRepositoryJPA.findByProcedimiento(procedimientoEntity)
                                          .stream()
                                          .map(procedimientoPasoEntity->{
                                            PasoDto pasoDto = new PasoDto().convertirPasoEntityAPasoDto(procedimientoPasoEntity.getPaso()); 
                                            pasoDto.setImagenes(imagenesService.obtenerImagenes("pasos", pasoDto.getIdPaso()));
                                            return pasoDto;
                                           })
                                          .collect(Collectors.toList());
        return pasosDtos;
    }


    public void eliminarPasoProcedimiento(int idProcedimiento, int idPaso) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        PasoEntity pasoEntity = pasosRepositoryJPA.findById(idPaso)
        .orElseThrow(() -> new RuntimeException("Paso no encontrado"));
        procedimientosPasosRepositoryJPA.deleteById(new ProcedimientoPasoId(procedimientoEntity.getIdProcedimiento(),pasoEntity.getIdPaso()));
    }


    public void crearPasoProcedimiento(int idProcedimiento, int idPaso) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        PasoEntity pasoEntity = pasosRepositoryJPA.findById(idPaso)
        .orElseThrow(() -> new RuntimeException("Paso no encontrado"));

        ProcedimientoPasoEntity procedimientoPasoEntity=new ProcedimientoPasoEntity(new ProcedimientoPasoId(procedimientoEntity.getIdProcedimiento(),pasoEntity.getIdPaso()),procedimientoEntity,pasoEntity);
        procedimientosPasosRepositoryJPA.save(procedimientoPasoEntity);

    }
    
}
