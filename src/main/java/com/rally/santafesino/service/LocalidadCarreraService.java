package com.rally.santafesino.service;

import com.rally.santafesino.domain.LocalidadCarrera;
import com.rally.santafesino.repository.LocalidadCarreraRepository;
import com.rally.santafesino.service.dto.LocalidadCarreraDTO;
import com.rally.santafesino.service.mapper.LocalidadCarreraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing LocalidadCarrera.
 */
@Service
@Transactional
public class LocalidadCarreraService {

    private final Logger log = LoggerFactory.getLogger(LocalidadCarreraService.class);

    private final LocalidadCarreraRepository localidadCarreraRepository;

    private final LocalidadCarreraMapper localidadCarreraMapper;

    public LocalidadCarreraService(LocalidadCarreraRepository localidadCarreraRepository, LocalidadCarreraMapper localidadCarreraMapper) {
        this.localidadCarreraRepository = localidadCarreraRepository;
        this.localidadCarreraMapper = localidadCarreraMapper;
    }

    /**
     * Save a localidadCarrera.
     *
     * @param localidadCarreraDTO the entity to save
     * @return the persisted entity
     */
    public LocalidadCarreraDTO save(LocalidadCarreraDTO localidadCarreraDTO) {
        log.debug("Request to save LocalidadCarrera : {}", localidadCarreraDTO);
        LocalidadCarrera localidadCarrera = localidadCarreraMapper.toEntity(localidadCarreraDTO);
        localidadCarrera = localidadCarreraRepository.save(localidadCarrera);
        return localidadCarreraMapper.toDto(localidadCarrera);
    }

    /**
     * Get all the localidadCarreras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LocalidadCarreraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocalidadCarreras");
        return localidadCarreraRepository.findAll(pageable)
            .map(localidadCarreraMapper::toDto);
    }

    /**
     * Get one localidadCarrera by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public LocalidadCarreraDTO findOne(Long id) {
        log.debug("Request to get LocalidadCarrera : {}", id);
        LocalidadCarrera localidadCarrera = localidadCarreraRepository.findOne(id);
        return localidadCarreraMapper.toDto(localidadCarrera);
    }

    /**
     * Delete the localidadCarrera by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LocalidadCarrera : {}", id);
        localidadCarreraRepository.delete(id);
    }
}
