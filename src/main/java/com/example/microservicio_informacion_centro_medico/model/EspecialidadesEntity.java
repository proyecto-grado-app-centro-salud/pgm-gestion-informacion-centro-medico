package com.example.microservicio_informacion_centro_medico.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "especialidades")
public class EspecialidadesEntity {
    @Id
    @Column(name = "id_especialidad")
    private int idEspecialidad;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Column(name = "requisitos_solicitud_ficha_medica")
    private Date requisitosSolicitudFichaMedica;
    @Column(name = "requisitos_minimos_atencion_consulta_externa")
    private Date requisitosMinimosAtencionConsultaExterna;
    @Column(name = "procedimiento_obtencion_ficha_medica_presencial")
    private Date procedimientoObtencionFichaMedicaPresencial;
    
}
