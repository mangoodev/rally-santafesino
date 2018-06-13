package com.rally.santafesino.service;

import com.rally.santafesino.domain.Localidad_carrera;
import com.rally.santafesino.repository.Localidad_carreraRepository;
import com.rally.santafesino.service.dto.Localidad_carreraDTO;
import com.rally.santafesino.service.mapper.Localidad_carreraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Localidad_carrera.
 */
@Service
@Transactional
public class Localidad_carreraService {

    private final Logger log = LoggerFactory.getLogger(Localidad_carreraService.class);

    private final Localidad_carreraRepository localidad_carreraRepository;

    private final Localidad_carreraMapper localidad_carreraMapper;

    public Localidad_carreraService(Localidad_carreraRepository localidad_carreraRepository, Localidad_carreraMapper localidad_carreraMapper) {
        this.localidad_carreraRepository = localidad_carreraRepository;
        this.localidad_carreraMapper = localidad_carreraMapper;
    }

    /**
     * Save a localidad_carrera.
     *
     * @param localidad_carreraDTO the entity to save
     * @return the persisted entity
     */
    public Localidad_carreraDTO save(Localidad_carreraDTO localidad_carreraDTO) {
        log.debug("Request to save Localidad_carrera : {}", localidad_carreraDTO);
        Localidad_carrera localidad_carrera = localidad_carreraMapper.toEntity(localidad_carreraDTO);
        localidad_carrera = localidad_carreraRepository.save(localidad_carrera);
        return localidad_carreraMapper.toDto(localidad_carrera);
    }

    /**
     * Get all the localidad_carreras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Localidad_carreraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Localidad_carreras");
        return localidad_carreraRepository.findAll(pageable)
            .map(localidad_carreraMapper::toDto);
    }

    /**
     * Get one localidad_carrera by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Localidad_carreraDTO findOne(Long id) {
        log.debug("Request to get Localidad_carrera : {}", id);
        Localidad_carrera localidad_carrera = localidad_carreraRepository.findOne(id);
        return localidad_carreraMapper.toDto(localidad_carrera);
    }

    /**
     * Delete the localidad_carrera by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Localidad_carrera : {}", id);
        localidad_carreraRepository.delete(id);
    }
}
