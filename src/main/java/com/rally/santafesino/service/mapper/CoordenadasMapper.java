package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.CoordenadasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Coordenadas and its DTO CoordenadasDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoordenadasMapper extends EntityMapper<CoordenadasDTO, Coordenadas> {



    default Coordenadas fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coordenadas coordenadas = new Coordenadas();
        coordenadas.setId(id);
        return coordenadas;
    }
}
