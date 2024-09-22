package com.example.microservicio_informacion_centro_medico.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_informacion_centro_medico.model.PasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoPasoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoRequisitoEntity;
import com.example.microservicio_informacion_centro_medico.model.RequisitoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.RequisitoDto;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoPasoId;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoRequisitoId;
import com.example.microservicio_informacion_centro_medico.repository.PasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosPasosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosRequisitosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.RequisitosRepositoryJPA;

@Service
public class RequisitosProcedimientosService {
    @Autowired
    private ProcedimientosRequisitosRepositoryJPA procedimientosRequisitosRepositoryJPA;

    @Autowired
    private ProcedimientosRepositoryJPA procedimientosRepositoryJPA;

    @Autowired
    private RequisitosRepositoryJPA requisitosRepositoryJPA;

    @Autowired
    ImagenesService imagenesService;

    public List<RequisitoDto> obtenerRequisitosDeProcedimiento(int idProcedimiento) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
            .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        List<RequisitoDto> requisitosDto = procedimientosRequisitosRepositoryJPA.findByProcedimiento(procedimientoEntity)
                                          .stream()
                                          .map(procedimientoRequisitoEntity->{
                                            RequisitoDto requisitoDto = new RequisitoDto().convertirRequisitoEntityARequisitoDto(procedimientoRequisitoEntity.getRequisito()); 
                                            requisitoDto.setImagenes(imagenesService.obtenerImagenes("requisitos", requisitoDto.getIdRequisito()));
                                            return requisitoDto;
                                           })
                                          .collect(Collectors.toList());
        return requisitosDto;
    }


    public void eliminarRequisitoProcedimiento(int idProcedimiento, int idRequisito) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findById(idRequisito)
        .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));
        procedimientosRequisitosRepositoryJPA.deleteById(new ProcedimientoRequisitoId(procedimientoEntity.getIdProcedimiento(),requisitoEntity.getIdRequisito()));
    }


    public void crearRequisitoProcedimiento(int idProcedimiento, int idRequisito) {

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        RequisitoEntity requisitoEntity = requisitosRepositoryJPA.findById(idRequisito)
        .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));

        ProcedimientoRequisitoEntity procedimientoRequisitoEntity=new ProcedimientoRequisitoEntity(new ProcedimientoRequisitoId(procedimientoEntity.getIdProcedimiento(),requisitoEntity.getIdRequisito()),procedimientoEntity,requisitoEntity);
        procedimientosRequisitosRepositoryJPA.save(procedimientoRequisitoEntity);

    }
}
