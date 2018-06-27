package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.LocalidadCarreraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LocalidadCarrera and its DTO LocalidadCarreraDTO.
 */
@Mapper(componentModel = "spring", uses = {CarreraMapper.class, LocalidadMapper.class})
public interface LocalidadCarreraMapper extends EntityMapper<LocalidadCarreraDTO, LocalidadCarrera> {

    @Mapping(source = "id_carrera.id", target = "id_carreraId")
    @Mapping(source = "id_localidad.id", target = "id_localidadId")
    LocalidadCarreraDTO toDto(LocalidadCarrera localidadCarrera);

    @Mapping(source = "id_carreraId", target = "id_carrera")
    @Mapping(source = "id_localidadId", target = "id_localidad")
    LocalidadCarrera toEntity(LocalidadCarreraDTO localidadCarreraDTO);

    default LocalidadCarrera fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalidadCarrera localidadCarrera = new LocalidadCarrera();
        localidadCarrera.setId(id);
        return localidadCarrera;
    }
}
