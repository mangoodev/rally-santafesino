package com.rally.santafesino.service;

import com.rally.santafesino.domain.Pruebas;
import com.rally.santafesino.repository.PruebasRepository;
import com.rally.santafesino.service.dto.PruebasDTO;
import com.rally.santafesino.service.mapper.PruebasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Pruebas.
 */
@Service
@Transactional
public class PruebasService {

    private final Logger log = LoggerFactory.getLogger(PruebasService.class);

    private final PruebasRepository pruebasRepository;

    private final PruebasMapper pruebasMapper;

    public PruebasService(PruebasRepository pruebasRepository, PruebasMapper pruebasMapper) {
        this.pruebasRepository = pruebasRepository;
        this.pruebasMapper = pruebasMapper;
    }

    /**
     * Save a pruebas.
     *
     * @param pruebasDTO the entity to save
     * @return the persisted entity
     */
    public PruebasDTO save(PruebasDTO pruebasDTO) {
        log.debug("Request to save Pruebas : {}", pruebasDTO);
        Pruebas pruebas = pruebasMapper.toEntity(pruebasDTO);
        pruebas = pruebasRepository.save(pruebas);
        return pruebasMapper.toDto(pruebas);
    }

    /**
     * Get all the pruebas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PruebasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pruebas");
        return pruebasRepository.findAll(pageable)
            .map(pruebasMapper::toDto);
    }

    /**
     * Get one pruebas by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PruebasDTO findOne(Long id) {
        log.debug("Request to get Pruebas : {}", id);
        Pruebas pruebas = pruebasRepository.findOne(id);
        return pruebasMapper.toDto(pruebas);
    }

    /**
     * Delete the pruebas by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pruebas : {}", id);
        pruebasRepository.delete(id);
    }
}
