package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.LocalidadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Localidad and its DTO LocalidadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocalidadMapper extends EntityMapper<LocalidadDTO, Localidad> {



    default Localidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        Localidad localidad = new Localidad();
        localidad.setId(id);
        return localidad;
    }
}
