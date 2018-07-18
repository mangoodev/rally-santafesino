package com.rally.santafesino.service;

import com.rally.santafesino.domain.CoordenadaTrayecto;
import com.rally.santafesino.repository.CoordenadaTrayectoRepository;
import com.rally.santafesino.service.dto.CoordenadaTrayectoDTO;
import com.rally.santafesino.service.dto.CoordenadasDTO;
import com.rally.santafesino.service.mapper.CoordenadaTrayectoMapper;
import com.rally.santafesino.service.mapper.CoordenadasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing CoordenadaTrayecto.
 */
@Service
@Transactional
public class CoordenadaTrayectoService {

    private final Logger log = LoggerFactory.getLogger(CoordenadaTrayectoService.class);

    private final CoordenadaTrayectoRepository coordenadaTrayectoRepository;

    private final CoordenadaTrayectoMapper coordenadaTrayectoMapper;

    private final CoordenadasMapper coordenadasMapper;

    public CoordenadaTrayectoService(CoordenadaTrayectoRepository coordenadaTrayectoRepository, CoordenadaTrayectoMapper coordenadaTrayectoMapper, CoordenadasMapper coordenadasMapper) {
        this.coordenadaTrayectoRepository = coordenadaTrayectoRepository;
        this.coordenadaTrayectoMapper = coordenadaTrayectoMapper;
        this.coordenadasMapper = coordenadasMapper;
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

    public List<CoordenadasDTO> findCoordenadasByTrayecto(Long trayectoId) {
        return coordenadaTrayectoRepository.findAllByTrayecto(trayectoId)
            .stream()
            .map(CoordenadaTrayecto::getId_coordenadas)
            .map(coordenadasMapper::toDto)
            .collect(Collectors.toList());
    }
}
