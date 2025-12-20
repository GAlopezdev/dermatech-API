package com.dermatech.DTO.Medico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoDTO {
	private Integer idMedico;
    private Integer idUsuario;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String dni;
    private Integer idEspecialidad;
    private String nombreEspecialidad;
    private String numeroColegiatura;
    private Double tarifaConsulta;
    private Integer aniosExperiencia;
    private Boolean activo;
}
