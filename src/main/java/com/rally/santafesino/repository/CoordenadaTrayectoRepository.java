package com.rally.santafesino.repository;

import com.rally.santafesino.domain.CoordenadaTrayecto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Arrays;
import java.util.List;


/**
 * Spring Data JPA repository for the CoordenadaTrayecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoordenadaTrayectoRepository extends JpaRepository<CoordenadaTrayecto, Long> {

    @Query(value = "select * from coordenada_trayecto ct where ct.id_trayecto_id = :trayectoId", nativeQuery = true)
    List<CoordenadaTrayecto> findAllByTrayecto(@Param("trayectoId") Long trayectoId);
}
