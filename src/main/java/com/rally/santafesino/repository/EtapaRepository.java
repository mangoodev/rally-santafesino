package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Etapa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Etapa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Long> {

}
