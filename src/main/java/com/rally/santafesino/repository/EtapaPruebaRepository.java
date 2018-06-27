package com.rally.santafesino.repository;

import com.rally.santafesino.domain.EtapaPrueba;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EtapaPrueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapaPruebaRepository extends JpaRepository<EtapaPrueba, Long> {

}
