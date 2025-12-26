package com.dermatech.service;

import java.time.LocalDateTime;
import java.util.List;

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
    private IRolesRepository rolRepository;

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
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Transactional
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {
        // 1. Validaciones de existencia
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (usuarioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        // 2. Determinar el rol (Por defecto 1 - PACIENTE si viene nulo)
        Integer idRol = (dto.getIdRol() != null) ? dto.getIdRol() : 1;
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // 3. Mapeo manual a la Entidad
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        
        // ENCRIPTACIÓN: Vital para que el login funcione
        usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        
        usuario.setTelefono(dto.getTelefono());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setRol(rol);
        
        // ESTADO Y FECHAS: Soluciona el error de "Column cannot be null"
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now().toString()); 

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(Integer id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        
        // Solo actualizamos contraseña si se envía una nueva
        if (dto.getContrasenia() != null && !dto.getContrasenia().isBlank()) {
            usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        }
        
        usuario.setTelefono(dto.getTelefono());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setActivo(dto.getActivo());

        if (dto.getIdRol() != null) {
            Rol rol = rolRepository.findById(dto.getIdRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(rol);
        }

        return convertirADTO(usuarioRepository.save(usuario));
    }

    public Usuario login(String email, String contrasenia) {
        Usuario encontrado = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Verifica la contraseña encriptada
        if (!passwordEncoder.matches(contrasenia, encontrado.getContrasenia())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        
        return encontrado;
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
        // Importante: No olvides setear el Rol en el DTO si tu DTO lo requiere
        return dto;
    }

    @Transactional
    public UsuarioDTO cambiarEstado(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setActivo(!usuario.getActivo());
        return convertirADTO(usuarioRepository.save(usuario));
    }
}