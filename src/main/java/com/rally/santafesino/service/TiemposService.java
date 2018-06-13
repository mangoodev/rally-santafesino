package com.rally.santafesino.service;

import com.rally.santafesino.domain.Tiempos;
import com.rally.santafesino.repository.TiemposRepository;
import com.rally.santafesino.service.dto.TiemposDTO;
import com.rally.santafesino.service.mapper.TiemposMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Tiempos.
 */
@Service
@Transactional
public class TiemposService {

    private final Logger log = LoggerFactory.getLogger(TiemposService.class);

    private final TiemposRepository tiemposRepository;

    private final TiemposMapper tiemposMapper;

    public TiemposService(TiemposRepository tiemposRepository, TiemposMapper tiemposMapper) {
        this.tiemposRepository = tiemposRepository;
        this.tiemposMapper = tiemposMapper;
    }

    /**
     * Save a tiempos.
     *
     * @param tiemposDTO the entity to save
     * @return the persisted entity
     */
    public TiemposDTO save(TiemposDTO tiemposDTO) {
        log.debug("Request to save Tiempos : {}", tiemposDTO);
        Tiempos tiempos = tiemposMapper.toEntity(tiemposDTO);
        tiempos = tiemposRepository.save(tiempos);
        return tiemposMapper.toDto(tiempos);
    }

    /**
     * Get all the tiempos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TiemposDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tiempos");
        return tiemposRepository.findAll(pageable)
            .map(tiemposMapper::toDto);
    }


    /**
     *  get all the tiempos where Auto_tiempo_prueba is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TiemposDTO> findAllWhereAuto_tiempo_pruebaIsNull() {
        log.debug("Request to get all tiempos where Auto_tiempo_prueba is null");
        return StreamSupport
            .stream(tiemposRepository.findAll().spliterator(), false)
            .filter(tiempos -> tiempos.getAuto_tiempo_prueba() == null)
            .map(tiemposMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tiempos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TiemposDTO findOne(Long id) {
        log.debug("Request to get Tiempos : {}", id);
        Tiempos tiempos = tiemposRepository.findOne(id);
        return tiemposMapper.toDto(tiempos);
    }

    /**
     * Delete the tiempos by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tiempos : {}", id);
        tiemposRepository.delete(id);
    }
}
