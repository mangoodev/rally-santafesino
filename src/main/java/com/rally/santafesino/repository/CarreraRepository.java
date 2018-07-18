package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.service.dto.CarreraDTO;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the Carrera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    List<Carrera> findByFechaAfterAndFechaBeforeOrderByFechaAsc(ZonedDateTime comienzoDia, ZonedDateTime finalDia);
}
