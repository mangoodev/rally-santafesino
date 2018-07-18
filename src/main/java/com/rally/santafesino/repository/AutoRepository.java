package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Auto;
import com.rally.santafesino.domain.Persona;
import com.rally.santafesino.service.dto.AutoDTO;
import com.rally.santafesino.service.dto.PersonaDTO;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Auto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {

    List<Auto> findAutoByPiloto_Id(Long piloto);

    List<Auto> findAutoByCopiloto_Id(Long piloto);
}
