package com.dermatech.DTO;


import lombok.Data;

@Data
public class CitaCreateDTO {
    private String fechaHora; // "YYYY-MM-DDTHH:mm"
    private String motivoConsulta;
    private int idPaciente;
    private int idMedico;
    private int idEstadoCita;
    private Double precio; // Opcional, si viene nulo usamos la tarifa del médico
}