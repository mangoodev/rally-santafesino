package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.EtapaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Etapa and its DTO EtapaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EtapaMapper extends EntityMapper<EtapaDTO, Etapa> {



    default Etapa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Etapa etapa = new Etapa();
        etapa.setId(id);
        return etapa;
    }
}
