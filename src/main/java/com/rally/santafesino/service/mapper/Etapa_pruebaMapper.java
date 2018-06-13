package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.Etapa_pruebaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Etapa_prueba and its DTO Etapa_pruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {EtapaMapper.class, PruebasMapper.class})
public interface Etapa_pruebaMapper extends EntityMapper<Etapa_pruebaDTO, Etapa_prueba> {

    @Mapping(source = "id_etapa.id", target = "id_etapaId")
    @Mapping(source = "id_prueba.id", target = "id_pruebaId")
    Etapa_pruebaDTO toDto(Etapa_prueba etapa_prueba);

    @Mapping(source = "id_etapaId", target = "id_etapa")
    @Mapping(source = "id_pruebaId", target = "id_prueba")
    Etapa_prueba toEntity(Etapa_pruebaDTO etapa_pruebaDTO);

    default Etapa_prueba fromId(Long id) {
        if (id == null) {
            return null;
        }
        Etapa_prueba etapa_prueba = new Etapa_prueba();
        etapa_prueba.setId(id);
        return etapa_prueba;
    }
}
