package com.example.microservicio_informacion_centro_medico.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimientoElementoDto {
    Integer idProcedimiento;
    String tipoElemento;
    Integer idElemento;
    String descripcion;
}
