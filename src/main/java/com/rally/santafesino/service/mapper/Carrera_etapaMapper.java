package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.Carrera_etapaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Carrera_etapa and its DTO Carrera_etapaDTO.
 */
@Mapper(componentModel = "spring", uses = {CarreraMapper.class, EtapaMapper.class})
public interface Carrera_etapaMapper extends EntityMapper<Carrera_etapaDTO, Carrera_etapa> {

    @Mapping(source = "id_carrera.id", target = "id_carreraId")
    @Mapping(source = "id_etapa.id", target = "id_etapaId")
    Carrera_etapaDTO toDto(Carrera_etapa carrera_etapa);

    @Mapping(source = "id_carreraId", target = "id_carrera")
    @Mapping(source = "id_etapaId", target = "id_etapa")
    Carrera_etapa toEntity(Carrera_etapaDTO carrera_etapaDTO);

    default Carrera_etapa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Carrera_etapa carrera_etapa = new Carrera_etapa();
        carrera_etapa.setId(id);
        return carrera_etapa;
    }
}
