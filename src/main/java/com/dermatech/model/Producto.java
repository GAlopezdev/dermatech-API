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
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private int idProducto;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaProducto categoria;
    
    @Column(name = "nombre_producto")
    private String nombreProducto;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "precio")
    private double precio;
    
    @Column(name = "stock")
    private Integer stock = 0;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @Column(name = "sku")
    private String sku;
    
    @Column(name = "activo")
    private Boolean activo;
    
    @Column(name = "fecha_creacion")
    private String fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private String fechaActualizacion;

}
