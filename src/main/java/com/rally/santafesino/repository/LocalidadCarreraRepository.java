package com.rally.santafesino.repository;

import com.rally.santafesino.domain.LocalidadCarrera;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LocalidadCarrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalidadCarreraRepository extends JpaRepository<LocalidadCarrera, Long> {

}
