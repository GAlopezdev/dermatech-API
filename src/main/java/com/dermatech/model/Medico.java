package com.dermatech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private int idMedico;    
    
    @Column(name = "numero_colegiatura")
    private String numeroColegiatura;
    
    @Column(name = "tarifa_consulta")
    private double tarifaConsulta;
    
    @Column(name = "anios_experiencia")
    private int aniosExperiencia;
    
    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;
    
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
}
