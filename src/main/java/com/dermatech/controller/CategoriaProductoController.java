package com.dermatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dermatech.DTO.CategoriaProductoDTO;
import com.dermatech.service.CategoriaProductoService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaProductoController {

	@Autowired
	private CategoriaProductoService _categoriaService;
	
	@GetMapping
    public ResponseEntity<List<CategoriaProductoDTO>> obtenerTodas() {
        return ResponseEntity.ok(_categoriaService.obtenerTodasCategorias());
    }
	
	@GetMapping("/activas")
    public ResponseEntity<List<CategoriaProductoDTO>> obtenerTodasActivas() {
        return ResponseEntity.ok(_categoriaService.obtenerTodasCategoriasActivas());
    }
	
	@PostMapping
    public ResponseEntity<CategoriaProductoDTO> crear(@RequestBody CategoriaProductoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(_categoriaService.crearCategoria(dto));
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<CategoriaProductoDTO> actualizar(@PathVariable Integer id, @RequestBody CategoriaProductoDTO dto) {
        return ResponseEntity.ok(_categoriaService.actualizarCategoria(id, dto));
    }
	
	@PostMapping("cambiar-estado/{id}")
	public ResponseEntity<CategoriaProductoDTO> habilitarYdeshabilitar(@PathVariable Integer id) {
        return ResponseEntity.ok(_categoriaService.cambiarEstado(id));
    }
}
