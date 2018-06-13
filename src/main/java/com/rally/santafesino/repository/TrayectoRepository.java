package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Trayecto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Trayecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrayectoRepository extends JpaRepository<Trayecto, Long> {

}
