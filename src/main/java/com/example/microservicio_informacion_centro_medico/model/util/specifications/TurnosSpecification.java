package com.example.microservicio_informacion_centro_medico.model.util.specifications;
import com.example.microservicio_informacion_centro_medico.model.TurnoEntity;
import java.time.LocalTime;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Order;

public class TurnosSpecification {
    public static Specification<TurnoEntity> obtenerTurnosPorParametros(String horaInicio, String horaFin) {
        try {
            return (root, query, criteriaBuilder) -> {
                Predicate predicateRango,predicateRetorno;
                predicateRetorno = criteriaBuilder.conjunction();
                if(horaInicio != null && horaFin != null){  
                    LocalTime inicio = LocalTime.parse(horaInicio);
                    LocalTime fin = LocalTime.parse(horaFin);
                    predicateRango = criteriaBuilder.between(root.get("horaInicio"), inicio, fin);
                    predicateRetorno=criteriaBuilder.and(predicateRetorno, predicateRango);
                }
                    Order order = criteriaBuilder.asc(root.get("horaInicio"));                
                    query.orderBy(order);
                    return predicateRetorno;
                };
        } catch (Exception e) {
            throw new RuntimeException("Hora inválida");
        }
    }
}
