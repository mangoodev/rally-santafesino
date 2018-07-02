package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.AutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Auto and its DTO AutoDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface AutoMapper extends EntityMapper<AutoDTO, Auto> {

    @Mapping(source = "copiloto.id", target = "copilotoId")
    @Mapping(source = "piloto.id", target = "pilotoId")
    @Mapping(source = "piloto.nombre", target = "pilotoNombre")
    @Mapping(source = "copiloto.nombre", target = "copilotoNombre")
    AutoDTO toDto(Auto auto);

    @Mapping(source = "copilotoId", target = "copiloto")
    @Mapping(source = "pilotoId", target = "piloto")
    Auto toEntity(AutoDTO autoDTO);

    default Auto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Auto auto = new Auto();
        auto.setId(id);
        return auto;
    }
}
