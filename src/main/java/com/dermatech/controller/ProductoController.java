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

import com.dermatech.DTO.ProductoDTO;
import com.dermatech.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService _productoService;
	
	@GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(_productoService.obtenerTodos());
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(_productoService.obtenerProducto(id));
    }
	
	@PostMapping("/guardar")
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(_productoService.crear(productoDTO));
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Integer id, @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(_productoService.actualizar(id, productoDTO));
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        _productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
