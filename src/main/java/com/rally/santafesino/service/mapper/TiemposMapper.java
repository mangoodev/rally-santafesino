package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.TiemposDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tiempos and its DTO TiemposDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TiemposMapper extends EntityMapper<TiemposDTO, Tiempos> {


    @Mapping(target = "auto_tiempo_prueba", ignore = true)
    Tiempos toEntity(TiemposDTO tiemposDTO);

    default Tiempos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tiempos tiempos = new Tiempos();
        tiempos.setId(id);
        return tiempos;
    }
}
