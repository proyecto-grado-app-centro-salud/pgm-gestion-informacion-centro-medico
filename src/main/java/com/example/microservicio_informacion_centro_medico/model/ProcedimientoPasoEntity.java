package com.example.microservicio_informacion_centro_medico.model;

import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoPasoId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "procedimiento_paso")
public class ProcedimientoPasoEntity {
    @EmbeddedId
    private ProcedimientoPasoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_procedimiento", nullable = false, insertable = false, updatable = false)
    private ProcedimientoEntity procedimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paso", nullable = false, insertable = false, updatable = false)
    private PasoEntity paso;
}
