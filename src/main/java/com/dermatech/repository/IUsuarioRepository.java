package com.dermatech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dermatech.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{

	Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);

}
