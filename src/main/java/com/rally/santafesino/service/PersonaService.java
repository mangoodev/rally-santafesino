package com.rally.santafesino.service;

import com.rally.santafesino.domain.Persona;
import com.rally.santafesino.repository.PersonaRepository;
import com.rally.santafesino.service.dto.PersonaDTO;
import com.rally.santafesino.service.mapper.PersonaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Persona.
 */
@Service
@Transactional
public class PersonaService {

    private final Logger log = LoggerFactory.getLogger(PersonaService.class);

    private final PersonaRepository personaRepository;

    private final PersonaMapper personaMapper;

    public PersonaService(PersonaRepository personaRepository, PersonaMapper personaMapper) {
        this.personaRepository = personaRepository;
        this.personaMapper = personaMapper;
    }

    /**
     * Save a persona.
     *
     * @param personaDTO the entity to save
     * @return the persisted entity
     */
    public PersonaDTO save(PersonaDTO personaDTO) {
        log.debug("Request to save Persona : {}", personaDTO);
        Persona persona = personaMapper.toEntity(personaDTO);
        persona = personaRepository.save(persona);
        return personaMapper.toDto(persona);
    }

    /**
     * Get all the personas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PersonaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Personas");
        return personaRepository.findAll(pageable)
            .map(personaMapper::toDto);
    }

    /**
     * Get one persona by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PersonaDTO findOne(Long id) {
        log.debug("Request to get Persona : {}", id);
        Persona persona = personaRepository.findOne(id);
        return personaMapper.toDto(persona);
    }

    /**
     * Delete the persona by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Persona : {}", id);
        personaRepository.delete(id);
    }
}
