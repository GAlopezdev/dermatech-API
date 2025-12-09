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
@Table(name = "categoria_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProducto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private int idCategoria;
    
    @Column(name = "nombre_categoria")
    private String nombreCategoria;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "activo")
    private Boolean activo;
    
}
