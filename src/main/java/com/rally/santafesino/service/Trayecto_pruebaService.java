package com.rally.santafesino.service;

import com.rally.santafesino.domain.Trayecto_prueba;
import com.rally.santafesino.repository.Trayecto_pruebaRepository;
import com.rally.santafesino.service.dto.Trayecto_pruebaDTO;
import com.rally.santafesino.service.mapper.Trayecto_pruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Trayecto_prueba.
 */
@Service
@Transactional
public class Trayecto_pruebaService {

    private final Logger log = LoggerFactory.getLogger(Trayecto_pruebaService.class);

    private final Trayecto_pruebaRepository trayecto_pruebaRepository;

    private final Trayecto_pruebaMapper trayecto_pruebaMapper;

    public Trayecto_pruebaService(Trayecto_pruebaRepository trayecto_pruebaRepository, Trayecto_pruebaMapper trayecto_pruebaMapper) {
        this.trayecto_pruebaRepository = trayecto_pruebaRepository;
        this.trayecto_pruebaMapper = trayecto_pruebaMapper;
    }

    /**
     * Save a trayecto_prueba.
     *
     * @param trayecto_pruebaDTO the entity to save
     * @return the persisted entity
     */
    public Trayecto_pruebaDTO save(Trayecto_pruebaDTO trayecto_pruebaDTO) {
        log.debug("Request to save Trayecto_prueba : {}", trayecto_pruebaDTO);
        Trayecto_prueba trayecto_prueba = trayecto_pruebaMapper.toEntity(trayecto_pruebaDTO);
        trayecto_prueba = trayecto_pruebaRepository.save(trayecto_prueba);
        return trayecto_pruebaMapper.toDto(trayecto_prueba);
    }

    /**
     * Get all the trayecto_pruebas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Trayecto_pruebaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trayecto_pruebas");
        return trayecto_pruebaRepository.findAll(pageable)
            .map(trayecto_pruebaMapper::toDto);
    }

    /**
     * Get one trayecto_prueba by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Trayecto_pruebaDTO findOne(Long id) {
        log.debug("Request to get Trayecto_prueba : {}", id);
        Trayecto_prueba trayecto_prueba = trayecto_pruebaRepository.findOne(id);
        return trayecto_pruebaMapper.toDto(trayecto_prueba);
    }

    /**
     * Delete the trayecto_prueba by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Trayecto_prueba : {}", id);
        trayecto_pruebaRepository.delete(id);
    }
}
