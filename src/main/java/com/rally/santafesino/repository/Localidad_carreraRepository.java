package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Localidad_carrera;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Localidad_carrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Localidad_carreraRepository extends JpaRepository<Localidad_carrera, Long> {

}
