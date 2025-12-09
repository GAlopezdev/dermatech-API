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
@Table(name = "detalle_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrden {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int idDetalle;
    
    @ManyToOne
    @JoinColumn(name = "id_orden")
    private Orden orden;
    
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
    
    @Column(name = "cantidad")
    private int cantidad;
    
    @Column(name = "precio_unitario")
    private double precioUnitario;
    
    @Column(name = "subtotal")
    private double subtotal;
}
