package com.example.microservicio_informacion_centro_medico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoPasoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.RequisitoDto;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoId;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoPasoId;
import com.example.microservicio_informacion_centro_medico.repository.PasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosPasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosRepositoryJPA;

@Service
public class PasosProcedimientosElementosService {
    @Autowired
    private ProcedimientosElementosPasosRepositoryJPA procedimientosElementosPasosRepositoryJPA;

    @Autowired
    private ProcedimientosElementosRepositoryJPA procedimientosElementosRepositoryJPA;

    @Autowired
    private ProcedimientosElementosService procedimientosElementosService;

    @Autowired
    private ProcedimientosRepositoryJPA procedimientosRepositoryJPA;


    @Autowired
    private PasosRepositoryJPA pasosRepositoryJPA;

    @Autowired
    ImagenesService imagenesService;

    public List<PasoDto> obtenerPasosDeProcedimiento(int idProcedimiento, int idElemento, String tipoElemento) {

        procedimientosElementosService.verificarExisteciaElemento(idElemento,tipoElemento);

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findOneByIdElementoAndTipoElementoAndProcedimiento(idElemento,tipoElemento,procedimientoEntity)
            .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));

        List<PasoDto> pasosDtos = procedimientosElementosPasosRepositoryJPA.findByProcedimientoElemento(procedimientoElementoEntity)
                                          .stream()
                                          .map(procedimientoElementoPasoEntity->{
                                            PasoDto pasoDto = new PasoDto().convertirPasoEntityAPasoDto(procedimientoElementoPasoEntity.getPaso()); 
                                            pasoDto.setImagenes(imagenesService.obtenerImagenes("pasos", pasoDto.getIdPaso()+""));
                                            return pasoDto;
                                           })
                                          .collect(Collectors.toList());
        return pasosDtos;
    }


    public void eliminarPasoProcedimientoElemento(int idProcedimiento, int idPaso, int idElemento, String tipoElemento) {
        procedimientosElementosService.verificarExisteciaElemento(idElemento,tipoElemento);

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findOneByIdElementoAndTipoElementoAndProcedimiento(idElemento,tipoElemento,procedimientoEntity)
            .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));

        PasoEntity pasoEntity = pasosRepositoryJPA.findById(idPaso)
        .orElseThrow(() -> new RuntimeException("Paso no encontrado"));

        ProcedimientoElementoPasoEntity procedimientoElementoPasoEntity = procedimientosElementosPasosRepositoryJPA.findOneByProcedimientoElementoAndPaso(procedimientoElementoEntity, pasoEntity)
        .orElseThrow(() -> new RuntimeException("Procedimiento elemento paso no encontrado"));

        procedimientosElementosPasosRepositoryJPA.delete(procedimientoElementoPasoEntity);
    }


    public void crearPasoProcedimientoElemento(int idProcedimiento, int idPaso, int idElemento, String tipoElemento) {
        procedimientosElementosService.verificarExisteciaElemento(idElemento,tipoElemento);

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findOneByIdElementoAndTipoElementoAndProcedimiento(idElemento,tipoElemento,procedimientoEntity)
            .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));

        PasoEntity pasoEntity = pasosRepositoryJPA.findById(idPaso)
        .orElseThrow(() -> new RuntimeException("Paso no encontrado"));

        ProcedimientoElementoPasoEntity procedimientoElementoPasoEntity = new ProcedimientoElementoPasoEntity();
        procedimientoElementoPasoEntity.setPaso(pasoEntity);
        procedimientoElementoPasoEntity.setProcedimientoElemento(procedimientoElementoEntity);

        procedimientosElementosPasosRepositoryJPA.save(procedimientoElementoPasoEntity);
    }
    
}
