package com.example.microservicio_informacion_centro_medico.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "turnos_atencion_medica")
public class HorariosAtencionMedicaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno_atencion_medica")
    private int idTurnoAtencionMedica;
    @Column(name = "numero_fichas_disponible")
    private int numeroFichasDisponible;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "id_consultorio")
    private int idConsultorio;
    @Column(name = "id_turno")
    private int idTurno;
    @Column(name = "id_medico")
    private int idMedico;
}
