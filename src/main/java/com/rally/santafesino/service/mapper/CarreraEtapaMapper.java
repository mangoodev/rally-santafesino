package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.CarreraEtapaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CarreraEtapa and its DTO CarreraEtapaDTO.
 */
@Mapper(componentModel = "spring", uses = {CarreraMapper.class, EtapaMapper.class})
public interface CarreraEtapaMapper extends EntityMapper<CarreraEtapaDTO, CarreraEtapa> {

    @Mapping(source = "id_carrera.id", target = "id_carreraId")
    @Mapping(source = "id_etapa.id", target = "id_etapaId")
    CarreraEtapaDTO toDto(CarreraEtapa carreraEtapa);

    @Mapping(source = "id_carreraId", target = "id_carrera")
    @Mapping(source = "id_etapaId", target = "id_etapa")
    CarreraEtapa toEntity(CarreraEtapaDTO carreraEtapaDTO);

    default CarreraEtapa fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarreraEtapa carreraEtapa = new CarreraEtapa();
        carreraEtapa.setId(id);
        return carreraEtapa;
    }
}
