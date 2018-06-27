package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.AutoTiempoPruebaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AutoTiempoPrueba and its DTO AutoTiempoPruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {AutoMapper.class, TiemposMapper.class, PruebasMapper.class})
public interface AutoTiempoPruebaMapper extends EntityMapper<AutoTiempoPruebaDTO, AutoTiempoPrueba> {

    @Mapping(source = "id_auto.id", target = "id_autoId")
    @Mapping(source = "id_tiempos.id", target = "id_tiemposId")
    @Mapping(source = "id_prueba.id", target = "id_pruebaId")
    AutoTiempoPruebaDTO toDto(AutoTiempoPrueba autoTiempoPrueba);

    @Mapping(source = "id_autoId", target = "id_auto")
    @Mapping(source = "id_tiemposId", target = "id_tiempos")
    @Mapping(source = "id_pruebaId", target = "id_prueba")
    AutoTiempoPrueba toEntity(AutoTiempoPruebaDTO autoTiempoPruebaDTO);

    default AutoTiempoPrueba fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutoTiempoPrueba autoTiempoPrueba = new AutoTiempoPrueba();
        autoTiempoPrueba.setId(id);
        return autoTiempoPrueba;
    }
}
