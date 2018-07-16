package com.rally.santafesino.repository;

import com.rally.santafesino.domain.CarreraClase;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CarreraClase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarreraClaseRepository extends JpaRepository<CarreraClase, Long> {
    List<CarreraClase> findAllByClase_Nombre(String clase);
}
