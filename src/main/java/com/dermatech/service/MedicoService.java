package com.dermatech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dermatech.DTO.Medico.MedicoCreateDTO;
import com.dermatech.DTO.Medico.MedicoDTO;
import com.dermatech.DTO.Medico.MedicoUpdateDTO;
import com.dermatech.model.Especialidad;
import com.dermatech.model.Medico;
import com.dermatech.model.Rol;
import com.dermatech.model.Usuario;
import com.dermatech.repository.IEspecialidadRepository;
import com.dermatech.repository.IMedicoRepository;
import com.dermatech.repository.IUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicoService {

	@Autowired
	private IMedicoRepository medicoRepository;
	@Autowired
    private IUsuarioRepository usuarioRepository;
	@Autowired
    private IEspecialidadRepository especialidadRepository;
	
	public List<MedicoDTO> obtenerTodosLosMedicos() {
        return medicoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .toList();
    }
	
	public List<MedicoDTO> obtenerMedicosActivos() {
        return medicoRepository.findAllActivos()
            .stream()
            .map(this::convertirADTO)
            .toList();
    }
	
	public MedicoDTO obtenerMedicoPorId(Integer id) {
        Medico medico = medicoRepository.findById(id).orElseThrow();
        return convertirADTO(medico);
    }
	
	@Transactional
    public MedicoDTO crearMedico(MedicoCreateDTO dto) {
		
        // Validaciones
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (usuarioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        if (medicoRepository.existsByNumeroColegiatura(dto.getNumeroColegiatura())) {
            throw new IllegalArgumentException("El número de colegiatura ya está registrado");
        }
        
        // Buscar especialidad
        Especialidad especialidad = especialidadRepository.findById(dto.getIdEspecialidad()).orElseThrow();
        
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasenia(dto.getContrasenia()); // RECORDAR ENCRIPTAR
        usuario.setTelefono(dto.getTelefono());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        
        // Asignar rol MEDICO (id_rol = 2)
        Rol rolMedico = new Rol();
        rolMedico.setIdRol(2);
        usuario.setRol(rolMedico);
        usuario.setActivo(true);
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        
        // Crear médico
        Medico medico = new Medico();
        medico.setUsuario(usuarioGuardado);
        medico.setEspecialidad(especialidad);
        medico.setNumeroColegiatura(dto.getNumeroColegiatura());
        medico.setTarifaConsulta(dto.getTarifaConsulta());
        medico.setAniosExperiencia(dto.getAniosExperiencia());
        
        Medico medicoGuardado = medicoRepository.save(medico);
        return convertirADTO(medicoGuardado);
    }
	
	@Transactional
    public MedicoDTO actualizar(Integer id, MedicoUpdateDTO dto) {
        Medico medico = medicoRepository.findById(id).orElseThrow();
        
        Usuario usuario = medico.getUsuario();
        
        // Validar email si cambió
        if (!usuario.getEmail().equals(dto.getEmail()) && 
            usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        // Validar número de colegiatura si cambió
        if (!medico.getNumeroColegiatura().equals(dto.getNumeroColegiatura()) &&
            medicoRepository.existsByNumeroColegiatura(dto.getNumeroColegiatura())) {
            throw new IllegalArgumentException("El número de colegiatura ya está registrado");
        }
        
        // Actualizar usuario
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuarioRepository.save(usuario);
        
        // Actualizar médico
        if (dto.getIdEspecialidad() != null) {
            Especialidad especialidad = especialidadRepository.findById(dto.getIdEspecialidad()).orElseThrow();
            medico.setEspecialidad(especialidad);
        }
        
        medico.setNumeroColegiatura(dto.getNumeroColegiatura());
        medico.setTarifaConsulta(dto.getTarifaConsulta());
        medico.setAniosExperiencia(dto.getAniosExperiencia());
        
        Medico medicoActualizado = medicoRepository.save(medico);
        return convertirADTO(medicoActualizado);
    }
	
	@Transactional
	public MedicoDTO cambiarEstado(Integer id) {
	    Medico medico = medicoRepository.findById(id).orElseThrow();

	    Usuario usuario = medico.getUsuario();
	    usuario.setActivo(!usuario.getActivo());

	    usuarioRepository.save(usuario);

	    return convertirADTO(medico);
	}
	
	private MedicoDTO convertirADTO(Medico medico) {
        MedicoDTO dto = new MedicoDTO();
        dto.setIdMedico(medico.getIdMedico());
        dto.setIdUsuario(medico.getUsuario().getIdUsuario());
        dto.setNombreCompleto(medico.getUsuario().getNombreCompleto());
        dto.setEmail(medico.getUsuario().getEmail());
        dto.setTelefono(medico.getUsuario().getTelefono());
        dto.setDni(medico.getUsuario().getDni());
        dto.setIdEspecialidad(medico.getEspecialidad().getIdEspecialidad());
        dto.setNombreEspecialidad(medico.getEspecialidad().getNombreEspecialidad());
        dto.setNumeroColegiatura(medico.getNumeroColegiatura());
        dto.setTarifaConsulta(medico.getTarifaConsulta());
        dto.setAniosExperiencia(medico.getAniosExperiencia());
        dto.setActivo(medico.getUsuario().getActivo());
        return dto;
    }
}
