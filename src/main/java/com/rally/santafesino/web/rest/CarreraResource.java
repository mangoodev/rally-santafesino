package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.AutoCarreraService;
import com.rally.santafesino.service.CarreraService;
import com.rally.santafesino.service.dto.AutoCarreraDTO;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.CarreraDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.time.ZoneId;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Carrera.
 */
@RestController
@RequestMapping("/api")
public class CarreraResource {

    private final Logger log = LoggerFactory.getLogger(CarreraResource.class);

    private static final String ENTITY_NAME = "carrera";

    private final CarreraService carreraService;

    public CarreraResource(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    /**
     * POST  /carreras : Create a new carrera.
     *
     * @param carreraDTO the carreraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carreraDTO, or with status 400 (Bad Request) if the carrera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carreras")
    @Timed
    public ResponseEntity<CarreraDTO> createCarrera(@Valid @RequestBody CarreraDTO carreraDTO) throws URISyntaxException {
        log.debug("REST request to save Carrera : {}", carreraDTO);
        if (carreraDTO.getId() != null) {
            throw new BadRequestAlertException("A new carrera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarreraDTO result = carreraService.save(carreraDTO);
        return ResponseEntity.created(new URI("/api/carreras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carreras : Updates an existing carrera.
     *
     * @param carreraDTO the carreraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carreraDTO,
     * or with status 400 (Bad Request) if the carreraDTO is not valid,
     * or with status 500 (Internal Server Error) if the carreraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carreras")
    @Timed
    public ResponseEntity<CarreraDTO> updateCarrera(@Valid @RequestBody CarreraDTO carreraDTO) throws URISyntaxException {
        log.debug("REST request to update Carrera : {}", carreraDTO);
        if (carreraDTO.getId() == null) {
            return createCarrera(carreraDTO);
        }
        CarreraDTO result = carreraService.save(carreraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carreraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carreras : get all the carreras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carreras in body
     */
    @GetMapping("/carreras")
    @Timed
    public ResponseEntity<List<CarreraDTO>> getAllCarreras(Pageable pageable) {
        log.debug("REST request to get a page of Carreras");
        Page<CarreraDTO> page = carreraService.findAll(pageable);

        List<CarreraDTO> dd = carreraService.findAll();


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carreras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carreras/:id : get the "id" carrera.
     *
     * @param id the id of the carreraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carreraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carreras/{id}")
    @Timed
    public ResponseEntity<CarreraDTO> getCarrera(@PathVariable Long id) {
        log.debug("REST request to get Carrera : {}", id);
        CarreraDTO carreraDTO = carreraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carreraDTO));
    }

    /**
     * DELETE  /carreras/:id : delete the "id" carrera.
     *
     * @param id the id of the carreraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carreras/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarrera(@PathVariable Long id) {
        log.debug("REST request to delete Carrera : {}", id);
        carreraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/carreras/getCarrerasDisponibles/{autoId}")
    @Timed
    public ResponseEntity<List<CarreraDTO>> getCarrerasDisponibles(@PathVariable Long autoId) {
        log.debug("REST request to get Auto for Persona : {}", autoId);
        List<CarreraDTO> carreraDTOs = carreraService.findCarrerasDisponibles(autoId, ZonedDateTime.now());
        return ResponseEntity.ok().body(carreraDTOs);
    }

    @PostMapping("/carreras/byFecha")
    @Timed
    public ResponseEntity<List<CarreraDTO>> getCarreraByFecha(@RequestBody ZonedDateTime a) {
        log.debug("REST request to lalalallalaa Carrera : {}", a);
        if(a == null) {
            new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
        List<CarreraDTO> carreraDTOs = carreraService.getCarreraByFecha(ZonedDateTime.now());
        return new ResponseEntity<>(carreraDTOs, null, HttpStatus.OK);
    }
    @GetMapping("/carreras/detalle/{id}")
    @Timed
    public ResponseEntity<CarreraDTO> getDetalleCarrera(@PathVariable Long id) {
        log.debug("REST request to lalalallalaa Carrera : {}");

        CarreraDTO carrera = carreraService.findCarreraWithAutoById(id);
        return new ResponseEntity<>(carrera, null, HttpStatus.OK);
    }
}
