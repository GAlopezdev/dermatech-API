package com.dermatech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dermatech.model.Medico;

@Repository
public interface IMedicoRepository extends JpaRepository<Medico, Integer>{

	@Query("SELECT m FROM Medico m WHERE m.usuario.activo = true")
    List<Medico> findAllActivos();
	
	List<Medico> findByEspecialidadIdEspecialidad(Integer idEspecialidad);
	
	Optional<Medico> findByNumeroColegiatura(String numeroColegiatura);
	
	boolean existsByNumeroColegiatura(String numeroColegiatura);
	
	@Query("SELECT m FROM Medico m WHERE m.usuario.id = :idUsuario")
    Optional<Medico> findByUsuarioId(Integer idUsuario);
}
