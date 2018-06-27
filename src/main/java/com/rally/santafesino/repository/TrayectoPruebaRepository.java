package com.rally.santafesino.repository;

import com.rally.santafesino.domain.TrayectoPrueba;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TrayectoPrueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrayectoPruebaRepository extends JpaRepository<TrayectoPrueba, Long> {

}
