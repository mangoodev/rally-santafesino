package com.rally.santafesino.service;

import com.rally.santafesino.domain.Auto_carrear;
import com.rally.santafesino.repository.Auto_carrearRepository;
import com.rally.santafesino.service.dto.Auto_carrearDTO;
import com.rally.santafesino.service.mapper.Auto_carrearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Auto_carrear.
 */
@Service
@Transactional
public class Auto_carrearService {

    private final Logger log = LoggerFactory.getLogger(Auto_carrearService.class);

    private final Auto_carrearRepository auto_carrearRepository;

    private final Auto_carrearMapper auto_carrearMapper;

    public Auto_carrearService(Auto_carrearRepository auto_carrearRepository, Auto_carrearMapper auto_carrearMapper) {
        this.auto_carrearRepository = auto_carrearRepository;
        this.auto_carrearMapper = auto_carrearMapper;
    }

    /**
     * Save a auto_carrear.
     *
     * @param auto_carrearDTO the entity to save
     * @return the persisted entity
     */
    public Auto_carrearDTO save(Auto_carrearDTO auto_carrearDTO) {
        log.debug("Request to save Auto_carrear : {}", auto_carrearDTO);
        Auto_carrear auto_carrear = auto_carrearMapper.toEntity(auto_carrearDTO);
        auto_carrear = auto_carrearRepository.save(auto_carrear);
        return auto_carrearMapper.toDto(auto_carrear);
    }

    /**
     * Get all the auto_carrears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Auto_carrearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Auto_carrears");
        return auto_carrearRepository.findAll(pageable)
            .map(auto_carrearMapper::toDto);
    }

    /**
     * Get one auto_carrear by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Auto_carrearDTO findOne(Long id) {
        log.debug("Request to get Auto_carrear : {}", id);
        Auto_carrear auto_carrear = auto_carrearRepository.findOne(id);
        return auto_carrearMapper.toDto(auto_carrear);
    }

    /**
     * Delete the auto_carrear by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Auto_carrear : {}", id);
        auto_carrearRepository.delete(id);
    }
}
