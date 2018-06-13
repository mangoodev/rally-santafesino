package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.Auto_carrearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Auto_carrear and its DTO Auto_carrearDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Auto_carrearMapper extends EntityMapper<Auto_carrearDTO, Auto_carrear> {



    default Auto_carrear fromId(Long id) {
        if (id == null) {
            return null;
        }
        Auto_carrear auto_carrear = new Auto_carrear();
        auto_carrear.setId(id);
        return auto_carrear;
    }
}
