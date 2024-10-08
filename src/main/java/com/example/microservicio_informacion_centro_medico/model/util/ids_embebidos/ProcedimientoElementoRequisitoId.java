package com.example.microservicio_informacion_centro_medico.model.util.ids_embebidos;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProcedimientoElementoRequisitoId implements Serializable{
    private Integer id_procedimiento;
    private Integer id_elemento;
    private String tipo_elemento;
    private Integer id_requisito;
}
