package com.dermatech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dermatech.DTO.Usuario.UsuarioCreateDTO;
import com.dermatech.DTO.Usuario.UsuarioDTO;
import com.dermatech.DTO.Usuario.UsuarioUpdateDTO;
import com.dermatech.model.Rol;
import com.dermatech.model.Usuario;
import com.dermatech.repository.IUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

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

    @Transactional
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {

        // Validaciones
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (usuarioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasenia(dto.getContrasenia()); // RECORDAR ENCRIPTAR
        usuario.setTelefono(dto.getTelefono());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setActivo(true);

        // Asignar rol
        Rol rol = new Rol();
        rol.setIdRol(dto.getIdRol() != null ? dto.getIdRol() : 3);
        usuario.setRol(rol);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(Integer id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();

        // Validar email si cambió
        if (!usuario.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // Validar DNI si cambió
        if (!usuario.getDni().equals(dto.getDni()) &&
                usuarioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasenia(dto.getContrasenia());
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

}
