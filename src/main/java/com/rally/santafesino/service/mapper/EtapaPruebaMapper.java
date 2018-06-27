package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.EtapaPruebaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EtapaPrueba and its DTO EtapaPruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {EtapaMapper.class, PruebasMapper.class})
public interface EtapaPruebaMapper extends EntityMapper<EtapaPruebaDTO, EtapaPrueba> {

    @Mapping(source = "id_etapa.id", target = "id_etapaId")
    @Mapping(source = "id_prueba.id", target = "id_pruebaId")
    EtapaPruebaDTO toDto(EtapaPrueba etapaPrueba);

    @Mapping(source = "id_etapaId", target = "id_etapa")
    @Mapping(source = "id_pruebaId", target = "id_prueba")
    EtapaPrueba toEntity(EtapaPruebaDTO etapaPruebaDTO);

    default EtapaPrueba fromId(Long id) {
        if (id == null) {
            return null;
        }
        EtapaPrueba etapaPrueba = new EtapaPrueba();
        etapaPrueba.setId(id);
        return etapaPrueba;
    }
}
