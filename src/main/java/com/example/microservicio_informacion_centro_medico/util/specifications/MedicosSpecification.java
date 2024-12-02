package com.example.microservicio_informacion_centro_medico.util.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.MedicoEntity;
import com.example.microservicio_informacion_centro_medico.model.RolEntity;
import com.example.microservicio_informacion_centro_medico.model.RolUsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class MedicosSpecification {
    public static Specification<RolUsuarioEntity> obtenerMedicos(String nombreUsuario) {
        try {
            return (root,query,builder) -> {
                Join<RolUsuarioEntity,UsuarioEntity> usuarioJoin = root.join("usuario");
                Join<RolUsuarioEntity,RolEntity> rolJoin = root.join("rol");

                Predicate predicadoFinal = builder.equal(rolJoin.get("idRol"), 2);
                predicadoFinal = builder.and(predicadoFinal,builder.isNull(root.get("deletedAt")));
                if(nombreUsuario!=null){
                    Predicate predicadoNombres = builder.or(
                        builder.like(builder.lower(usuarioJoin.get("nombres")), "%" + nombreUsuario.toLowerCase() + "%"),
                        builder.like(builder.lower(usuarioJoin.get("apellidoPaterno")), "%" + nombreUsuario.toLowerCase() + "%"),
                        builder.like(builder.lower(usuarioJoin.get("apellidoMaterno")), "%" + nombreUsuario.toLowerCase() + "%")
                    );
                    predicadoFinal = builder.and(predicadoFinal, predicadoNombres);
                }
                return predicadoFinal;
            };
        } catch (Exception e) {
            throw new RuntimeException("Error obtener consultas medicas por parametros");
        }
        
    }
}
