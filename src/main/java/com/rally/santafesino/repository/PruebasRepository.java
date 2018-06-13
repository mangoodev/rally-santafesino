package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Pruebas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pruebas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PruebasRepository extends JpaRepository<Pruebas, Long> {

}
