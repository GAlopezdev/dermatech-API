package com.dermatech.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dermatech.DTO.LoginDTO;
import com.dermatech.DTO.Usuario.UsuarioCreateDTO;
import com.dermatech.DTO.Usuario.UsuarioDTO;
import com.dermatech.DTO.Usuario.UsuarioUpdateDTO;
import com.dermatech.model.Usuario;
import com.dermatech.security.JwtUtil;
import com.dermatech.service.UsuarioService;

@CrossOrigin(origins = "http://localhost:4200")
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
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
    	try {
    	    Usuario encontrado = usuarioService.login(login.getEmail(), login.getContrasenia());
			
			String rol = encontrado.getRol().getNombreRol();
			String token = jwtUtil.generarToken(encontrado.getEmail(),rol);
			
			return ResponseEntity.ok(Map.of(
					"token",token,
					"usuario",encontrado.getEmail(),
					"rol",rol			
					));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioCreateDTO dto) {
        try {            
            UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(dto);
            
            System.out.println("Usuario registrado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
                    
        } catch (Exception e) {
            System.err.println("ERROR AL REGISTRAR USUARIO:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }
}
