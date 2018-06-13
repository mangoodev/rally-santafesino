package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Etapa_prueba;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Etapa_prueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Etapa_pruebaRepository extends JpaRepository<Etapa_prueba, Long> {

}
