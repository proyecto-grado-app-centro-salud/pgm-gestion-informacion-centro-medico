package com.example.microservicio_informacion_centro_medico.services;

import java.util.List;
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
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoId;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoPasoId;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoRequisitoId;
import com.example.microservicio_informacion_centro_medico.repository.PasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosPasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosRequisitosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.RequisitosRepositoryJPA;

@Service
public class RequisitosProcedimientosElementosService {
    @Autowired
    private ProcedimientosElementosRequisitosRepositoryJPA procedimientosElementosRequisitosRepositoryJPA;

    @Autowired
    private ProcedimientosElementosRepositoryJPA procedimientosElementosRepositoryJPA;


    @Autowired
    private RequisitosRepositoryJPA requisitosRepositoryJPA;

    @Autowired
    ImagenesService imagenesService;

    public List<RequisitoDto> obtenerRequisitosDeProcedimientoElemento(int idProcedimiento, int idElemento, String tipoElemento) {


        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findById(new ProcedimientoElementoId(idProcedimiento,idElemento,tipoElemento))
            .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        List<RequisitoDto> pasosDtos = procedimientosElementosRequisitosRepositoryJPA.findByProcedimientoElemento(procedimientoElementoEntity)
                                          .stream()
                                          .map(procedimientoElementoRequisitoEntity->{
                                            RequisitoDto requisitoDto = new RequisitoDto().convertirRequisitoEntityARequisitoDto(procedimientoElementoRequisitoEntity.getRequisito()); 
                                            requisitoDto.setImagenes(imagenesService.obtenerImagenes("requisitos", requisitoDto.getIdRequisito()));
                                            return requisitoDto;
                                           })
                                          .collect(Collectors.toList());
        return pasosDtos;
    }


    public void eliminarRequisitoProcedimientoElemento(int idProcedimiento, int idRequisito, int idElemento, String tipoElemento) {
        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findById(new ProcedimientoElementoId(idProcedimiento,idElemento,tipoElemento))
        .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));


        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findById(idRequisito)
        .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));
        procedimientosElementosRequisitosRepositoryJPA.deleteById(new ProcedimientoElementoRequisitoId(idProcedimiento,idElemento,tipoElemento,idRequisito));
    }


    public void crearRequisitoProcedimientoElemento(int idProcedimiento, int idRequisito, int idElemento, String tipoElemento) {
        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findById(new ProcedimientoElementoId(idProcedimiento,idElemento,tipoElemento))
        .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));

        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findById(idRequisito)
        .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));
        ProcedimientoElementoRequisitoId procedimientoElementoRequisitoId=new ProcedimientoElementoRequisitoId(idProcedimiento,idElemento,tipoElemento,idRequisito);
        procedimientosElementosRequisitosRepositoryJPA.save(new ProcedimientoElementoRequisitoEntity(procedimientoElementoRequisitoId,procedimientoElementoEntity,requisitoEntity));
    }
}
