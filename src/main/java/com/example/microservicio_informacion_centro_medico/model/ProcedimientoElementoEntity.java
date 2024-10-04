package com.example.microservicio_informacion_centro_medico.model;

import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoId;
import com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos.ProcedimientoElementoPasoId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "procedimientos_elementos")
public class ProcedimientoElementoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_procedimiento_elemento")
    private int idProcedimientoElemento;

    @Column(name = "id_elemento")
    private int idElemento;

    @Column(name = "tipo_elemento")
    private String tipoElemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_procedimiento", nullable = false)
    private ProcedimientoEntity procedimiento;

    @Column(name = "descripcion")
    private String descripcion;

}
