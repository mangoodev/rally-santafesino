package com.rally.santafesino.service;

import com.rally.santafesino.domain.Etapa;
import com.rally.santafesino.repository.EtapaRepository;
import com.rally.santafesino.service.dto.EtapaDTO;
import com.rally.santafesino.service.mapper.EtapaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Etapa.
 */
@Service
@Transactional
public class EtapaService {

    private final Logger log = LoggerFactory.getLogger(EtapaService.class);

    private final EtapaRepository etapaRepository;

    private final EtapaMapper etapaMapper;

    public EtapaService(EtapaRepository etapaRepository, EtapaMapper etapaMapper) {
        this.etapaRepository = etapaRepository;
        this.etapaMapper = etapaMapper;
    }

    /**
     * Save a etapa.
     *
     * @param etapaDTO the entity to save
     * @return the persisted entity
     */
    public EtapaDTO save(EtapaDTO etapaDTO) {
        log.debug("Request to save Etapa : {}", etapaDTO);
        Etapa etapa = etapaMapper.toEntity(etapaDTO);
        etapa = etapaRepository.save(etapa);
        return etapaMapper.toDto(etapa);
    }

    /**
     * Get all the etapas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EtapaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Etapas");
        return etapaRepository.findAll(pageable)
            .map(etapaMapper::toDto);
    }

    /**
     * Get one etapa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EtapaDTO findOne(Long id) {
        log.debug("Request to get Etapa : {}", id);
        Etapa etapa = etapaRepository.findOne(id);
        return etapaMapper.toDto(etapa);
    }

    /**
     * Delete the etapa by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Etapa : {}", id);
        etapaRepository.delete(id);
    }
}
