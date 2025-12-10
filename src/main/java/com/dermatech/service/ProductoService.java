package com.dermatech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dermatech.DTO.ProductoDTO;
import com.dermatech.model.Producto;
import com.dermatech.repository.IProductoRepository;
import java.util.stream.Collectors;
@Service
public class ProductoService {

	@Autowired
	private IProductoRepository productoRepository;
	
	public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findByActivoTrue()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
	
	
	
	private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setSku(producto.getSku());
        dto.setActivo(producto.getActivo());
        dto.setIdCategoria(producto.getCategoria().getIdCategoria());
        dto.setNombreCategoria(producto.getCategoria().getNombreCategoria());
        return dto;
    }
}
