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
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private int idOrden;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @Column(name = "numero_orden")
    private String numeroOrden;
    
    @Column(name = "subtotal")
    private double subtotal;
    
    @Column(name = "descuento")
    private double descuento;
    
    @Column(name = "total")
    private double total;
    
    @ManyToOne
    @JoinColumn(name = "id_estado_orden")
    private EstadoOrden estadoOrden;
    
    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;
    
    @Column(name = "direccion_envio")
    private String direccionEnvio;
    
    @Column(name = "fecha_orden")
    private String fechaOrden;
    
    @Column(name = "fecha_actualizacion")
    private String fechaActualizacion;
    
}
