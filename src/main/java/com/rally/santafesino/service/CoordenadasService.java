package com.rally.santafesino.service;

import com.rally.santafesino.domain.Coordenadas;
import com.rally.santafesino.repository.CoordenadasRepository;
import com.rally.santafesino.service.dto.CoordenadasDTO;
import com.rally.santafesino.service.mapper.CoordenadasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Coordenadas.
 */
@Service
@Transactional
public class CoordenadasService {

    private final Logger log = LoggerFactory.getLogger(CoordenadasService.class);

    private final CoordenadasRepository coordenadasRepository;

    private final CoordenadasMapper coordenadasMapper;

    public CoordenadasService(CoordenadasRepository coordenadasRepository, CoordenadasMapper coordenadasMapper) {
        this.coordenadasRepository = coordenadasRepository;
        this.coordenadasMapper = coordenadasMapper;
    }

    /**
     * Save a coordenadas.
     *
     * @param coordenadasDTO the entity to save
     * @return the persisted entity
     */
    public CoordenadasDTO save(CoordenadasDTO coordenadasDTO) {
        log.debug("Request to save Coordenadas : {}", coordenadasDTO);
        Coordenadas coordenadas = coordenadasMapper.toEntity(coordenadasDTO);
        coordenadas = coordenadasRepository.save(coordenadas);
        return coordenadasMapper.toDto(coordenadas);
    }

    /**
     * Get all the coordenadas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CoordenadasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coordenadas");
        return coordenadasRepository.findAll(pageable)
            .map(coordenadasMapper::toDto);
    }

    /**
     * Get one coordenadas by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CoordenadasDTO findOne(Long id) {
        log.debug("Request to get Coordenadas : {}", id);
        Coordenadas coordenadas = coordenadasRepository.findOne(id);
        return coordenadasMapper.toDto(coordenadas);
    }

    /**
     * Delete the coordenadas by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Coordenadas : {}", id);
        coordenadasRepository.delete(id);
    }
}
