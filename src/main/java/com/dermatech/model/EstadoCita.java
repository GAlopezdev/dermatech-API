package com.dermatech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado_cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCita {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_cita")
    private int idEstadoCita;
    
    @Column(name = "nombre_estado")
    private String nombreEstado;
}
