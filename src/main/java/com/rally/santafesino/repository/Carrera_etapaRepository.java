package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Carrera_etapa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Carrera_etapa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Carrera_etapaRepository extends JpaRepository<Carrera_etapa, Long> {

}
