package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.Carrera_etapaService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.Carrera_etapaDTO;
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
 * REST controller for managing Carrera_etapa.
 */
@RestController
@RequestMapping("/api")
public class Carrera_etapaResource {

    private final Logger log = LoggerFactory.getLogger(Carrera_etapaResource.class);

    private static final String ENTITY_NAME = "carrera_etapa";

    private final Carrera_etapaService carrera_etapaService;

    public Carrera_etapaResource(Carrera_etapaService carrera_etapaService) {
        this.carrera_etapaService = carrera_etapaService;
    }

    /**
     * POST  /carrera-etapas : Create a new carrera_etapa.
     *
     * @param carrera_etapaDTO the carrera_etapaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carrera_etapaDTO, or with status 400 (Bad Request) if the carrera_etapa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carrera-etapas")
    @Timed
    public ResponseEntity<Carrera_etapaDTO> createCarrera_etapa(@Valid @RequestBody Carrera_etapaDTO carrera_etapaDTO) throws URISyntaxException {
        log.debug("REST request to save Carrera_etapa : {}", carrera_etapaDTO);
        if (carrera_etapaDTO.getId() != null) {
            throw new BadRequestAlertException("A new carrera_etapa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Carrera_etapaDTO result = carrera_etapaService.save(carrera_etapaDTO);
        return ResponseEntity.created(new URI("/api/carrera-etapas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carrera-etapas : Updates an existing carrera_etapa.
     *
     * @param carrera_etapaDTO the carrera_etapaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carrera_etapaDTO,
     * or with status 400 (Bad Request) if the carrera_etapaDTO is not valid,
     * or with status 500 (Internal Server Error) if the carrera_etapaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carrera-etapas")
    @Timed
    public ResponseEntity<Carrera_etapaDTO> updateCarrera_etapa(@Valid @RequestBody Carrera_etapaDTO carrera_etapaDTO) throws URISyntaxException {
        log.debug("REST request to update Carrera_etapa : {}", carrera_etapaDTO);
        if (carrera_etapaDTO.getId() == null) {
            return createCarrera_etapa(carrera_etapaDTO);
        }
        Carrera_etapaDTO result = carrera_etapaService.save(carrera_etapaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carrera_etapaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carrera-etapas : get all the carrera_etapas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carrera_etapas in body
     */
    @GetMapping("/carrera-etapas")
    @Timed
    public ResponseEntity<List<Carrera_etapaDTO>> getAllCarrera_etapas(Pageable pageable) {
        log.debug("REST request to get a page of Carrera_etapas");
        Page<Carrera_etapaDTO> page = carrera_etapaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carrera-etapas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carrera-etapas/:id : get the "id" carrera_etapa.
     *
     * @param id the id of the carrera_etapaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carrera_etapaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carrera-etapas/{id}")
    @Timed
    public ResponseEntity<Carrera_etapaDTO> getCarrera_etapa(@PathVariable Long id) {
        log.debug("REST request to get Carrera_etapa : {}", id);
        Carrera_etapaDTO carrera_etapaDTO = carrera_etapaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carrera_etapaDTO));
    }

    /**
     * DELETE  /carrera-etapas/:id : delete the "id" carrera_etapa.
     *
     * @param id the id of the carrera_etapaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carrera-etapas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarrera_etapa(@PathVariable Long id) {
        log.debug("REST request to delete Carrera_etapa : {}", id);
        carrera_etapaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
