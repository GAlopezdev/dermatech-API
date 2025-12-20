package com.dermatech.DTO.Usuario;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String nombreCompleto;
    private String email;
    private String contrasenia;
    private String telefono;
    private String dni;
    private String direccion;
    private String fechaNacimiento;
    private Boolean activo;
    private Integer idRol;

}
