package com.rally.santafesino.service;

import com.rally.santafesino.domain.Coordenada_trayecto;
import com.rally.santafesino.repository.Coordenada_trayectoRepository;
import com.rally.santafesino.service.dto.Coordenada_trayectoDTO;
import com.rally.santafesino.service.mapper.Coordenada_trayectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Coordenada_trayecto.
 */
@Service
@Transactional
public class Coordenada_trayectoService {

    private final Logger log = LoggerFactory.getLogger(Coordenada_trayectoService.class);

    private final Coordenada_trayectoRepository coordenada_trayectoRepository;

    private final Coordenada_trayectoMapper coordenada_trayectoMapper;

    public Coordenada_trayectoService(Coordenada_trayectoRepository coordenada_trayectoRepository, Coordenada_trayectoMapper coordenada_trayectoMapper) {
        this.coordenada_trayectoRepository = coordenada_trayectoRepository;
        this.coordenada_trayectoMapper = coordenada_trayectoMapper;
    }

    /**
     * Save a coordenada_trayecto.
     *
     * @param coordenada_trayectoDTO the entity to save
     * @return the persisted entity
     */
    public Coordenada_trayectoDTO save(Coordenada_trayectoDTO coordenada_trayectoDTO) {
        log.debug("Request to save Coordenada_trayecto : {}", coordenada_trayectoDTO);
        Coordenada_trayecto coordenada_trayecto = coordenada_trayectoMapper.toEntity(coordenada_trayectoDTO);
        coordenada_trayecto = coordenada_trayectoRepository.save(coordenada_trayecto);
        return coordenada_trayectoMapper.toDto(coordenada_trayecto);
    }

    /**
     * Get all the coordenada_trayectos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Coordenada_trayectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coordenada_trayectos");
        return coordenada_trayectoRepository.findAll(pageable)
            .map(coordenada_trayectoMapper::toDto);
    }

    /**
     * Get one coordenada_trayecto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Coordenada_trayectoDTO findOne(Long id) {
        log.debug("Request to get Coordenada_trayecto : {}", id);
        Coordenada_trayecto coordenada_trayecto = coordenada_trayectoRepository.findOne(id);
        return coordenada_trayectoMapper.toDto(coordenada_trayecto);
    }

    /**
     * Delete the coordenada_trayecto by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Coordenada_trayecto : {}", id);
        coordenada_trayectoRepository.delete(id);
    }
}
