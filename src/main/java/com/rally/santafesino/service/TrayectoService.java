package com.rally.santafesino.service;

import com.rally.santafesino.domain.Trayecto;
import com.rally.santafesino.repository.TrayectoRepository;
import com.rally.santafesino.service.dto.TrayectoDTO;
import com.rally.santafesino.service.mapper.TrayectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Trayecto.
 */
@Service
@Transactional
public class TrayectoService {

    private final Logger log = LoggerFactory.getLogger(TrayectoService.class);

    private final TrayectoRepository trayectoRepository;

    private final TrayectoMapper trayectoMapper;

    public TrayectoService(TrayectoRepository trayectoRepository, TrayectoMapper trayectoMapper) {
        this.trayectoRepository = trayectoRepository;
        this.trayectoMapper = trayectoMapper;
    }

    /**
     * Save a trayecto.
     *
     * @param trayectoDTO the entity to save
     * @return the persisted entity
     */
    public TrayectoDTO save(TrayectoDTO trayectoDTO) {
        log.debug("Request to save Trayecto : {}", trayectoDTO);
        Trayecto trayecto = trayectoMapper.toEntity(trayectoDTO);
        trayecto = trayectoRepository.save(trayecto);
        return trayectoMapper.toDto(trayecto);
    }

    /**
     * Get all the trayectos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrayectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trayectos");
        return trayectoRepository.findAll(pageable)
            .map(trayectoMapper::toDto);
    }

    /**
     * Get one trayecto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TrayectoDTO findOne(Long id) {
        log.debug("Request to get Trayecto : {}", id);
        Trayecto trayecto = trayectoRepository.findOne(id);
        return trayectoMapper.toDto(trayecto);
    }

    /**
     * Delete the trayecto by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Trayecto : {}", id);
        trayectoRepository.delete(id);
    }
}
