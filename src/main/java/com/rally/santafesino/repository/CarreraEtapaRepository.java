package com.rally.santafesino.repository;

import com.rally.santafesino.domain.CarreraEtapa;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Arrays;
import java.util.List;


/**
 * Spring Data JPA repository for the CarreraEtapa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarreraEtapaRepository extends JpaRepository<CarreraEtapa, Long> {

    @Query(value = "select * from carreraEtapa ce where ce.id_carrera_id = :carreraId", nativeQuery = true)
    List<CarreraEtapa> findAllByCarrera(@Param("carreraId") Long carreraId);

}
