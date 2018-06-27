package com.rally.santafesino.service;

import com.rally.santafesino.domain.CoordenadaTrayecto;
import com.rally.santafesino.repository.CoordenadaTrayectoRepository;
import com.rally.santafesino.service.dto.CoordenadaTrayectoDTO;
import com.rally.santafesino.service.mapper.CoordenadaTrayectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CoordenadaTrayecto.
 */
@Service
@Transactional
public class CoordenadaTrayectoService {

    private final Logger log = LoggerFactory.getLogger(CoordenadaTrayectoService.class);

    private final CoordenadaTrayectoRepository coordenadaTrayectoRepository;

    private final CoordenadaTrayectoMapper coordenadaTrayectoMapper;

    public CoordenadaTrayectoService(CoordenadaTrayectoRepository coordenadaTrayectoRepository, CoordenadaTrayectoMapper coordenadaTrayectoMapper) {
        this.coordenadaTrayectoRepository = coordenadaTrayectoRepository;
        this.coordenadaTrayectoMapper = coordenadaTrayectoMapper;
    }

    /**
     * Save a coordenadaTrayecto.
     *
     * @param coordenadaTrayectoDTO the entity to save
     * @return the persisted entity
     */
    public CoordenadaTrayectoDTO save(CoordenadaTrayectoDTO coordenadaTrayectoDTO) {
        log.debug("Request to save CoordenadaTrayecto : {}", coordenadaTrayectoDTO);
        CoordenadaTrayecto coordenadaTrayecto = coordenadaTrayectoMapper.toEntity(coordenadaTrayectoDTO);
        coordenadaTrayecto = coordenadaTrayectoRepository.save(coordenadaTrayecto);
        return coordenadaTrayectoMapper.toDto(coordenadaTrayecto);
    }

    /**
     * Get all the coordenadaTrayectos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CoordenadaTrayectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoordenadaTrayectos");
        return coordenadaTrayectoRepository.findAll(pageable)
            .map(coordenadaTrayectoMapper::toDto);
    }

    /**
     * Get one coordenadaTrayecto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CoordenadaTrayectoDTO findOne(Long id) {
        log.debug("Request to get CoordenadaTrayecto : {}", id);
        CoordenadaTrayecto coordenadaTrayecto = coordenadaTrayectoRepository.findOne(id);
        return coordenadaTrayectoMapper.toDto(coordenadaTrayecto);
    }

    /**
     * Delete the coordenadaTrayecto by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CoordenadaTrayecto : {}", id);
        coordenadaTrayectoRepository.delete(id);
    }
}
