package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.Trayecto_pruebaService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.Trayecto_pruebaDTO;
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
 * REST controller for managing Trayecto_prueba.
 */
@RestController
@RequestMapping("/api")
public class Trayecto_pruebaResource {

    private final Logger log = LoggerFactory.getLogger(Trayecto_pruebaResource.class);

    private static final String ENTITY_NAME = "trayecto_prueba";

    private final Trayecto_pruebaService trayecto_pruebaService;

    public Trayecto_pruebaResource(Trayecto_pruebaService trayecto_pruebaService) {
        this.trayecto_pruebaService = trayecto_pruebaService;
    }

    /**
     * POST  /trayecto-pruebas : Create a new trayecto_prueba.
     *
     * @param trayecto_pruebaDTO the trayecto_pruebaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trayecto_pruebaDTO, or with status 400 (Bad Request) if the trayecto_prueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trayecto-pruebas")
    @Timed
    public ResponseEntity<Trayecto_pruebaDTO> createTrayecto_prueba(@Valid @RequestBody Trayecto_pruebaDTO trayecto_pruebaDTO) throws URISyntaxException {
        log.debug("REST request to save Trayecto_prueba : {}", trayecto_pruebaDTO);
        if (trayecto_pruebaDTO.getId() != null) {
            throw new BadRequestAlertException("A new trayecto_prueba cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trayecto_pruebaDTO result = trayecto_pruebaService.save(trayecto_pruebaDTO);
        return ResponseEntity.created(new URI("/api/trayecto-pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trayecto-pruebas : Updates an existing trayecto_prueba.
     *
     * @param trayecto_pruebaDTO the trayecto_pruebaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trayecto_pruebaDTO,
     * or with status 400 (Bad Request) if the trayecto_pruebaDTO is not valid,
     * or with status 500 (Internal Server Error) if the trayecto_pruebaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trayecto-pruebas")
    @Timed
    public ResponseEntity<Trayecto_pruebaDTO> updateTrayecto_prueba(@Valid @RequestBody Trayecto_pruebaDTO trayecto_pruebaDTO) throws URISyntaxException {
        log.debug("REST request to update Trayecto_prueba : {}", trayecto_pruebaDTO);
        if (trayecto_pruebaDTO.getId() == null) {
            return createTrayecto_prueba(trayecto_pruebaDTO);
        }
        Trayecto_pruebaDTO result = trayecto_pruebaService.save(trayecto_pruebaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trayecto_pruebaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trayecto-pruebas : get all the trayecto_pruebas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trayecto_pruebas in body
     */
    @GetMapping("/trayecto-pruebas")
    @Timed
    public ResponseEntity<List<Trayecto_pruebaDTO>> getAllTrayecto_pruebas(Pageable pageable) {
        log.debug("REST request to get a page of Trayecto_pruebas");
        Page<Trayecto_pruebaDTO> page = trayecto_pruebaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trayecto-pruebas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trayecto-pruebas/:id : get the "id" trayecto_prueba.
     *
     * @param id the id of the trayecto_pruebaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trayecto_pruebaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trayecto-pruebas/{id}")
    @Timed
    public ResponseEntity<Trayecto_pruebaDTO> getTrayecto_prueba(@PathVariable Long id) {
        log.debug("REST request to get Trayecto_prueba : {}", id);
        Trayecto_pruebaDTO trayecto_pruebaDTO = trayecto_pruebaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trayecto_pruebaDTO));
    }

    /**
     * DELETE  /trayecto-pruebas/:id : delete the "id" trayecto_prueba.
     *
     * @param id the id of the trayecto_pruebaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trayecto-pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrayecto_prueba(@PathVariable Long id) {
        log.debug("REST request to delete Trayecto_prueba : {}", id);
        trayecto_pruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
