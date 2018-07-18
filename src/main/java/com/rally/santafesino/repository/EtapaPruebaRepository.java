package com.rally.santafesino.repository;

import com.rally.santafesino.domain.EtapaPrueba;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Arrays;
import java.util.List;


/**
 * Spring Data JPA repository for the EtapaPrueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapaPruebaRepository extends JpaRepository<EtapaPrueba, Long> {

    @Query(value = "select * from etapa_prueba ep where ep.id_etapa_id = :etapaId", nativeQuery = true)
    List<EtapaPrueba> findAllByEtapa(@Param("etapaId") Long etapaId);
}
