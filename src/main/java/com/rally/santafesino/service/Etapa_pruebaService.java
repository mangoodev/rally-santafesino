package com.rally.santafesino.service;

import com.rally.santafesino.domain.Etapa_prueba;
import com.rally.santafesino.repository.Etapa_pruebaRepository;
import com.rally.santafesino.service.dto.Etapa_pruebaDTO;
import com.rally.santafesino.service.mapper.Etapa_pruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Etapa_prueba.
 */
@Service
@Transactional
public class Etapa_pruebaService {

    private final Logger log = LoggerFactory.getLogger(Etapa_pruebaService.class);

    private final Etapa_pruebaRepository etapa_pruebaRepository;

    private final Etapa_pruebaMapper etapa_pruebaMapper;

    public Etapa_pruebaService(Etapa_pruebaRepository etapa_pruebaRepository, Etapa_pruebaMapper etapa_pruebaMapper) {
        this.etapa_pruebaRepository = etapa_pruebaRepository;
        this.etapa_pruebaMapper = etapa_pruebaMapper;
    }

    /**
     * Save a etapa_prueba.
     *
     * @param etapa_pruebaDTO the entity to save
     * @return the persisted entity
     */
    public Etapa_pruebaDTO save(Etapa_pruebaDTO etapa_pruebaDTO) {
        log.debug("Request to save Etapa_prueba : {}", etapa_pruebaDTO);
        Etapa_prueba etapa_prueba = etapa_pruebaMapper.toEntity(etapa_pruebaDTO);
        etapa_prueba = etapa_pruebaRepository.save(etapa_prueba);
        return etapa_pruebaMapper.toDto(etapa_prueba);
    }

    /**
     * Get all the etapa_pruebas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Etapa_pruebaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Etapa_pruebas");
        return etapa_pruebaRepository.findAll(pageable)
            .map(etapa_pruebaMapper::toDto);
    }

    /**
     * Get one etapa_prueba by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Etapa_pruebaDTO findOne(Long id) {
        log.debug("Request to get Etapa_prueba : {}", id);
        Etapa_prueba etapa_prueba = etapa_pruebaRepository.findOne(id);
        return etapa_pruebaMapper.toDto(etapa_prueba);
    }

    /**
     * Delete the etapa_prueba by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Etapa_prueba : {}", id);
        etapa_pruebaRepository.delete(id);
    }
}
