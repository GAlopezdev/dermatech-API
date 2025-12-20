package com.dermatech.DTO.Medico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoCreateDTO {
	// Datos del usuario
    private String nombreCompleto;
    private String email;
    private String contrasenia;
    private String telefono;
    private String dni;
    private String direccion;
    private String fechaNacimiento;
    
    // Datos del médico
    private Integer idEspecialidad;
    private String numeroColegiatura;
    private Double tarifaConsulta;
    private Integer aniosExperiencia;
}
