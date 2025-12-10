package com.dermatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dermatech.model.CategoriaProducto;

public interface ICategoriaProductoRepository extends JpaRepository<CategoriaProducto, Integer>{

}
