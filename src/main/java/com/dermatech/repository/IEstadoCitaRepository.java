package com.dermatech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dermatech.model.EstadoCita;


@Repository
public interface IEstadoCitaRepository extends JpaRepository<EstadoCita, Integer>{
	
    Optional<EstadoCita> findByNombreEstado(String nombreEstado);
    
    boolean existsByNombreEstado(String nombreEstado);
}
