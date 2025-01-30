package com.example.microservicio_informacion_centro_medico.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoPasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoRequisitoEntity;
import com.example.microservicio_informacion_centro_medico.model.RequisitoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.RequisitoDto;
import com.example.microservicio_informacion_centro_medico.model.util.exceptions.BusinessValidationException;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoId;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoPasoId;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoRequisitoId;
import com.example.microservicio_informacion_centro_medico.repository.PasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosPasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosRequisitosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.RequisitosRepositoryJPA;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class RequisitosProcedimientosElementosService {
    @Autowired
    private ProcedimientosElementosRequisitosRepositoryJPA procedimientosElementosRequisitosRepositoryJPA;

    @Autowired
    private ProcedimientosElementosRepositoryJPA procedimientosElementosRepositoryJPA;


    @Autowired
    private RequisitosRepositoryJPA requisitosRepositoryJPA;

    @Autowired
    private ProcedimientosElementosService procedimientosElementosService;


    @Autowired
    ImagenesService imagenesService;

    @Autowired
    private ProcedimientosRepositoryJPA procedimientosRepositoryJPA;

    public List<RequisitoDto> obtenerRequisitosDeProcedimientoElemento(int idProcedimiento, int idElemento, String tipoElemento) throws JsonProcessingException {


        procedimientosElementosService.verificarExisteciaElemento(idElemento,tipoElemento);

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findOneByIdElementoAndTipoElementoAndProcedimiento(idElemento,tipoElemento,procedimientoEntity)
            .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));

        List<RequisitoDto> requisitosDtos = procedimientosElementosRequisitosRepositoryJPA.findByProcedimientoElemento(procedimientoElementoEntity)
                                          .stream()
                                          .map(procedimientoElementoRequisitoEntity->{
                                            RequisitoDto requisitoDto = new RequisitoDto().convertirRequisitoEntityARequisitoDto(procedimientoElementoRequisitoEntity.getRequisito()); 
                                            requisitoDto.setImagenes(imagenesService.obtenerImagenes("requisitos", requisitoDto.getIdRequisito()+""));
                                            return requisitoDto;
                                           })
                                          .collect(Collectors.toList());
        return requisitosDtos;
    }


    public void eliminarRequisitoProcedimientoElemento(int idProcedimiento, int idRequisito, int idElemento, String tipoElemento) throws JsonProcessingException {
        procedimientosElementosService.verificarExisteciaElemento(idElemento,tipoElemento);

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findOneByIdElementoAndTipoElementoAndProcedimiento(idElemento,tipoElemento,procedimientoEntity)
            .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));

        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findByIdRequisitoAndDeletedAtIsNull(idRequisito)
        .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));

        ProcedimientoElementoRequisitoEntity procedimientoElementoRequisitoEntity = procedimientosElementosRequisitosRepositoryJPA.findOneByProcedimientoElementoAndRequisito(procedimientoElementoEntity, requisitoEntity)
        .orElseThrow(() -> new RuntimeException("Procedimiento elemento paso no encontrado"));


        procedimientosElementosRequisitosRepositoryJPA.delete(procedimientoElementoRequisitoEntity);
    }


    public void crearRequisitoProcedimientoElemento(int idProcedimiento, int idRequisito, int idElemento, String tipoElemento) throws JsonProcessingException {
        procedimientosElementosService.verificarExisteciaElemento(idElemento,tipoElemento);

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findOneByIdElementoAndTipoElementoAndProcedimiento(idElemento,tipoElemento,procedimientoEntity)
            .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));

        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findByIdRequisitoAndDeletedAtIsNull(idRequisito)
        .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));

        Optional<ProcedimientoElementoRequisitoEntity> procedimientoElementoRequisitoEncontradoEntity = procedimientosElementosRequisitosRepositoryJPA.findOneByProcedimientoElementoAndRequisito(procedimientoElementoEntity, requisitoEntity);
        if(procedimientoElementoRequisitoEncontradoEntity.isPresent()) throw new BusinessValidationException("El requisito ya fue añadido al elemento");

        ProcedimientoElementoRequisitoEntity procedimientoElementoRequisitoEntity = new ProcedimientoElementoRequisitoEntity();
        procedimientoElementoRequisitoEntity.setProcedimientoElemento(procedimientoElementoEntity);
        procedimientoElementoRequisitoEntity.setRequisito(requisitoEntity);

        procedimientosElementosRequisitosRepositoryJPA.save(procedimientoElementoRequisitoEntity);
    }
}
