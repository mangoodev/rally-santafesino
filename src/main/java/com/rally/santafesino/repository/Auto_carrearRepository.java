package com.rally.santafesino.repository;

import com.rally.santafesino.domain.Auto_carrear;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Auto_carrear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Auto_carrearRepository extends JpaRepository<Auto_carrear, Long> {

}
