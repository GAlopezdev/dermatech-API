package com.dermatech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dermatech.DTO.ProductoDTO;
import com.dermatech.model.CategoriaProducto;
import com.dermatech.model.Producto;
import com.dermatech.repository.IProductoRepository;

import jakarta.transaction.Transactional;

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
	
	public ProductoDTO obtenerProducto(int id) {
		Producto producto = productoRepository.findById(id).orElseThrow();
		return convertirADTO(producto);
	}
	
	@Transactional
    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = convertirAEntidad(dto);
        producto.setActivo(true);
        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }
	
	@Transactional
	public ProductoDTO actualizar(int id, ProductoDTO dto){

		Producto producto = productoRepository.findById(id).orElseThrow();
		
		producto.setNombreProducto(dto.getNombreProducto());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());
        
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setIdCategoria(dto.getIdCategoria());
        producto.setCategoria(categoria);
        
        Producto actualizado = productoRepository.save(producto);
		
		return convertirADTO(actualizado);
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
	
	private Producto convertirAEntidad(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setSku(dto.getSku());
        
        CategoriaProducto c = new CategoriaProducto();
        c.setIdCategoria(dto.getIdCategoria());
        producto.setCategoria(c);
        
        return producto;
    }
}
