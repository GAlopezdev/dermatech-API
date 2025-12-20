package com.dermatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dermatech.model.Especialidad;

@Repository
public interface IEspecialidadRepository extends JpaRepository<Especialidad, Integer>{

}
