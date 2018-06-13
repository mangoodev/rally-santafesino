package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Trayecto_prueba;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Trayecto_prueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Trayecto_pruebaRepository extends JpaRepository<Trayecto_prueba, Long> {

}
