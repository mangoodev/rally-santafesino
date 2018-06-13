package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Coordenadas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Coordenadas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long> {

}
