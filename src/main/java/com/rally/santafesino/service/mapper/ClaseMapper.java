package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.ClaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Clase and its DTO ClaseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClaseMapper extends EntityMapper<ClaseDTO, Clase> {



    default Clase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Clase clase = new Clase();
        clase.setId(id);
        return clase;
    }
}
