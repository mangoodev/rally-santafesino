package com.rally.santafesino.service.mapper;

import com.rally.santafesino.domain.*;
import com.rally.santafesino.service.dto.PersonaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Persona and its DTO PersonaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonaMapper extends EntityMapper<PersonaDTO, Persona> {



    default Persona fromId(Long id) {
        if (id == null) {
            return null;
        }
        Persona persona = new Persona();
        persona.setId(id);
        return persona;
    }
}
