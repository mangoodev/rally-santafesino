package com.rally.santafesino.service;

import com.rally.santafesino.domain.Clase;
import com.rally.santafesino.repository.ClaseRepository;
import com.rally.santafesino.service.dto.ClaseDTO;
import com.rally.santafesino.service.mapper.ClaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Clase.
 */
@Service
@Transactional
public class ClaseService {

    private final Logger log = LoggerFactory.getLogger(ClaseService.class);

    private final ClaseRepository claseRepository;

    private final ClaseMapper claseMapper;

    public ClaseService(ClaseRepository claseRepository, ClaseMapper claseMapper) {
        this.claseRepository = claseRepository;
        this.claseMapper = claseMapper;
    }

    /**
     * Save a clase.
     *
     * @param claseDTO the entity to save
     * @return the persisted entity
     */
    public ClaseDTO save(ClaseDTO claseDTO) {
        log.debug("Request to save Clase : {}", claseDTO);
        Clase clase = claseMapper.toEntity(claseDTO);
        clase = claseRepository.save(clase);
        return claseMapper.toDto(clase);
    }

    /**
     * Get all the clases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clases");
        return claseRepository.findAll(pageable)
            .map(claseMapper::toDto);
    }

    /**
     * Get one clase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ClaseDTO findOne(Long id) {
        log.debug("Request to get Clase : {}", id);
        Clase clase = claseRepository.findOne(id);
        return claseMapper.toDto(clase);
    }

    /**
     * Delete the clase by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Clase : {}", id);
        claseRepository.delete(id);
    }
}
