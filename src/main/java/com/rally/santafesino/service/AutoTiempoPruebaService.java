package com.rally.santafesino.service;

import com.rally.santafesino.domain.AutoTiempoPrueba;
import com.rally.santafesino.repository.AutoTiempoPruebaRepository;
import com.rally.santafesino.service.dto.AutoTiempoPruebaDTO;
import com.rally.santafesino.service.mapper.AutoTiempoPruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AutoTiempoPrueba.
 */
@Service
@Transactional
public class AutoTiempoPruebaService {

    private final Logger log = LoggerFactory.getLogger(AutoTiempoPruebaService.class);

    private final AutoTiempoPruebaRepository autoTiempoPruebaRepository;

    private final AutoTiempoPruebaMapper autoTiempoPruebaMapper;

    public AutoTiempoPruebaService(AutoTiempoPruebaRepository autoTiempoPruebaRepository, AutoTiempoPruebaMapper autoTiempoPruebaMapper) {
        this.autoTiempoPruebaRepository = autoTiempoPruebaRepository;
        this.autoTiempoPruebaMapper = autoTiempoPruebaMapper;
    }

    /**
     * Save a autoTiempoPrueba.
     *
     * @param autoTiempoPruebaDTO the entity to save
     * @return the persisted entity
     */
    public AutoTiempoPruebaDTO save(AutoTiempoPruebaDTO autoTiempoPruebaDTO) {
        log.debug("Request to save AutoTiempoPrueba : {}", autoTiempoPruebaDTO);
        AutoTiempoPrueba autoTiempoPrueba = autoTiempoPruebaMapper.toEntity(autoTiempoPruebaDTO);
        autoTiempoPrueba = autoTiempoPruebaRepository.save(autoTiempoPrueba);
        return autoTiempoPruebaMapper.toDto(autoTiempoPrueba);
    }

    /**
     * Get all the autoTiempoPruebas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AutoTiempoPruebaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutoTiempoPruebas");
        return autoTiempoPruebaRepository.findAll(pageable)
            .map(autoTiempoPruebaMapper::toDto);
    }

    /**
     * Get one autoTiempoPrueba by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AutoTiempoPruebaDTO findOne(Long id) {
        log.debug("Request to get AutoTiempoPrueba : {}", id);
        AutoTiempoPrueba autoTiempoPrueba = autoTiempoPruebaRepository.findOne(id);
        return autoTiempoPruebaMapper.toDto(autoTiempoPrueba);
    }

    /**
     * Delete the autoTiempoPrueba by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AutoTiempoPrueba : {}", id);
        autoTiempoPruebaRepository.delete(id);
    }
}
