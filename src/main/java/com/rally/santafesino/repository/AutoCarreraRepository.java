package com.rally.santafesino.repository;

import com.rally.santafesino.domain.AutoCarrera;
import com.rally.santafesino.domain.Carrera;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;


/**
 * Spring Data JPA repository for the AutoCarrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoCarreraRepository extends JpaRepository<AutoCarrera, Long> {
    List<AutoCarrera> findAutoCarreraByAuto_Id(Long autoId);

    List<AutoCarrera> findAllByCarrera_Id(Long id);

    List<AutoCarrera> findAllByAuto_Id(Long id);
}
