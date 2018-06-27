package com.rally.santafesino.repository;

import com.rally.santafesino.domain.CoordenadaTrayecto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CoordenadaTrayecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoordenadaTrayectoRepository extends JpaRepository<CoordenadaTrayecto, Long> {

}
