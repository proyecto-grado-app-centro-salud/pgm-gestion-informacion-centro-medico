package com.example.microservicio_informacion_centro_medico.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.microservicio_informacion_centro_medico.model.ProcedimientoElementoEntity;
import com.example.microservicio_informacion_centro_medico.model.ProcedimientoEntity;
import com.example.microservicio_informacion_centro_medico.model.dtos.PasoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoDto;
import com.example.microservicio_informacion_centro_medico.model.dtos.ProcedimientoElementoDto;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoId;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosElementosRepositoryJPA;
import com.example.microservicio_informacion_centro_medico.repository.ProcedimientosRepositoryJPA;

@Service
public class ProcedimientosElementosService {

    @Autowired
    ProcedimientosElementosRepositoryJPA procedimientosElementosRepositoryJPA;

    @Autowired
    ProcedimientosRepositoryJPA procedimientosRepositoryJPA;


    @Autowired
    EspecialidadesService especialidadesService;

    @Autowired
    ImagenesService imagenesService;

    public void crearProcedimientoElemento(int idProcedimiento, int idElemento, String tipoElemento,@RequestParam("data") ProcedimientoElementoDto procedimientoElementoDto) {

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        verificarExisteciaElemento(idElemento,tipoElemento);


        ProcedimientoElementoEntity procedimientoElementoEntity = new ProcedimientoElementoEntity();        
        procedimientoElementoEntity.setIdElemento(idElemento);
        procedimientoElementoEntity.setTipoElemento(tipoElemento);
        procedimientoElementoEntity.setProcedimiento(procedimientoEntity);
        procedimientoElementoEntity.setDescripcion(procedimientoElementoDto.getDescripcion());
        

        procedimientosElementosRepositoryJPA.save(procedimientoElementoEntity);    
    }

    public void eliminarProcedimientoElemento(int idProcedimiento, int idElemento, String tipoElemento) {
        verificarExisteciaElemento(idElemento,tipoElemento);

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findOneByIdElementoAndTipoElementoAndProcedimiento(idElemento,tipoElemento,procedimientoEntity)
        .orElseThrow(() -> new RuntimeException("Procedimiento elemento no encontrado"));
        procedimientosElementosRepositoryJPA.delete(procedimientoElementoEntity);
    }

    public List<ProcedimientoDto> obtenerProcedimientosDeElemento(int idElemento, String tipoElemento) {
        verificarExisteciaElemento(idElemento,tipoElemento);

        List<ProcedimientoDto> pasosDtos = procedimientosElementosRepositoryJPA.findByTipoElementoAndIdElemento(tipoElemento, idElemento)
                                          .stream()
                                          .map(procedimientoElementoEntity->{
                                            ProcedimientoDto procedimientoDto = new ProcedimientoDto().convertirProcedimientoEntityAProcedimientoDto(procedimientoElementoEntity.getProcedimiento()); 
                                            procedimientoDto.setImagenes(imagenesService.obtenerImagenes("procedimientos", procedimientoDto.getIdProcedimiento()+""));
                                            return procedimientoDto;
                                           })
                                          .collect(Collectors.toList());
        return pasosDtos;
    }

    protected void verificarExisteciaElemento(int idElemento, String tipoElemento) {
        switch (tipoElemento) {
            case "especialidades":
                especialidadesService.obtenerEspecialidadPorId(idElemento);
                break;
            case "centro-salud":
                break;
            default:
                throw new RuntimeException("Elemento no encontrado");
        }
    }

    public ProcedimientoDto obtenerProcedimientoDeElemento(int idProcedimiento, int idElemento, String tipoElemento) {
        verificarExisteciaElemento(idElemento,tipoElemento);

        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        ProcedimientoElementoEntity procedimientoElementoEntity = procedimientosElementosRepositoryJPA.findOneByIdElementoAndTipoElementoAndProcedimiento(idElemento,tipoElemento,procedimientoEntity)
        .orElseThrow(() -> new RuntimeException("Elemento no encontrado"));

        return new ProcedimientoDto().convertirProcedimientoEntityAProcedimientoDto(procedimientoElementoEntity.getProcedimiento());
    }

    public List<ProcedimientoElementoDto> obtenerElementosDeProcedimiento(int idProcedimiento) {
        ProcedimientoEntity procedimientoEntity = procedimientosRepositoryJPA.findByIdProcedimientoAndDeletedAtIsNull(idProcedimiento)
        .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));

        List<ProcedimientoElementoDto> procedimientosElementosDtos = procedimientosElementosRepositoryJPA.findAllProcedimientoElementoByIdProcedimiento(idProcedimiento);

        procedimientosElementosDtos = procedimientosElementosDtos
                                          .stream()
                                          .map(procedimientoElementoDto->{
                                            procedimientoElementoDto.setImagenes(imagenesService.obtenerImagenes("procedimientos-elementos", procedimientoElementoDto.getIdProcedimientoElemento()+""));
                                            return procedimientoElementoDto;
                                           }).toList();
        return procedimientosElementosDtos;
    }
    
}
