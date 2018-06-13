package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.TrayectoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Trayecto and its DTO TrayectoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrayectoMapper extends EntityMapper<TrayectoDTO, Trayecto> {



    default Trayecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trayecto trayecto = new Trayecto();
        trayecto.setId(id);
        return trayecto;
    }
}
