package com.rally.santafesino.repository;

import com.rally.santafesino.domain.CarreraEtapa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CarreraEtapa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarreraEtapaRepository extends JpaRepository<CarreraEtapa, Long> {

}
