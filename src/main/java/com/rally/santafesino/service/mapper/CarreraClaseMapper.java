package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.CarreraClaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CarreraClase and its DTO CarreraClaseDTO.
 */
@Mapper(componentModel = "spring", uses = {CarreraMapper.class, ClaseMapper.class})
public interface CarreraClaseMapper extends EntityMapper<CarreraClaseDTO, CarreraClase> {

    @Mapping(source = "carrera.id", target = "carreraId")
    @Mapping(source = "clase.id", target = "claseId")
    CarreraClaseDTO toDto(CarreraClase carreraClase);

    @Mapping(source = "carreraId", target = "carrera")
    @Mapping(source = "claseId", target = "clase")
    CarreraClase toEntity(CarreraClaseDTO carreraClaseDTO);

    default CarreraClase fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarreraClase carreraClase = new CarreraClase();
        carreraClase.setId(id);
        return carreraClase;
    }
}
