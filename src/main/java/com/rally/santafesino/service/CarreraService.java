package com.rally.santafesino.service;

import com.rally.santafesino.domain.AutoCarrera;
import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.repository.AutoCarreraRepository;
import com.rally.santafesino.repository.CarreraRepository;
import com.rally.santafesino.service.dto.AutoCarreraDTO;
import com.rally.santafesino.service.dto.CarreraDTO;
import com.rally.santafesino.service.mapper.AutoMapper;
import com.rally.santafesino.service.mapper.CarreraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Carrera.
 */


@Service
@Transactional
public class CarreraService {

    private final Logger log = LoggerFactory.getLogger(CarreraService.class);

    private final CarreraRepository carreraRepository;

    private final CarreraMapper carreraMapper;

    private final AutoService autoService;

    private final CarreraClaseService carreraClaseService;

    private final AutoMapper autoMapper;

    private final AutoCarreraRepository autoCarreraRepository;

    public CarreraService(CarreraRepository carreraRepository, CarreraMapper carreraMapper, AutoService autoService, CarreraClaseService carreraClaseService, AutoCarreraRepository autoCarreraRepository, AutoMapper autoMapper) {
        this.carreraRepository = carreraRepository;
        this.carreraMapper = carreraMapper;
        this.autoService = autoService;
        this.carreraClaseService = carreraClaseService;
        this.autoCarreraRepository = autoCarreraRepository;
        this.autoMapper = autoMapper;
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

    @Transactional(readOnly = true)
    public List<CarreraDTO> findAll() {
        log.debug("Request to get all Carreras");
        List<CarreraDTO> carreraDTOs = carreraMapper.toDto(carreraRepository.findAll());
        return carreraDTOs;
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

    public List<CarreraDTO> findCarrerasDisponibles(Long autoId, ZonedDateTime fechaActual) {
        return carreraClaseService.findCarrerasForClase(autoService.findClaseByAuto(autoId))
            .stream()
            .filter((carreraDTO) -> carreraDTO.getInicioInscripcion().isBefore(fechaActual))
            .filter((carreraDTO) -> carreraDTO.getFinalInscripcion().isAfter(fechaActual))
            .collect(Collectors.toList());
    }

    public List<CarreraDTO> getCarreraByFecha(ZonedDateTime fecha) {
        ZonedDateTime comienzoDia = fecha.truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime finalDia = comienzoDia.plus(Duration.ofDays(1));
        List<CarreraDTO> carreraDTOs;
        List<Carrera> carreras = carreraRepository.findByFechaAfterAndFechaBeforeOrderByFechaAsc(comienzoDia,finalDia);
        carreraDTOs = carreraMapper.toDto(carreras);
        return carreraDTOs;
    }

    public CarreraDTO findCarreraWithAutoById(Long id) {
        Carrera carrera = carreraRepository.findOne(id);
        return toCarreraDtoWithAutos(carrera);
    }

    private CarreraDTO toCarreraDtoWithAutos(Carrera carrera) {
        CarreraDTO carreraDto = carreraMapper.toDto(carrera);
        List<AutoCarrera> autoCarreras = autoCarreraRepository.findAllByCarrera_Id(carrera.getId());
        carreraDto.setAutos(autoCarreras.stream().map(AutoCarrera::getAuto).map(autoMapper::toDto).collect(Collectors.toList()));
        return carreraDto;
    }

    public List<CarreraDTO> findCarreras(List<AutoCarreraDTO> autoCarreras) {
        Set<Carrera> historial = new HashSet<Carrera>();

        for(AutoCarreraDTO autoCarreraDTO : autoCarreras){
            historial.add(carreraRepository.findOne(autoCarreraDTO.getCarreraId()));
        }
        List<CarreraDTO> historialDTO = historial.stream().map(this::toCarreraDtoWithAutos).collect(Collectors.toList());
        return historialDTO;
    }
}
