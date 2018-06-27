package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.CoordenadaTrayectoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CoordenadaTrayecto and its DTO CoordenadaTrayectoDTO.
 */
@Mapper(componentModel = "spring", uses = {CoordenadasMapper.class, TrayectoMapper.class})
public interface CoordenadaTrayectoMapper extends EntityMapper<CoordenadaTrayectoDTO, CoordenadaTrayecto> {

    @Mapping(source = "id_coordenadas.id", target = "id_coordenadasId")
    @Mapping(source = "id_trayecto.id", target = "id_trayectoId")
    CoordenadaTrayectoDTO toDto(CoordenadaTrayecto coordenadaTrayecto);

    @Mapping(source = "id_coordenadasId", target = "id_coordenadas")
    @Mapping(source = "id_trayectoId", target = "id_trayecto")
    CoordenadaTrayecto toEntity(CoordenadaTrayectoDTO coordenadaTrayectoDTO);

    default CoordenadaTrayecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoordenadaTrayecto coordenadaTrayecto = new CoordenadaTrayecto();
        coordenadaTrayecto.setId(id);
        return coordenadaTrayecto;
    }
}
