package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.TrayectoService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.TrayectoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Trayecto.
 */
@RestController
@RequestMapping("/api")
public class TrayectoResource {

    private final Logger log = LoggerFactory.getLogger(TrayectoResource.class);

    private static final String ENTITY_NAME = "trayecto";

    private final TrayectoService trayectoService;

    public TrayectoResource(TrayectoService trayectoService) {
        this.trayectoService = trayectoService;
    }

    /**
     * POST  /trayectos : Create a new trayecto.
     *
     * @param trayectoDTO the trayectoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trayectoDTO, or with status 400 (Bad Request) if the trayecto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trayectos")
    @Timed
    public ResponseEntity<TrayectoDTO> createTrayecto(@RequestBody TrayectoDTO trayectoDTO) throws URISyntaxException {
        log.debug("REST request to save Trayecto : {}", trayectoDTO);
        if (trayectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new trayecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrayectoDTO result = trayectoService.save(trayectoDTO);
        return ResponseEntity.created(new URI("/api/trayectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trayectos : Updates an existing trayecto.
     *
     * @param trayectoDTO the trayectoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trayectoDTO,
     * or with status 400 (Bad Request) if the trayectoDTO is not valid,
     * or with status 500 (Internal Server Error) if the trayectoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trayectos")
    @Timed
    public ResponseEntity<TrayectoDTO> updateTrayecto(@RequestBody TrayectoDTO trayectoDTO) throws URISyntaxException {
        log.debug("REST request to update Trayecto : {}", trayectoDTO);
        if (trayectoDTO.getId() == null) {
            return createTrayecto(trayectoDTO);
        }
        TrayectoDTO result = trayectoService.save(trayectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trayectoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trayectos : get all the trayectos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trayectos in body
     */
    @GetMapping("/trayectos")
    @Timed
    public ResponseEntity<List<TrayectoDTO>> getAllTrayectos(Pageable pageable) {
        log.debug("REST request to get a page of Trayectos");
        Page<TrayectoDTO> page = trayectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trayectos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trayectos/:id : get the "id" trayecto.
     *
     * @param id the id of the trayectoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trayectoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trayectos/{id}")
    @Timed
    public ResponseEntity<TrayectoDTO> getTrayecto(@PathVariable Long id) {
        log.debug("REST request to get Trayecto : {}", id);
        TrayectoDTO trayectoDTO = trayectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trayectoDTO));
    }

    /**
     * DELETE  /trayectos/:id : delete the "id" trayecto.
     *
     * @param id the id of the trayectoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trayectos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrayecto(@PathVariable Long id) {
        log.debug("REST request to delete Trayecto : {}", id);
        trayectoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
