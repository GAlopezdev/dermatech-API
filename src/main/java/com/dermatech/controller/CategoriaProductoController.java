package com.dermatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
