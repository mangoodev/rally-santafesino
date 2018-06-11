package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Auto_carrera;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Auto_carrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Auto_carreraRepository extends JpaRepository<Auto_carrera, Long> {

}
