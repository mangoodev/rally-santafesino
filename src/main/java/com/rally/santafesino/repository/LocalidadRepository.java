package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Localidad;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Localidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

}
