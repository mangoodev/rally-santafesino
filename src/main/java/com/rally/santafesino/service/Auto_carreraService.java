package com.rally.santafesino.service;

import com.rally.santafesino.domain.Auto_carrera;
import com.rally.santafesino.repository.Auto_carreraRepository;
import com.rally.santafesino.service.dto.Auto_carreraDTO;
import com.rally.santafesino.service.mapper.Auto_carreraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Auto_carrera.
 */
@Service
@Transactional
public class Auto_carreraService {

    private final Logger log = LoggerFactory.getLogger(Auto_carreraService.class);

    private final Auto_carreraRepository auto_carreraRepository;

    private final Auto_carreraMapper auto_carreraMapper;

    public Auto_carreraService(Auto_carreraRepository auto_carreraRepository, Auto_carreraMapper auto_carreraMapper) {
        this.auto_carreraRepository = auto_carreraRepository;
        this.auto_carreraMapper = auto_carreraMapper;
    }

    /**
     * Save a auto_carrera.
     *
     * @param auto_carreraDTO the entity to save
     * @return the persisted entity
     */
    public Auto_carreraDTO save(Auto_carreraDTO auto_carreraDTO) {
        log.debug("Request to save Auto_carrera : {}", auto_carreraDTO);
        Auto_carrera auto_carrera = auto_carreraMapper.toEntity(auto_carreraDTO);
        auto_carrera = auto_carreraRepository.save(auto_carrera);
        return auto_carreraMapper.toDto(auto_carrera);
    }

    /**
     * Get all the auto_carreras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Auto_carreraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Auto_carreras");
        return auto_carreraRepository.findAll(pageable)
            .map(auto_carreraMapper::toDto);
    }

    /**
     * Get one auto_carrera by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Auto_carreraDTO findOne(Long id) {
        log.debug("Request to get Auto_carrera : {}", id);
        Auto_carrera auto_carrera = auto_carreraRepository.findOne(id);
        return auto_carreraMapper.toDto(auto_carrera);
    }

    /**
     * Delete the auto_carrera by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Auto_carrera : {}", id);
        auto_carreraRepository.delete(id);
    }
}
