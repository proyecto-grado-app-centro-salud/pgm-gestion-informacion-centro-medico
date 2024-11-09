package com.example.microservicio_informacion_centro_medico.model.util.specifications;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.services.TurnosAtencionMedicaService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TurnosAtencionMedicaSpecification {
    static Logger logger = LoggerFactory.getLogger(TurnosAtencionMedicaSpecification.class);
    public static Specification<TurnosAtencionMedicaEntity> greatherEqualThanFechaInicio(String min) {
        Date minDate;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            minDate = formato.parse(min);
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), minDate);
        } catch (Exception e) {
            throw new RuntimeException("Fecha inválida");
        }
    }
    public static Specification<TurnosAtencionMedicaEntity> lessEqualThanFechaFin(String max) {
        Date maxDate;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            maxDate = formato.parse(max);
            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), maxDate);
        } catch (Exception e) {
            throw new RuntimeException("Fecha inválida");
        }
    }
    public static Specification<TurnosAtencionMedicaEntity> hasEspecialidadId(int idEspecialidad) {
        return (root,query,builder) -> {
            Join<TurnosAtencionMedicaEntity, ConsultorioEntity> consultorioJoin = root.join("consultorio");
            Join<ConsultorioEntity, EspecialidadEntity> especialidadJoin = consultorioJoin.join("especialidad");
            Predicate predicate = builder.equal(especialidadJoin.get("idEspecialidad"), idEspecialidad);

            return predicate;
        };
    }
}
