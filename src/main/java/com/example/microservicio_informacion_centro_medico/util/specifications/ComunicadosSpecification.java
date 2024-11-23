package com.example.microservicio_informacion_centro_medico.util.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.microservicio_informacion_centro_medico.model.ComunicadoEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;

import jakarta.persistence.criteria.Predicate;

public class ComunicadosSpecification {
    public static Specification<ComunicadoEntity> obtenerComunicadosPorParametros(String tituloComunicado) {
        try {
            return (root,query,builder) -> {
                Predicate predicadoFinal =builder.isNull(root.get("deletedAt"));
                if(tituloComunicado!=null){
                    predicadoFinal = builder.and(predicadoFinal, builder.like(root.get("titulo"), "%"+tituloComunicado+"%"));
                }
                query.orderBy(builder.desc(root.get("updatedAt")));
                return predicadoFinal;
            };
        } catch (Exception e) {
            throw new RuntimeException("Error obtener consultas medicas por parametros");
        }
    }
}
