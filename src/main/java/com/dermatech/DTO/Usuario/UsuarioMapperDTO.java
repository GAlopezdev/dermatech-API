package com.dermatech.DTO.Usuario;

import org.springframework.stereotype.Component;

import com.dermatech.model.Usuario;

@Component
public class UsuarioMapperDTO {

	public UsuarioDTO toDTO(Usuario usuario) {
	    if (usuario == null) return null;
	
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
