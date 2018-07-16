package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Clase;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Clase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

}
