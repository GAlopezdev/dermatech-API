package com.dermatech.DTO.Medico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoUpdateDTO {
	private String nombreCompleto;
    private String email;
    private String telefono;
    private String direccion;
    private Integer idEspecialidad;
    private String numeroColegiatura;
    private Double tarifaConsulta;
    private Integer aniosExperiencia;
}
