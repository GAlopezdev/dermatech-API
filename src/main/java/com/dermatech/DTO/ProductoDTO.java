package com.dermatech.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

	private Integer idProducto;
    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagenUrl;
    private String sku;
    private Boolean activo;
    private Integer idCategoria;
    private String nombreCategoria;
    
}
