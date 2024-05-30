package com.example.microservicio_informacion_centro_medico.model;

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
@Table(name = "consultorios")
public class ConsultoriosEntity {
    @Id
    @Column(name = "id_consultorio")
    private int idConsultorio;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "equipamiento")
    private String equipamiento;
    @Column(name = "id_especialidad")
    private int idEspecialidad;
}
