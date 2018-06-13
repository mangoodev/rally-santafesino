package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.PruebasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pruebas and its DTO PruebasDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PruebasMapper extends EntityMapper<PruebasDTO, Pruebas> {



    default Pruebas fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pruebas pruebas = new Pruebas();
        pruebas.setId(id);
        return pruebas;
    }
}
