package com.rally.santafesino.service;

import com.rally.santafesino.domain.CarreraClase;
import com.rally.santafesino.repository.CarreraClaseRepository;
import com.rally.santafesino.service.dto.CarreraClaseDTO;
import com.rally.santafesino.service.dto.CarreraDTO;
import com.rally.santafesino.service.mapper.CarreraClaseMapper;
import com.rally.santafesino.service.mapper.CarreraMapper;
import com.rally.santafesino.service.mapper.ClaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing CarreraClase.
 */
@Service
@Transactional
public class CarreraClaseService {

    private final Logger log = LoggerFactory.getLogger(CarreraClaseService.class);

    private final CarreraClaseRepository carreraClaseRepository;

    private final CarreraClaseMapper carreraClaseMapper;

    private final CarreraMapper carreraMapper;

    public CarreraClaseService(CarreraClaseRepository carreraClaseRepository, CarreraClaseMapper carreraClaseMapper, CarreraMapper carreraMapper) {
        this.carreraClaseRepository = carreraClaseRepository;
        this.carreraClaseMapper = carreraClaseMapper;
        this.carreraMapper = carreraMapper;
    }

    /**
     * Save a carreraClase.
     *
     * @param carreraClaseDTO the entity to save
     * @return the persisted entity
     */
    public CarreraClaseDTO save(CarreraClaseDTO carreraClaseDTO) {
        log.debug("Request to save CarreraClase : {}", carreraClaseDTO);
        CarreraClase carreraClase = carreraClaseMapper.toEntity(carreraClaseDTO);
        carreraClase = carreraClaseRepository.save(carreraClase);
        return carreraClaseMapper.toDto(carreraClase);
    }

    /**
     * Get all the carreraClases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CarreraClaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarreraClases");
        return carreraClaseRepository.findAll(pageable)
            .map(carreraClaseMapper::toDto);
    }

    /**
     * Get one carreraClase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CarreraClaseDTO findOne(Long id) {
        log.debug("Request to get CarreraClase : {}", id);
        CarreraClase carreraClase = carreraClaseRepository.findOne(id);
        return carreraClaseMapper.toDto(carreraClase);
    }

    /**
     * Delete the carreraClase by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CarreraClase : {}", id);
        carreraClaseRepository.delete(id);
    }

    public List<CarreraDTO> findCarrerasForClase(String claseId) {
        return carreraClaseRepository.findAllByClase_Nombre(claseId)
            .stream()
            .map(CarreraClase::getCarrera)
            .map(carreraMapper::toDto)
            .collect(Collectors.toList());
    }
}
