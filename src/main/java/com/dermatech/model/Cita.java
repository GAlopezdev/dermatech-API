package com.dermatech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private int idCita;

    @Column(name = "fecha_hora")
    private String fechaHora;
    
    @Column(name = "motivo_consulta")
    private String motivoConsulta;
    
    @Column(name = "diagnostico")
    private String diagnostico;
    
    @Column(name = "tratamiento")
    private String tratamiento;
    
    @Column(name = "observaciones")
    private String observaciones;
    
    @Column(name = "precio")
    private double precio;
    
    @Column(name = "fecha_creacion")
    private String fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private String fechaActualizacion;
    
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Usuario paciente;
    
    @ManyToOne
    @JoinColumn(name = "id_estado_cita")
    private EstadoCita estadoCita;
    
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;
    
}
