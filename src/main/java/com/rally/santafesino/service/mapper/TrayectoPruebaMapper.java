package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.TrayectoPruebaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TrayectoPrueba and its DTO TrayectoPruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {PruebasMapper.class, TrayectoMapper.class})
public interface TrayectoPruebaMapper extends EntityMapper<TrayectoPruebaDTO, TrayectoPrueba> {

    @Mapping(source = "id_prueba.id", target = "id_pruebaId")
    @Mapping(source = "id_trayecto.id", target = "id_trayectoId")
    TrayectoPruebaDTO toDto(TrayectoPrueba trayectoPrueba);

    @Mapping(source = "id_pruebaId", target = "id_prueba")
    @Mapping(source = "id_trayectoId", target = "id_trayecto")
    TrayectoPrueba toEntity(TrayectoPruebaDTO trayectoPruebaDTO);

    default TrayectoPrueba fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrayectoPrueba trayectoPrueba = new TrayectoPrueba();
        trayectoPrueba.setId(id);
        return trayectoPrueba;
    }
}
