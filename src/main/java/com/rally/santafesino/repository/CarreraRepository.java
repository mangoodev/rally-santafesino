package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Carrera;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Carrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

}
