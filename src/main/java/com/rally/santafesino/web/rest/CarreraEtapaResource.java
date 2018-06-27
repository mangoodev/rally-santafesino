package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.CarreraEtapaService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.CarreraEtapaDTO;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CarreraEtapa.
 */
@RestController
@RequestMapping("/api")
public class CarreraEtapaResource {

    private final Logger log = LoggerFactory.getLogger(CarreraEtapaResource.class);

    private static final String ENTITY_NAME = "carreraEtapa";

    private final CarreraEtapaService carreraEtapaService;

    public CarreraEtapaResource(CarreraEtapaService carreraEtapaService) {
        this.carreraEtapaService = carreraEtapaService;
    }

    /**
     * POST  /carrera-etapas : Create a new carreraEtapa.
     *
     * @param carreraEtapaDTO the carreraEtapaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carreraEtapaDTO, or with status 400 (Bad Request) if the carreraEtapa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carrera-etapas")
    @Timed
    public ResponseEntity<CarreraEtapaDTO> createCarreraEtapa(@Valid @RequestBody CarreraEtapaDTO carreraEtapaDTO) throws URISyntaxException {
        log.debug("REST request to save CarreraEtapa : {}", carreraEtapaDTO);
        if (carreraEtapaDTO.getId() != null) {
            throw new BadRequestAlertException("A new carreraEtapa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarreraEtapaDTO result = carreraEtapaService.save(carreraEtapaDTO);
        return ResponseEntity.created(new URI("/api/carrera-etapas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carrera-etapas : Updates an existing carreraEtapa.
     *
     * @param carreraEtapaDTO the carreraEtapaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carreraEtapaDTO,
     * or with status 400 (Bad Request) if the carreraEtapaDTO is not valid,
     * or with status 500 (Internal Server Error) if the carreraEtapaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carrera-etapas")
    @Timed
    public ResponseEntity<CarreraEtapaDTO> updateCarreraEtapa(@Valid @RequestBody CarreraEtapaDTO carreraEtapaDTO) throws URISyntaxException {
        log.debug("REST request to update CarreraEtapa : {}", carreraEtapaDTO);
        if (carreraEtapaDTO.getId() == null) {
            return createCarreraEtapa(carreraEtapaDTO);
        }
        CarreraEtapaDTO result = carreraEtapaService.save(carreraEtapaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carreraEtapaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carrera-etapas : get all the carreraEtapas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carreraEtapas in body
     */
    @GetMapping("/carrera-etapas")
    @Timed
    public ResponseEntity<List<CarreraEtapaDTO>> getAllCarreraEtapas(Pageable pageable) {
        log.debug("REST request to get a page of CarreraEtapas");
        Page<CarreraEtapaDTO> page = carreraEtapaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carrera-etapas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carrera-etapas/:id : get the "id" carreraEtapa.
     *
     * @param id the id of the carreraEtapaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carreraEtapaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carrera-etapas/{id}")
    @Timed
    public ResponseEntity<CarreraEtapaDTO> getCarreraEtapa(@PathVariable Long id) {
        log.debug("REST request to get CarreraEtapa : {}", id);
        CarreraEtapaDTO carreraEtapaDTO = carreraEtapaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carreraEtapaDTO));
    }

    /**
     * DELETE  /carrera-etapas/:id : delete the "id" carreraEtapa.
     *
     * @param id the id of the carreraEtapaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carrera-etapas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarreraEtapa(@PathVariable Long id) {
        log.debug("REST request to delete CarreraEtapa : {}", id);
        carreraEtapaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
