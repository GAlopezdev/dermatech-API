package com.dermatech.DTO;

import lombok.Data;
@Data
public class CitaDTO {
    private int idCita;
    private String fechaHora;
    private String motivoConsulta;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private double precio;
    
    private int idPaciente;
    private String nombrePaciente;
    private int idMedico;
    private String nombreMedico;
    private int idEstadoCita;
    private String nombreEstado;
}