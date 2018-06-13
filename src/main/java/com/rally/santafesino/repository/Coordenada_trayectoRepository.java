package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Coordenada_trayecto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Coordenada_trayecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Coordenada_trayectoRepository extends JpaRepository<Coordenada_trayecto, Long> {

}
