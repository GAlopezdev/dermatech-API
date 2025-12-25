package com.dermatech.DTO.Login;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;     
    private Integer idUsuario;
    private String nombreCompleto;
    private String email;
    private Integer rol;           
}
