package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Tiempos;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tiempos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiemposRepository extends JpaRepository<Tiempos, Long> {

}
