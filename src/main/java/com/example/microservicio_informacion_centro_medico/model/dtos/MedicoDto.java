package com.example.microservicio_informacion_centro_medico.model.dtos;
import com.example.microservicio_informacion_centro_medico.model.RolUsuarioEntity;
import com.example.microservicio_informacion_centro_medico.model.UsuarioEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class MedicoDto {
    private String idUsuario;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String ci;

    private String direccion;

    private String celular;

    private String email;

    private Integer aniosExperiencia;

    private Float salario;

    private String foto;

    private String descripcion;

    private String grupoSanguineo;

    private String password;

    private List<ImagenDto> imagenes;

    public MedicoDto convertirUsuarioEntityAMedicoDto(UsuarioEntity usuarioEntity) {
        this.idUsuario=usuarioEntity.getIdUsuario();
        this.nombres=usuarioEntity.getNombres();
        this.apellidoPaterno=usuarioEntity.getApellidoPaterno();
        this.apellidoMaterno=usuarioEntity.getApellidoMaterno();
        this.ci=usuarioEntity.getCi();
        this.direccion=usuarioEntity.getDireccion();
        this.celular=usuarioEntity.getCelular();
        this.email=usuarioEntity.getEmail();
        return this;
    }
    public MedicoDto convertirRolUsuarioEntityAMedicoDto(RolUsuarioEntity rolUsuarioEntity) {
        this.idUsuario=rolUsuarioEntity.getUsuario().getIdUsuario();
        this.nombres=rolUsuarioEntity.getUsuario().getNombres();
        this.apellidoPaterno=rolUsuarioEntity.getUsuario().getApellidoPaterno();
        this.apellidoMaterno=rolUsuarioEntity.getUsuario().getApellidoMaterno();
        this.ci=rolUsuarioEntity.getUsuario().getCi();
        this.direccion=rolUsuarioEntity.getUsuario().getDireccion();
        this.celular=rolUsuarioEntity.getUsuario().getCelular();
        this.email=rolUsuarioEntity.getUsuario().getEmail();
        return this;
    }
}
