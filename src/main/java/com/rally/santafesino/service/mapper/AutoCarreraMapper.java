package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.AutoCarreraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AutoCarrera and its DTO AutoCarreraDTO.
 */
@Mapper(componentModel = "spring", uses = {AutoMapper.class, CarreraMapper.class})
public interface AutoCarreraMapper extends EntityMapper<AutoCarreraDTO, AutoCarrera> {

    @Mapping(source = "auto.id", target = "autoId")
    @Mapping(source = "carrera.id", target = "carreraId")
    AutoCarreraDTO toDto(AutoCarrera autoCarrera);

    @Mapping(source = "autoId", target = "auto")
    @Mapping(source = "carreraId", target = "carrera")
    AutoCarrera toEntity(AutoCarreraDTO autoCarreraDTO);

    default AutoCarrera fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutoCarrera autoCarrera = new AutoCarrera();
        autoCarrera.setId(id);
        return autoCarrera;
    }
}
