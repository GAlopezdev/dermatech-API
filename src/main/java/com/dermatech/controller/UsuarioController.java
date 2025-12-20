package com.dermatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dermatech.DTO.Usuario.UsuarioCreateDTO;
import com.dermatech.DTO.Usuario.UsuarioDTO;
import com.dermatech.DTO.Usuario.UsuarioUpdateDTO;
import com.dermatech.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable int id) {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioCreateDTO dto) {
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(dto);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable int id,
            @RequestBody UsuarioUpdateDTO dto) {
        UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @PatchMapping("/estado/{id}")
    public ResponseEntity<UsuarioDTO> cambiarEstado(@PathVariable int id) {
        UsuarioDTO usuario = usuarioService.cambiarEstado(id);
        return ResponseEntity.ok(usuario);
    }
}
