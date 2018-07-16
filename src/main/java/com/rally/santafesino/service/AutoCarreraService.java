package com.rally.santafesino.service;

import com.rally.santafesino.domain.AutoCarrera;
import com.rally.santafesino.repository.AutoCarreraRepository;
import com.rally.santafesino.service.dto.AutoCarreraDTO;
import com.rally.santafesino.service.dto.CarreraDTO;
import com.rally.santafesino.service.dto.AutoDTO;
import com.rally.santafesino.service.mapper.AutoCarreraMapper;
import com.rally.santafesino.service.mapper.CarreraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing AutoCarrera.
 */
@Service
@Transactional
public class AutoCarreraService {

    private final Logger log = LoggerFactory.getLogger(AutoCarreraService.class);

    private final AutoCarreraRepository autoCarreraRepository;

    private final AutoCarreraMapper autoCarreraMapper;

    private final CarreraMapper carreraMapper;

    public AutoCarreraService(AutoCarreraRepository autoCarreraRepository, AutoCarreraMapper autoCarreraMapper, CarreraMapper carreraMapper) {
        this.autoCarreraRepository = autoCarreraRepository;
        this.autoCarreraMapper = autoCarreraMapper;
        this.carreraMapper = carreraMapper;
    }

    /**
     * Save a autoCarrera.
     *
     * @param autoCarreraDTO the entity to save
     * @return the persisted entity
     */
    public AutoCarreraDTO save(AutoCarreraDTO autoCarreraDTO) {
        log.debug("Request to save AutoCarrera : {}", autoCarreraDTO);
        AutoCarrera autoCarrera = autoCarreraMapper.toEntity(autoCarreraDTO);
        autoCarrera = autoCarreraRepository.save(autoCarrera);
        return autoCarreraMapper.toDto(autoCarrera);
    }

    /**
     * Get all the autoCarreras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AutoCarreraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutoCarreras");
        return autoCarreraRepository.findAll(pageable)
            .map(autoCarreraMapper::toDto);
    }

    /**
     * Get one autoCarrera by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AutoCarreraDTO findOne(Long id) {
        log.debug("Request to get AutoCarrera : {}", id);
        AutoCarrera autoCarrera = autoCarreraRepository.findOne(id);
        return autoCarreraMapper.toDto(autoCarrera);
    }

    /**
     * Delete the autoCarrera by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AutoCarrera : {}", id);
        autoCarreraRepository.delete(id);
    }

    public List<CarreraDTO> findCarrerasByAuto(Long autoId) {
        return autoCarreraRepository.findAutoCarreraByAuto_Id(autoId)
            .stream()
            .map(AutoCarrera::getCarrera)
            .map(carreraMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<AutoCarreraDTO> getAutosQueCorren(long id){
        List<AutoCarrera> autos = autoCarreraRepository.findAllByCarrera_Id(id);
        return autos.stream().map(autoCarreraMapper::toDto).collect(Collectors.toList());
    }

    public List<AutoCarreraDTO> findByIdsAutos(List<AutoDTO> autosDeLaPersona) {
        List<AutoCarreraDTO> autoCarreras = null;
        for(AutoDTO autoDTO : autosDeLaPersona){
            autoCarreras.addAll(autoCarreraRepository.findAllByAuto_Id(autoDTO.getId()).stream().map(autoCarreraMapper::toDto).collect(Collectors.toList()));
        }
        return autoCarreras;
    }
}
