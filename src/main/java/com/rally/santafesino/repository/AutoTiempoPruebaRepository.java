package com.rally.santafesino.repository;

import com.rally.santafesino.domain.AutoTiempoPrueba;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AutoTiempoPrueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoTiempoPruebaRepository extends JpaRepository<AutoTiempoPrueba, Long> {

}
