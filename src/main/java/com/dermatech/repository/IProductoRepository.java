package com.dermatech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dermatech.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer>{

	// Listado de productos activos
	List<Producto> findByActivoTrue();

}
