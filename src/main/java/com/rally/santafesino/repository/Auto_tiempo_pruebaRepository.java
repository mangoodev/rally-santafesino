package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Auto_tiempo_prueba;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Auto_tiempo_prueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Auto_tiempo_pruebaRepository extends JpaRepository<Auto_tiempo_prueba, Long> {

}
