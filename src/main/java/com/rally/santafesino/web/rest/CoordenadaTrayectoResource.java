package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.CoordenadaTrayectoService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.CoordenadaTrayectoDTO;
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
 * REST controller for managing CoordenadaTrayecto.
 */
@RestController
@RequestMapping("/api")
public class CoordenadaTrayectoResource {

    private final Logger log = LoggerFactory.getLogger(CoordenadaTrayectoResource.class);

    private static final String ENTITY_NAME = "coordenadaTrayecto";

    private final CoordenadaTrayectoService coordenadaTrayectoService;

    public CoordenadaTrayectoResource(CoordenadaTrayectoService coordenadaTrayectoService) {
        this.coordenadaTrayectoService = coordenadaTrayectoService;
    }

    /**
     * POST  /coordenada-trayectos : Create a new coordenadaTrayecto.
     *
     * @param coordenadaTrayectoDTO the coordenadaTrayectoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coordenadaTrayectoDTO, or with status 400 (Bad Request) if the coordenadaTrayecto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coordenada-trayectos")
    @Timed
    public ResponseEntity<CoordenadaTrayectoDTO> createCoordenadaTrayecto(@Valid @RequestBody CoordenadaTrayectoDTO coordenadaTrayectoDTO) throws URISyntaxException {
        log.debug("REST request to save CoordenadaTrayecto : {}", coordenadaTrayectoDTO);
        if (coordenadaTrayectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new coordenadaTrayecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoordenadaTrayectoDTO result = coordenadaTrayectoService.save(coordenadaTrayectoDTO);
        return ResponseEntity.created(new URI("/api/coordenada-trayectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coordenada-trayectos : Updates an existing coordenadaTrayecto.
     *
     * @param coordenadaTrayectoDTO the coordenadaTrayectoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coordenadaTrayectoDTO,
     * or with status 400 (Bad Request) if the coordenadaTrayectoDTO is not valid,
     * or with status 500 (Internal Server Error) if the coordenadaTrayectoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coordenada-trayectos")
    @Timed
    public ResponseEntity<CoordenadaTrayectoDTO> updateCoordenadaTrayecto(@Valid @RequestBody CoordenadaTrayectoDTO coordenadaTrayectoDTO) throws URISyntaxException {
        log.debug("REST request to update CoordenadaTrayecto : {}", coordenadaTrayectoDTO);
        if (coordenadaTrayectoDTO.getId() == null) {
            return createCoordenadaTrayecto(coordenadaTrayectoDTO);
        }
        CoordenadaTrayectoDTO result = coordenadaTrayectoService.save(coordenadaTrayectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coordenadaTrayectoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coordenada-trayectos : get all the coordenadaTrayectos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coordenadaTrayectos in body
     */
    @GetMapping("/coordenada-trayectos")
    @Timed
    public ResponseEntity<List<CoordenadaTrayectoDTO>> getAllCoordenadaTrayectos(Pageable pageable) {
        log.debug("REST request to get a page of CoordenadaTrayectos");
        Page<CoordenadaTrayectoDTO> page = coordenadaTrayectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coordenada-trayectos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /coordenada-trayectos/:id : get the "id" coordenadaTrayecto.
     *
     * @param id the id of the coordenadaTrayectoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coordenadaTrayectoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coordenada-trayectos/{id}")
    @Timed
    public ResponseEntity<CoordenadaTrayectoDTO> getCoordenadaTrayecto(@PathVariable Long id) {
        log.debug("REST request to get CoordenadaTrayecto : {}", id);
        CoordenadaTrayectoDTO coordenadaTrayectoDTO = coordenadaTrayectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coordenadaTrayectoDTO));
    }

    /**
     * DELETE  /coordenada-trayectos/:id : delete the "id" coordenadaTrayecto.
     *
     * @param id the id of the coordenadaTrayectoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coordenada-trayectos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoordenadaTrayecto(@PathVariable Long id) {
        log.debug("REST request to delete CoordenadaTrayecto : {}", id);
        coordenadaTrayectoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
