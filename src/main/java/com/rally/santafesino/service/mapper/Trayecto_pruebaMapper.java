package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.Trayecto_pruebaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Trayecto_prueba and its DTO Trayecto_pruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {PruebasMapper.class, TrayectoMapper.class})
public interface Trayecto_pruebaMapper extends EntityMapper<Trayecto_pruebaDTO, Trayecto_prueba> {

    @Mapping(source = "id_prueba.id", target = "id_pruebaId")
    @Mapping(source = "id_trayecto.id", target = "id_trayectoId")
    Trayecto_pruebaDTO toDto(Trayecto_prueba trayecto_prueba);

    @Mapping(source = "id_pruebaId", target = "id_prueba")
    @Mapping(source = "id_trayectoId", target = "id_trayecto")
    Trayecto_prueba toEntity(Trayecto_pruebaDTO trayecto_pruebaDTO);

    default Trayecto_prueba fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trayecto_prueba trayecto_prueba = new Trayecto_prueba();
        trayecto_prueba.setId(id);
        return trayecto_prueba;
    }
}
