package com.rally.santafesino.service;

import com.rally.santafesino.domain.EtapaPrueba;
import com.rally.santafesino.repository.EtapaPruebaRepository;
import com.rally.santafesino.service.dto.EtapaDTO;
import com.rally.santafesino.service.dto.EtapaPruebaDTO;
import com.rally.santafesino.service.dto.PruebasDTO;
import com.rally.santafesino.service.mapper.EtapaPruebaMapper;
import com.rally.santafesino.service.mapper.PruebasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing EtapaPrueba.
 */
@Service
@Transactional
public class EtapaPruebaService {

    private final Logger log = LoggerFactory.getLogger(EtapaPruebaService.class);

    private final EtapaPruebaRepository etapaPruebaRepository;

    private final EtapaPruebaMapper etapaPruebaMapper;

    private final PruebasMapper pruebasMapper;

    public EtapaPruebaService(EtapaPruebaRepository etapaPruebaRepository, EtapaPruebaMapper etapaPruebaMapper, PruebasMapper pruebasMapper) {
        this.etapaPruebaRepository = etapaPruebaRepository;
        this.etapaPruebaMapper = etapaPruebaMapper;
        this.pruebasMapper = pruebasMapper;
    }

    /**
     * Save a etapaPrueba.
     *
     * @param etapaPruebaDTO the entity to save
     * @return the persisted entity
     */
    public EtapaPruebaDTO save(EtapaPruebaDTO etapaPruebaDTO) {
        log.debug("Request to save EtapaPrueba : {}", etapaPruebaDTO);
        EtapaPrueba etapaPrueba = etapaPruebaMapper.toEntity(etapaPruebaDTO);
        etapaPrueba = etapaPruebaRepository.save(etapaPrueba);
        return etapaPruebaMapper.toDto(etapaPrueba);
    }

    /**
     * Get all the etapaPruebas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EtapaPruebaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EtapaPruebas");
        return etapaPruebaRepository.findAll(pageable)
            .map(etapaPruebaMapper::toDto);
    }

    /**
     * Get one etapaPrueba by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EtapaPruebaDTO findOne(Long id) {
        log.debug("Request to get EtapaPrueba : {}", id);
        EtapaPrueba etapaPrueba = etapaPruebaRepository.findOne(id);
        return etapaPruebaMapper.toDto(etapaPrueba);
    }

    /**
     * Delete the etapaPrueba by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EtapaPrueba : {}", id);
        etapaPruebaRepository.delete(id);
    }

    public List<PruebasDTO> findPruebasByEtapa(Long etapaId) {
        return etapaPruebaRepository.findAllByEtapa(etapaId)
            .stream()
            .map(EtapaPrueba::getId_prueba)
            .map(pruebasMapper::toDto)
            .collect(Collectors.toList());
    }
}
