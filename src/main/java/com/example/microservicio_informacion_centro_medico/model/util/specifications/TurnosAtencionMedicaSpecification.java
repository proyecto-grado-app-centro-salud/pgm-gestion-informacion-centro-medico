package com.example.microservicio_informacion_centro_medico.model.util.specifications;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.example.microservicio_informacion_centro_medico.model.ConsultorioEntity;
import com.example.microservicio_informacion_centro_medico.model.EspecialidadEntity;
import com.example.microservicio_informacion_centro_medico.model.MedicoEntity;
import com.example.microservicio_informacion_centro_medico.model.TurnosAtencionMedicaEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;
import com.example.microservicio_informacion_centro_medico.services.TurnosAtencionMedicaService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TurnosAtencionMedicaSpecification {
    static Logger logger = LoggerFactory.getLogger(TurnosAtencionMedicaSpecification.class);
    public static Specification<TurnosAtencionMedicaEntity> greatherEqualThanFechaInicio(String min) {
        LocalDate minDate;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            minDate = LocalDate.parse(min, formato);
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), minDate);
        } catch (Exception e) {
            throw new RuntimeException("Fecha inválida");
        }
    }
    public static Specification<TurnosAtencionMedicaEntity> lessEqualThanFechaFin(String max) {
        LocalDate maxDate;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            maxDate = LocalDate.parse(max, formato);
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
    // public static Specification<UsuarioEntity> obtenerMedicosDeEspecialidadEnTurnosAtencionMedica(
    //     int idEspecialidad, String min, String max) {
    // return (Root<TurnosAtencionMedicaEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
    //     Date minDate;
    //     Date maxDate;
    //     SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    //     try {
    //         // Parsear las fechas
    //         minDate = formato.parse(min);
    //         maxDate = formato.parse(max);
    public static Specification<TurnosAtencionMedicaEntity> deletedAtIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

            
    //         // Unir entidades relacionadas
    //         Join<TurnosAtencionMedicaEntity, MedicoEntity> medicoJoin = root.join("medico");  // MedicoEntity relacionado con TurnosAtencionMedicaEntity
    //         Join<TurnosAtencionMedicaEntity, ConsultorioEntity> consultorioJoin = root.join("consultorio");  // Consultorio relacionado con TurnosAtencionMedicaEntity
    //         Join<ConsultorioEntity, EspecialidadEntity> especialidadJoin = consultorioJoin.join("especialidad");  // Especialidad relacionada con Consultorio

    //         // Filtrar por especialidad
    //         Predicate especialidadPredicate = builder.equal(especialidadJoin.get("idEspecialidad"), idEspecialidad);

    //         // Filtrar por rango de fechas
    //         Predicate fechaPredicate = builder.between(root.get("fecha"), minDate, maxDate);

    //         // Asegurar que los resultados no se repitan
    //         query.distinct(true);

    //         // Seleccionar los atributos de UsuarioEntity asociados a MedicoEntity
    //         // Aquí asumimos que MedicoEntity tiene una relación con UsuarioEntity
    //         query.select(medicoJoin.get("usuario"));  // Asegúrate de que MedicoEntity tenga el atributo 'usuario'

    //         // Devolver la combinación de predicados
    //         return builder.and(especialidadPredicate, fechaPredicate);
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error al procesar las fechas o la consulta: " + e.getMessage());
    //     }
    // };
//}
}
