package com.rally.santafesino.repository;

import com.rally.santafesino.domain.AutoCarrera;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AutoCarrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoCarreraRepository extends JpaRepository<AutoCarrera, Long> {

}
