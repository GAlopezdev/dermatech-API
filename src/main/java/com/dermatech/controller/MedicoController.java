package com.dermatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dermatech.DTO.Medico.MedicoCreateDTO;
import com.dermatech.DTO.Medico.MedicoDTO;
import com.dermatech.DTO.Medico.MedicoUpdateDTO;
import com.dermatech.service.MedicoService;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
	
	@Autowired
    private MedicoService _medicoService;

	@GetMapping
    public ResponseEntity<List<MedicoDTO>> obtenerTodos() {
        return ResponseEntity.ok(_medicoService.obtenerTodosLosMedicos());
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<MedicoDTO>> obtenerActivos() {
        return ResponseEntity.ok(_medicoService.obtenerMedicosActivos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(_medicoService.obtenerMedicoPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<MedicoDTO> crear(@RequestBody MedicoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(_medicoService.crearMedico(dto));
    }
	
    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> actualizar(@PathVariable Integer id, @RequestBody MedicoUpdateDTO dto) {
        return ResponseEntity.ok(_medicoService.actualizar(id, dto));
    }
    
    @PostMapping("cambiar-estado/{id}")
    public ResponseEntity<MedicoDTO> habilitarYdeshabilitar(@PathVariable Integer id) {
        return ResponseEntity.ok(_medicoService.cambiarEstado(id));
    }
}
