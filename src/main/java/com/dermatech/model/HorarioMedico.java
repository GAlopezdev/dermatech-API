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
@Table(name = "horario_medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioMedico {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private int idHorario;
    
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;
    
    @Column(name = "dia_semana")
    private int diaSemana;
    
    @Column(name = "hora_inicio")
    private String horaInicio;
    
    @Column(name = "hora_fin")
    private String horaFin;
    
    @Column(name = "activo")
    private Boolean activo;
}
