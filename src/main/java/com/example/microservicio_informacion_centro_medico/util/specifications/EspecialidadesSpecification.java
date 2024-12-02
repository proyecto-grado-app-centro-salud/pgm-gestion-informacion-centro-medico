package com.example.microservicio_informacion_centro_medico.util.specifications;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.jpa.domain.Specification;

import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class EspecialidadesSpecification {
    public static Specification<EspecialidadEntity> obtenerEspecialidadesPorParametros(String nombreEspecialidad) {
        try {
            return (root,query,builder) -> {
                Predicate predicadoFinal =builder.isNull(root.get("deletedAt"));
                if(nombreEspecialidad!=null){
                    predicadoFinal = builder.and(predicadoFinal,builder.like(root.get("nombre"), "%"+nombreEspecialidad+"%"));
                }
                return predicadoFinal;
            };
        } catch (Exception e) {
            throw new RuntimeException("Error obtener consultas medicas por parametros");
        }
        
    }
}
