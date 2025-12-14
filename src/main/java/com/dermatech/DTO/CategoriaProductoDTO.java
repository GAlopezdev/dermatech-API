package com.dermatech.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProductoDTO {
	
	private int idCategoria;
    private String nombreCategoria;
    private String descripcion;
    private Boolean activo;
    
}
