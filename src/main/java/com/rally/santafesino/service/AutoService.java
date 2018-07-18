package com.rally.santafesino.service;

import com.rally.santafesino.domain.Auto;
import com.rally.santafesino.repository.AutoRepository;
import com.rally.santafesino.service.dto.AutoDTO;
import com.rally.santafesino.service.dto.CarreraDTO;
import com.rally.santafesino.service.mapper.AutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Auto.
 */
@Service
@Transactional
public class AutoService {

    private final Logger log = LoggerFactory.getLogger(AutoService.class);

    private final AutoRepository autoRepository;

    private final AutoMapper autoMapper;

    private final AutoCarreraService autoCarreraService;

    public AutoService(AutoRepository autoRepository, AutoMapper autoMapper, AutoCarreraService autoCarreraService) {
        this.autoRepository = autoRepository;
        this.autoMapper = autoMapper;
        this.autoCarreraService = autoCarreraService;
    }

    /**
     * Save a auto.
     *
     * @param autoDTO the entity to save
     * @return the persisted entity
     */
    public AutoDTO save(AutoDTO autoDTO) {
        log.debug("Request to save Auto : {}", autoDTO);
        Auto auto = autoMapper.toEntity(autoDTO);
        auto = autoRepository.save(auto);
        return autoMapper.toDto(auto);
    }

    /**
     * Get all the autos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Autos");
        return autoRepository.findAll(pageable)
            .map(autoMapper::toDto);
    }

    /**
     * Get one auto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AutoDTO findOne(Long id) {
        log.debug("Request to get Auto : {}", id);
        Auto auto = autoRepository.findOne(id);
        return autoMapper.toDto(auto);
    }

    /**
     * Delete the auto by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Auto : {}", id);
        autoRepository.delete(id);
    }

    public List<AutoDTO> findByPersona(Long personaId) {
        log.debug("Request to find Persona by Auto : {}", personaId);
        List<Auto> autos = autoRepository.findAutoByPiloto_Id(personaId);
        autos.addAll(autoRepository.findAutoByCopiloto_Id(personaId));

        return autos.stream()
            .map(autoMapper::toDto)
            .collect(Collectors.toList());
    }

    public String findClaseByAuto(Long autoId) {
        return findOne(autoId).getClase();//TODO do in SQL query
    }

    public Set<CarreraDTO> findHistorialForAuto(Long autoId, ZonedDateTime fechaActual) {
        return autoCarreraService.findCarrerasByAuto(autoId)
            .stream()
            .filter(carreraDTO -> carreraDTO.getFecha().isBefore(fechaActual))
            .collect(Collectors.toSet());
    }
}
