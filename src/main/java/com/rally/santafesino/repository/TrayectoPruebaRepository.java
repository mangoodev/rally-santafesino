package com.rally.santafesino.repository;

import com.rally.santafesino.domain.TrayectoPrueba;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Arrays;
import java.util.List;


/**
 * Spring Data JPA repository for the TrayectoPrueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrayectoPruebaRepository extends JpaRepository<TrayectoPrueba, Long> {

    @Query(value = "select * from trayecto_prueba tp where tp.id_prueba_id = :pruebaId", nativeQuery = true)
    List<TrayectoPrueba> findAllByPrueba(@Param("pruebaId") Long pruebaId);
}
