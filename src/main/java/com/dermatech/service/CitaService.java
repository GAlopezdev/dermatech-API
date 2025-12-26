package com.dermatech.service;

import com.dermatech.DTO.CitaDTO;
import com.dermatech.DTO.CitaCreateDTO;
import com.dermatech.model.*;
import com.dermatech.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired private ICitaRepository citaRepository;
    @Autowired private IUsuarioRepository usuarioRepository;
    @Autowired private IMedicoRepository medicoRepository;
    @Autowired private IEstadoCitaRepository estadoCitaRepository;

    public List<CitaDTO> obtenerTodas() {
        return citaRepository.findAllByOrderByFechaHoraDesc()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CitaDTO crearCita(CitaCreateDTO dto) {
        // 1. Convertir String del DTO a LocalDateTime para lógica de negocio
        LocalDateTime fechaCita = LocalDateTime.parse(dto.getFechaHora());

        // 2. Validaciones de Negocios
        if (fechaCita.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede agendar una cita en el pasado.");
        }

        // Conflicto Médico (±30 min): El repositorio usa LocalDateTime, por eso pasamos fechaCita
        if (citaRepository.existeCitaMedicoEnRango(dto.getIdMedico(), 
                fechaCita.minusMinutes(30), 
                fechaCita.plusMinutes(30), 0)) {
            throw new IllegalArgumentException("El médico ya tiene una cita programada en un rango cercano a esta hora.");
        }

        // Conflicto Paciente
        if (citaRepository.existeCitaPacienteEnFecha(dto.getIdPaciente(), fechaCita, 0)) {
            throw new IllegalArgumentException("El paciente ya tiene una cita agendada para esta fecha y hora.");
        }

        // 3. Carga de entidades (Validar que existan en la BD)
        Usuario paciente = usuarioRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Medico medico = medicoRepository.findById(dto.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        EstadoCita estado = estadoCitaRepository.findById(dto.getIdEstadoCita())
                .orElseThrow(() -> new RuntimeException("Estado de cita no encontrado"));

        // 4. Mapeo a Entidad y Guardado
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setEstadoCita(estado);
        
        // Sincronización con el modelo (LocalDateTime)
        cita.setFechaHora(fechaCita); 
        cita.setMotivoConsulta(dto.getMotivoConsulta());
        cita.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : medico.getTarifaConsulta());
        
        // Auditoría con objetos de tiempo real
        cita.setFechaCreacion(LocalDateTime.now()); 

        return convertirADTO(citaRepository.save(cita));
    }

    @Transactional
    public void eliminarCita(int id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada");
        }
        citaRepository.deleteById(id);
    }

    public List<CitaDTO> listarPorPaciente(int id) {
        return citaRepository.findByPacienteId(id)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    // Método de conversión manual para mantener el control de los datos
    private CitaDTO convertirADTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setIdCita(cita.getIdCita());
        
        // Convertimos LocalDateTime a String para el DTO
        if (cita.getFechaHora() != null) {
            dto.setFechaHora(cita.getFechaHora().toString());
        }
        
        dto.setMotivoConsulta(cita.getMotivoConsulta());
        dto.setDiagnostico(cita.getDiagnostico());
        dto.setTratamiento(cita.getTratamiento());
        dto.setObservaciones(cita.getObservaciones());
        dto.setPrecio(cita.getPrecio());
        
        // Datos del Paciente (Usuario)
        dto.setIdPaciente(cita.getPaciente().getIdUsuario());
        dto.setNombrePaciente(cita.getPaciente().getNombreCompleto());
        
        // Datos del Médico (Relación anidada)
        dto.setIdMedico(cita.getMedico().getIdMedico());
        dto.setNombreMedico(cita.getMedico().getUsuario().getNombreCompleto());
        
        // Datos del Estado
        dto.setIdEstadoCita(cita.getEstadoCita().getIdEstadoCita());
        dto.setNombreEstado(cita.getEstadoCita().getNombreEstado());
        
        return dto;
    }
    
    @Transactional
    public CitaDTO cancelarCita(int id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        // Buscamos el objeto estado que se llame "Cancelada"
        EstadoCita estadoCancelado = estadoCitaRepository.findByNombreEstado("Cancelada")
                .orElseThrow(() -> new RuntimeException("El estado 'Cancelada' no existe en la BD"));
        
        cita.setEstadoCita(estadoCancelado);
        cita.setFechaActualizacion(LocalDateTime.now());
        
        return convertirADTO(citaRepository.save(cita));
    }
}