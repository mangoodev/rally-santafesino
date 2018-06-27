package com.rally.santafesino.service;

import com.rally.santafesino.domain.CarreraEtapa;
import com.rally.santafesino.repository.CarreraEtapaRepository;
import com.rally.santafesino.service.dto.CarreraEtapaDTO;
import com.rally.santafesino.service.mapper.CarreraEtapaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CarreraEtapa.
 */
@Service
@Transactional
public class CarreraEtapaService {

    private final Logger log = LoggerFactory.getLogger(CarreraEtapaService.class);

    private final CarreraEtapaRepository carreraEtapaRepository;

    private final CarreraEtapaMapper carreraEtapaMapper;

    public CarreraEtapaService(CarreraEtapaRepository carreraEtapaRepository, CarreraEtapaMapper carreraEtapaMapper) {
        this.carreraEtapaRepository = carreraEtapaRepository;
        this.carreraEtapaMapper = carreraEtapaMapper;
    }

    /**
     * Save a carreraEtapa.
     *
     * @param carreraEtapaDTO the entity to save
     * @return the persisted entity
     */
    public CarreraEtapaDTO save(CarreraEtapaDTO carreraEtapaDTO) {
        log.debug("Request to save CarreraEtapa : {}", carreraEtapaDTO);
        CarreraEtapa carreraEtapa = carreraEtapaMapper.toEntity(carreraEtapaDTO);
        carreraEtapa = carreraEtapaRepository.save(carreraEtapa);
        return carreraEtapaMapper.toDto(carreraEtapa);
    }

    /**
     * Get all the carreraEtapas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CarreraEtapaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarreraEtapas");
        return carreraEtapaRepository.findAll(pageable)
            .map(carreraEtapaMapper::toDto);
    }

    /**
     * Get one carreraEtapa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CarreraEtapaDTO findOne(Long id) {
        log.debug("Request to get CarreraEtapa : {}", id);
        CarreraEtapa carreraEtapa = carreraEtapaRepository.findOne(id);
        return carreraEtapaMapper.toDto(carreraEtapa);
    }

    /**
     * Delete the carreraEtapa by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CarreraEtapa : {}", id);
        carreraEtapaRepository.delete(id);
    }
}
