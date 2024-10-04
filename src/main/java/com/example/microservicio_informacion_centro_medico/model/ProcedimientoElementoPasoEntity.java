package com.example.microservicio_informacion_centro_medico.model;

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
import jakarta.persistence.JoinColumns;
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
@Table(name = "procedimientos_elementos_pasos")
public class ProcedimientoElementoPasoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_procedimiento_elemento_paso")
    private int idProcedimientoElementoPaso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_procedimiento_elemento", nullable = false)
    private ProcedimientoElementoEntity procedimientoElemento;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paso", nullable = false)
    private PasoEntity paso;
}
