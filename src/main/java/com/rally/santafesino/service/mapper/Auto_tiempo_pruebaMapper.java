package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.Auto_tiempo_pruebaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Auto_tiempo_prueba and its DTO Auto_tiempo_pruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {AutoMapper.class, TiemposMapper.class, PruebasMapper.class})
public interface Auto_tiempo_pruebaMapper extends EntityMapper<Auto_tiempo_pruebaDTO, Auto_tiempo_prueba> {

    @Mapping(source = "id_auto.id", target = "id_autoId")
    @Mapping(source = "id_tiempos.id", target = "id_tiemposId")
    @Mapping(source = "id_prueba.id", target = "id_pruebaId")
    Auto_tiempo_pruebaDTO toDto(Auto_tiempo_prueba auto_tiempo_prueba);

    @Mapping(source = "id_autoId", target = "id_auto")
    @Mapping(source = "id_tiemposId", target = "id_tiempos")
    @Mapping(source = "id_pruebaId", target = "id_prueba")
    Auto_tiempo_prueba toEntity(Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO);

    default Auto_tiempo_prueba fromId(Long id) {
        if (id == null) {
            return null;
        }
        Auto_tiempo_prueba auto_tiempo_prueba = new Auto_tiempo_prueba();
        auto_tiempo_prueba.setId(id);
        return auto_tiempo_prueba;
    }
}
