package com.dermatech.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dermatech.model.Cita;

@Repository
public interface ICitaRepository extends JpaRepository<Cita, Integer> {

	List<Cita> findAllByOrderByFechaHoraDesc();

	/**
	 * Buscar citas por paciente
	 */
	@Query("SELECT c FROM Cita c WHERE c.paciente.idUsuario = :idPaciente ORDER BY c.fechaHora DESC")
	List<Cita> findByPacienteId(@Param("idPaciente") Integer idPaciente);

	/**
	 * Buscar citas por médico
	 */
	@Query("SELECT c FROM Cita c WHERE c.medico.idMedico = :idMedico ORDER BY c.fechaHora DESC")
	List<Cita> findByMedicoId(@Param("idMedico") Integer idMedico);

	/**
	 * Buscar citas por estado
	 */
	@Query("SELECT c FROM Cita c WHERE c.estadoCita.idEstadoCita = :idEstado ORDER BY c.fechaHora DESC")
	List<Cita> findByEstadoId(@Param("idEstado") Integer idEstado);

	/**
	 * Verificar si un médico tiene una cita en un rango de tiempo
	 */
	@Query("SELECT COUNT(c) > 0 FROM Cita c " + "WHERE c.medico.idMedico = :idMedico "
			+ "AND c.fechaHora BETWEEN :inicio AND :fin " + "AND c.idCita != :idCitaExcluir "
			+ "AND c.estadoCita.nombreEstado NOT IN ('Cancelada', 'Rechazada')")
	boolean existeCitaMedicoEnRango(@Param("idMedico") Integer idMedico, @Param("inicio") LocalDateTime inicio,
			@Param("fin") LocalDateTime fin, @Param("idCitaExcluir") Integer idCitaExcluir);

	/**
	 * Verificar si un paciente tiene una cita en una fecha específica
	 */
	@Query("SELECT COUNT(c) > 0 FROM Cita c " + "WHERE c.paciente.idUsuario = :idPaciente "
			+ "AND c.fechaHora = :fechaHora " + "AND c.idCita != :idCitaExcluir "
			+ "AND c.estadoCita.nombreEstado NOT IN ('Cancelada', 'Rechazada')")
	boolean existeCitaPacienteEnFecha(@Param("idPaciente") Integer idPaciente,
			@Param("fechaHora") LocalDateTime fechaHora, @Param("idCitaExcluir") Integer idCitaExcluir);

	/**
	 * Buscar citas en un rango de fechas
	 */
	@Query("SELECT c FROM Cita c " + "WHERE c.fechaHora BETWEEN :inicio AND :fin " + "ORDER BY c.fechaHora ASC")
	List<Cita> findByFechaHoraBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

	/**
	 * Buscar citas de un médico en un rango de fechas
	 */
	@Query("SELECT c FROM Cita c " + "WHERE c.medico.idMedico = :idMedico "
			+ "AND c.fechaHora BETWEEN :inicio AND :fin " + "ORDER BY c.fechaHora ASC")
	List<Cita> findByMedicoAndFechaHoraBetween(@Param("idMedico") Integer idMedico,
			@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

	/**
	 * Buscar citas de un paciente en un rango de fechas
	 */
	@Query("SELECT c FROM Cita c " + "WHERE c.paciente.idUsuario = :idPaciente "
			+ "AND c.fechaHora BETWEEN :inicio AND :fin " + "ORDER BY c.fechaHora ASC")
	List<Cita> findByPacienteAndFechaHoraBetween(@Param("idPaciente") Integer idPaciente,
			@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

	/**
	 * Contar citas por estado
	 */
	@Query("SELECT COUNT(c) FROM Cita c WHERE c.estadoCita.idEstadoCita = :idEstado")
	Long countByEstado(@Param("idEstado") Integer idEstado);

	/**
	 * Contar citas de hoy
	 */
	@Query("SELECT COUNT(c) FROM Cita c " + "WHERE DATE(c.fechaHora) = CURRENT_DATE")
	Long countCitasHoy();

	/**
	 * Obtener citas próximas de un paciente (futuras y no canceladas)
	 */
	@Query("SELECT c FROM Cita c " + "WHERE c.paciente.idUsuario = :idPaciente "
			+ "AND c.fechaHora > CURRENT_TIMESTAMP "
			+ "AND c.estadoCita.nombreEstado NOT IN ('Cancelada', 'Rechazada') " + "ORDER BY c.fechaHora ASC")
	List<Cita> findProximasCitasPaciente(@Param("idPaciente") Integer idPaciente);

	@Query("SELECT c FROM Cita c " + "WHERE c.medico.idMedico = :idMedico " + "AND DATE(c.fechaHora) = CURRENT_DATE "
			+ "ORDER BY c.fechaHora ASC")
	List<Cita> findCitasHoyMedico(@Param("idMedico") Integer idMedico);

	/* Contar Citas y filtrar por pendientes */
	long countByEstadoCita_NombreEstado(String nombreEstado);
}
