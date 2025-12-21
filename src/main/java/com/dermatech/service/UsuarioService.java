package com.dermatech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dermatech.DTO.Usuario.UsuarioCreateDTO;
import com.dermatech.DTO.Usuario.UsuarioDTO;
import com.dermatech.DTO.Usuario.UsuarioMapperDTO;
import com.dermatech.DTO.Usuario.UsuarioUpdateDTO;
import com.dermatech.model.Rol;
import com.dermatech.model.Usuario;
import com.dermatech.repository.IRolesRepository;
import com.dermatech.repository.IUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioMapperDTO mapper;

    @Autowired 
    private PasswordEncoder passwordEncoder; 

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public UsuarioDTO obtenerUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        return convertirADTO(usuario);
    }

    @Autowired
    private IRolesRepository rolRepository;

    @Transactional
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {
        System.out.println("=== CREAR USUARIO ===");
        System.out.println("DTO recibido: " + dto);
        
        // Validaciones básicas
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (usuarioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        // Determinar el rol
        Integer idRol = dto.getIdRol() != null ? dto.getIdRol() : 1; // Por defecto PACIENTE (1)
        
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol con ID " + idRol + " no encontrado"));
        
        System.out.println("Rol asignado: " + rol.getIdRol() + " - " + rol.getNombreRol());

        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        usuario.setTelefono(dto.getTelefono());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setActivo(true);
        usuario.setRol(rol);

        System.out.println("Guardando usuario con rol: " + usuario.getRol().getNombreRol());
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return convertirADTO(usuarioGuardado);
    }    
    @Transactional
    public UsuarioDTO actualizarUsuario(Integer id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();

        
//        // Validar email si cambió
//        if (!usuario.getEmail().equals(dto.getEmail()) &&
//                usuarioRepository.existsByEmail(dto.getEmail())) {
//            throw new IllegalArgumentException("El email ya está registrado");
//        }
//
//        // Validar DNI si cambió
//        if (!usuario.getDni().equals(dto.getDni()) &&
//                usuarioRepository.existsByDni(dto.getDni())) {
//            throw new IllegalArgumentException("El DNI ya está registrado");
//        }

        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        usuario.setTelefono(dto.getTelefono());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setActivo(dto.getActivo());

        // Cambiar rol si se envía
        if (dto.getIdRol() != null) {
            Rol rol = new Rol();
            rol.setIdRol(dto.getIdRol());
            usuario.setRol(rol);
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioActualizado);
    }

    @Transactional
    public UsuarioDTO cambiarEstado(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setActivo(!usuario.getActivo());
        usuarioRepository.save(usuario);
        return convertirADTO(usuario);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setEmail(usuario.getEmail());
        dto.setContrasenia(usuario.getContrasenia());
        dto.setTelefono(usuario.getTelefono());
        dto.setDni(usuario.getDni());
        dto.setDireccion(usuario.getDireccion());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setActivo(usuario.getActivo());

        return dto;
    }
    

	public Usuario login(String email, String contrasenia) {
		Usuario encontrado = usuarioRepository.findByEmail(email)
					.orElseThrow(()->new RuntimeException("Usuario no encontrado"));
		
		if (!passwordEncoder.matches(contrasenia, encontrado.getContrasenia())) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		
		return encontrado;
	}
    public List<UsuarioDTO> obtenerTodosLosUsuariosList() {
        return usuarioRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
    public Usuario registrarUsuario(Usuario usuario) { 
        // codifica la contraseña antes de guardarla 
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        usuario.setActivo(true);
        return usuarioRepository.save(usuario); 
    }


}
