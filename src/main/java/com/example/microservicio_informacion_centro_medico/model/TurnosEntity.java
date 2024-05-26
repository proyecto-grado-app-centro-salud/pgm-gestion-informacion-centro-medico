package com.example.microservicio_informacion_centro_medico.model;

import java.sql.Time;

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
@Table(name = "turnos")
public class TurnosEntity {
    @Id
    @Column(name = "id_turno")
    private int idTurno;
    @Column(name = "nombre")
    private int nombre;
    @Column(name = "hora_inicio")
    private Time horaInicio;
    @Column(name = "hora_fin")
    private Time horaFin;
}
