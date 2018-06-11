package com.rally.santafesino.service;

import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.repository.CarreraRepository;
import com.rally.santafesino.service.dto.CarreraDTO;
import com.rally.santafesino.service.mapper.CarreraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Carrera.
 */
@Service
@Transactional
public class CarreraService {

    private final Logger log = LoggerFactory.getLogger(CarreraService.class);

    private final CarreraRepository carreraRepository;

    private final CarreraMapper carreraMapper;

    public CarreraService(CarreraRepository carreraRepository, CarreraMapper carreraMapper) {
        this.carreraRepository = carreraRepository;
        this.carreraMapper = carreraMapper;
    }

    /**
     * Save a carrera.
     *
     * @param carreraDTO the entity to save
     * @return the persisted entity
     */
    public CarreraDTO save(CarreraDTO carreraDTO) {
        log.debug("Request to save Carrera : {}", carreraDTO);
        Carrera carrera = carreraMapper.toEntity(carreraDTO);
        carrera = carreraRepository.save(carrera);
        return carreraMapper.toDto(carrera);
    }

    /**
     * Get all the carreras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CarreraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Carreras");
        return carreraRepository.findAll(pageable)
            .map(carreraMapper::toDto);
    }

    /**
     * Get one carrera by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CarreraDTO findOne(Long id) {
        log.debug("Request to get Carrera : {}", id);
        Carrera carrera = carreraRepository.findOne(id);
        return carreraMapper.toDto(carrera);
    }

    /**
     * Delete the carrera by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Carrera : {}", id);
        carreraRepository.delete(id);
    }
}
