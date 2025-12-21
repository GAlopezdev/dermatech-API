package com.dermatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dermatech.model.Rol;

public interface IRolesRepository extends JpaRepository<Rol, Integer>  {

}
