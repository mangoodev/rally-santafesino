package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.Localidad_carreraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Localidad_carrera and its DTO Localidad_carreraDTO.
 */
@Mapper(componentModel = "spring", uses = {CarreraMapper.class, LocalidadMapper.class})
public interface Localidad_carreraMapper extends EntityMapper<Localidad_carreraDTO, Localidad_carrera> {

    @Mapping(source = "id_carrera.id", target = "id_carreraId")
    @Mapping(source = "id_localidad.id", target = "id_localidadId")
    Localidad_carreraDTO toDto(Localidad_carrera localidad_carrera);

    @Mapping(source = "id_carreraId", target = "id_carrera")
    @Mapping(source = "id_localidadId", target = "id_localidad")
    Localidad_carrera toEntity(Localidad_carreraDTO localidad_carreraDTO);

    default Localidad_carrera fromId(Long id) {
        if (id == null) {
            return null;
        }
        Localidad_carrera localidad_carrera = new Localidad_carrera();
        localidad_carrera.setId(id);
        return localidad_carrera;
    }
}
