package com.rally.santafesino.service;

import com.rally.santafesino.domain.Localidad;
import com.rally.santafesino.repository.LocalidadRepository;
import com.rally.santafesino.service.dto.LocalidadDTO;
import com.rally.santafesino.service.mapper.LocalidadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Localidad.
 */
@Service
@Transactional
public class LocalidadService {

    private final Logger log = LoggerFactory.getLogger(LocalidadService.class);

    private final LocalidadRepository localidadRepository;

    private final LocalidadMapper localidadMapper;

    public LocalidadService(LocalidadRepository localidadRepository, LocalidadMapper localidadMapper) {
        this.localidadRepository = localidadRepository;
        this.localidadMapper = localidadMapper;
    }

    /**
     * Save a localidad.
     *
     * @param localidadDTO the entity to save
     * @return the persisted entity
     */
    public LocalidadDTO save(LocalidadDTO localidadDTO) {
        log.debug("Request to save Localidad : {}", localidadDTO);
        Localidad localidad = localidadMapper.toEntity(localidadDTO);
        localidad = localidadRepository.save(localidad);
        return localidadMapper.toDto(localidad);
    }

    /**
     * Get all the localidads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LocalidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Localidads");
        return localidadRepository.findAll(pageable)
            .map(localidadMapper::toDto);
    }

    /**
     * Get one localidad by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public LocalidadDTO findOne(Long id) {
        log.debug("Request to get Localidad : {}", id);
        Localidad localidad = localidadRepository.findOne(id);
        return localidadMapper.toDto(localidad);
    }

    /**
     * Delete the localidad by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Localidad : {}", id);
        localidadRepository.delete(id);
    }
}
