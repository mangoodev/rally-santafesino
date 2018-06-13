package com.rally.santafesino.service;

import com.rally.santafesino.domain.Carrera_etapa;
import com.rally.santafesino.repository.Carrera_etapaRepository;
import com.rally.santafesino.service.dto.Carrera_etapaDTO;
import com.rally.santafesino.service.mapper.Carrera_etapaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Carrera_etapa.
 */
@Service
@Transactional
public class Carrera_etapaService {

    private final Logger log = LoggerFactory.getLogger(Carrera_etapaService.class);

    private final Carrera_etapaRepository carrera_etapaRepository;

    private final Carrera_etapaMapper carrera_etapaMapper;

    public Carrera_etapaService(Carrera_etapaRepository carrera_etapaRepository, Carrera_etapaMapper carrera_etapaMapper) {
        this.carrera_etapaRepository = carrera_etapaRepository;
        this.carrera_etapaMapper = carrera_etapaMapper;
    }

    /**
     * Save a carrera_etapa.
     *
     * @param carrera_etapaDTO the entity to save
     * @return the persisted entity
     */
    public Carrera_etapaDTO save(Carrera_etapaDTO carrera_etapaDTO) {
        log.debug("Request to save Carrera_etapa : {}", carrera_etapaDTO);
        Carrera_etapa carrera_etapa = carrera_etapaMapper.toEntity(carrera_etapaDTO);
        carrera_etapa = carrera_etapaRepository.save(carrera_etapa);
        return carrera_etapaMapper.toDto(carrera_etapa);
    }

    /**
     * Get all the carrera_etapas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Carrera_etapaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Carrera_etapas");
        return carrera_etapaRepository.findAll(pageable)
            .map(carrera_etapaMapper::toDto);
    }

    /**
     * Get one carrera_etapa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Carrera_etapaDTO findOne(Long id) {
        log.debug("Request to get Carrera_etapa : {}", id);
        Carrera_etapa carrera_etapa = carrera_etapaRepository.findOne(id);
        return carrera_etapaMapper.toDto(carrera_etapa);
    }

    /**
     * Delete the carrera_etapa by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Carrera_etapa : {}", id);
        carrera_etapaRepository.delete(id);
    }
}
