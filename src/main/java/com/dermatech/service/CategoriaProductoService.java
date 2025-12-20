package com.dermatech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dermatech.DTO.CategoriaProductoDTO;
import com.dermatech.model.CategoriaProducto;
import com.dermatech.repository.ICategoriaProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaProductoService {

	@Autowired
	private ICategoriaProductoRepository categoriaProductoRepository;
	
	public List<CategoriaProductoDTO> obtenerTodasCategorias() {
        return categoriaProductoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .toList();
    }
	
	public List<CategoriaProductoDTO> obtenerTodasCategoriasActivas() {
        return categoriaProductoRepository.findByActivoTrue()
            .stream()
            .map(this::convertirADTO)
            .toList();
    }
	
	@Transactional
    public CategoriaProductoDTO crearCategoria(CategoriaProductoDTO dto) {
        if (categoriaProductoRepository.existsByNombreCategoria(dto.getNombreCategoria())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }
        
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombreCategoria(dto.getNombreCategoria());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setActivo(true);
        
        CategoriaProducto guardada = categoriaProductoRepository.save(categoria);
        return convertirADTO(guardada);
    }
	
	@Transactional
    public CategoriaProductoDTO actualizarCategoria(Integer id, CategoriaProductoDTO dto) {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id).orElseThrow();
        
        // Verificar si el nuevo nombre ya existe
        if (!categoria.getNombreCategoria().equalsIgnoreCase(dto.getNombreCategoria()) &&
        		categoriaProductoRepository.existsByNombreCategoria(dto.getNombreCategoria())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }
        
        categoria.setNombreCategoria(dto.getNombreCategoria());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setActivo(dto.getActivo());
        
        CategoriaProducto actualizada = categoriaProductoRepository.save(categoria);
        return convertirADTO(actualizada);
    }
	
	@Transactional
	public CategoriaProductoDTO cambiarEstado(Integer id) {
		CategoriaProducto categoria = categoriaProductoRepository.findById(id).orElseThrow();
		
		categoria.setActivo(!categoria.getActivo());
		
		CategoriaProducto actualizada = categoriaProductoRepository.save(categoria);
        return convertirADTO(actualizada);
	}
	
	private CategoriaProductoDTO convertirADTO(CategoriaProducto categoria) {
        CategoriaProductoDTO dto = new CategoriaProductoDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setActivo(categoria.getActivo());
        return dto;
    }
}
