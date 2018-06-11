package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.CarreraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Carrera and its DTO CarreraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarreraMapper extends EntityMapper<CarreraDTO, Carrera> {



    default Carrera fromId(Long id) {
        if (id == null) {
            return null;
        }
        Carrera carrera = new Carrera();
        carrera.setId(id);
        return carrera;
    }
}
