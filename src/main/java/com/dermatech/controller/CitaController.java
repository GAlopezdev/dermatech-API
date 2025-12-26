package com.dermatech.controller;


import com.dermatech.DTO.CitaCreateDTO;
import com.dermatech.DTO.CitaDTO;
import com.dermatech.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:4200") 
public class CitaController {

    @Autowired
    private CitaService _citaService;

    @GetMapping
    public ResponseEntity<List<CitaDTO>> obtenerTodas() {
        return ResponseEntity.ok(_citaService.obtenerTodas());
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<CitaDTO>> listarPorPaciente(@PathVariable int id) {
        return ResponseEntity.ok(_citaService.listarPorPaciente(id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CitaCreateDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(_citaService.crearCita(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

 // Cambia esto en tu CitaController.java
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable int id) {
        try {
            return ResponseEntity.ok(_citaService.cancelarCita(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}