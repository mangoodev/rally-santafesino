package com.rally.santafesino.service;

import com.rally.santafesino.domain.Auto_tiempo_prueba;
import com.rally.santafesino.repository.Auto_tiempo_pruebaRepository;
import com.rally.santafesino.service.dto.Auto_tiempo_pruebaDTO;
import com.rally.santafesino.service.mapper.Auto_tiempo_pruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Auto_tiempo_prueba.
 */
@Service
@Transactional
public class Auto_tiempo_pruebaService {

    private final Logger log = LoggerFactory.getLogger(Auto_tiempo_pruebaService.class);

    private final Auto_tiempo_pruebaRepository auto_tiempo_pruebaRepository;

    private final Auto_tiempo_pruebaMapper auto_tiempo_pruebaMapper;

    public Auto_tiempo_pruebaService(Auto_tiempo_pruebaRepository auto_tiempo_pruebaRepository, Auto_tiempo_pruebaMapper auto_tiempo_pruebaMapper) {
        this.auto_tiempo_pruebaRepository = auto_tiempo_pruebaRepository;
        this.auto_tiempo_pruebaMapper = auto_tiempo_pruebaMapper;
    }

    /**
     * Save a auto_tiempo_prueba.
     *
     * @param auto_tiempo_pruebaDTO the entity to save
     * @return the persisted entity
     */
    public Auto_tiempo_pruebaDTO save(Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO) {
        log.debug("Request to save Auto_tiempo_prueba : {}", auto_tiempo_pruebaDTO);
        Auto_tiempo_prueba auto_tiempo_prueba = auto_tiempo_pruebaMapper.toEntity(auto_tiempo_pruebaDTO);
        auto_tiempo_prueba = auto_tiempo_pruebaRepository.save(auto_tiempo_prueba);
        return auto_tiempo_pruebaMapper.toDto(auto_tiempo_prueba);
    }

    /**
     * Get all the auto_tiempo_pruebas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Auto_tiempo_pruebaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Auto_tiempo_pruebas");
        return auto_tiempo_pruebaRepository.findAll(pageable)
            .map(auto_tiempo_pruebaMapper::toDto);
    }

    /**
     * Get one auto_tiempo_prueba by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Auto_tiempo_pruebaDTO findOne(Long id) {
        log.debug("Request to get Auto_tiempo_prueba : {}", id);
        Auto_tiempo_prueba auto_tiempo_prueba = auto_tiempo_pruebaRepository.findOne(id);
        return auto_tiempo_pruebaMapper.toDto(auto_tiempo_prueba);
    }

    /**
     * Delete the auto_tiempo_prueba by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Auto_tiempo_prueba : {}", id);
        auto_tiempo_pruebaRepository.delete(id);
    }
}
