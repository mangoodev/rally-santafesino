package com.rally.santafesino.service;

import com.rally.santafesino.domain.Etapa;
import com.rally.santafesino.domain.EtapaPrueba;
import com.rally.santafesino.domain.Trayecto;
import com.rally.santafesino.domain.TrayectoPrueba;
import com.rally.santafesino.repository.TrayectoPruebaRepository;
import com.rally.santafesino.service.dto.TrayectoDTO;
import com.rally.santafesino.service.dto.TrayectoPruebaDTO;
import com.rally.santafesino.service.mapper.TrayectoMapper;
import com.rally.santafesino.service.mapper.TrayectoPruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing TrayectoPrueba.
 */
@Service
@Transactional
public class TrayectoPruebaService {

    private final Logger log = LoggerFactory.getLogger(TrayectoPruebaService.class);

    private final TrayectoPruebaRepository trayectoPruebaRepository;

    private final TrayectoPruebaMapper trayectoPruebaMapper;

    private final TrayectoMapper trayectoMapper;

    public TrayectoPruebaService(TrayectoPruebaRepository trayectoPruebaRepository, TrayectoPruebaMapper trayectoPruebaMapper, TrayectoMapper trayectoMapper) {
        this.trayectoPruebaRepository = trayectoPruebaRepository;
        this.trayectoPruebaMapper = trayectoPruebaMapper;
        this.trayectoMapper = trayectoMapper;
    }

    /**
     * Save a trayectoPrueba.
     *
     * @param trayectoPruebaDTO the entity to save
     * @return the persisted entity
     */
    public TrayectoPruebaDTO save(TrayectoPruebaDTO trayectoPruebaDTO) {
        log.debug("Request to save TrayectoPrueba : {}", trayectoPruebaDTO);
        TrayectoPrueba trayectoPrueba = trayectoPruebaMapper.toEntity(trayectoPruebaDTO);
        trayectoPrueba = trayectoPruebaRepository.save(trayectoPrueba);
        return trayectoPruebaMapper.toDto(trayectoPrueba);
    }

    /**
     * Get all the trayectoPruebas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrayectoPruebaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrayectoPruebas");
        return trayectoPruebaRepository.findAll(pageable)
            .map(trayectoPruebaMapper::toDto);
    }

    /**
     * Get one trayectoPrueba by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TrayectoPruebaDTO findOne(Long id) {
        log.debug("Request to get TrayectoPrueba : {}", id);
        TrayectoPrueba trayectoPrueba = trayectoPruebaRepository.findOne(id);
        return trayectoPruebaMapper.toDto(trayectoPrueba);
    }

    /**
     * Delete the trayectoPrueba by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TrayectoPrueba : {}", id);
        trayectoPruebaRepository.delete(id);
    }

    public List<TrayectoDTO> findTrayectosByPruebas(Long pruebasId) {
        return trayectoPruebaRepository.findAllByPrueba(pruebasId)
            .stream()
            .map(TrayectoPrueba::getId_trayecto)
            .map(trayectoMapper::toDto)
            .collect(Collectors.toList());
    }
}
