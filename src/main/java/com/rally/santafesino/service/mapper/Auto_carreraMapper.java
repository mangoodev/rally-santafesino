package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.Auto_carreraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Auto_carrera and its DTO Auto_carreraDTO.
 */
@Mapper(componentModel = "spring", uses = {AutoMapper.class, CarreraMapper.class})
public interface Auto_carreraMapper extends EntityMapper<Auto_carreraDTO, Auto_carrera> {

    @Mapping(source = "auto.id", target = "autoId")
    @Mapping(source = "carrera.id", target = "carreraId")
    Auto_carreraDTO toDto(Auto_carrera auto_carrera);

    @Mapping(source = "autoId", target = "auto")
    @Mapping(source = "carreraId", target = "carrera")
    Auto_carrera toEntity(Auto_carreraDTO auto_carreraDTO);

    default Auto_carrera fromId(Long id) {
        if (id == null) {
            return null;
        }
        Auto_carrera auto_carrera = new Auto_carrera();
        auto_carrera.setId(id);
        return auto_carrera;
    }
}
