package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.Coordenada_trayectoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Coordenada_trayecto and its DTO Coordenada_trayectoDTO.
 */
@Mapper(componentModel = "spring", uses = {CoordenadasMapper.class, TrayectoMapper.class})
public interface Coordenada_trayectoMapper extends EntityMapper<Coordenada_trayectoDTO, Coordenada_trayecto> {

    @Mapping(source = "id_coordenadas.id", target = "id_coordenadasId")
    @Mapping(source = "id_trayecto.id", target = "id_trayectoId")
    Coordenada_trayectoDTO toDto(Coordenada_trayecto coordenada_trayecto);

    @Mapping(source = "id_coordenadasId", target = "id_coordenadas")
    @Mapping(source = "id_trayectoId", target = "id_trayecto")
    Coordenada_trayecto toEntity(Coordenada_trayectoDTO coordenada_trayectoDTO);

    default Coordenada_trayecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coordenada_trayecto coordenada_trayecto = new Coordenada_trayecto();
        coordenada_trayecto.setId(id);
        return coordenada_trayecto;
    }
}
