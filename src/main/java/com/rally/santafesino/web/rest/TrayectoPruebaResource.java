package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.TrayectoPruebaService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.TrayectoPruebaDTO;
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
 * REST controller for managing TrayectoPrueba.
 */
@RestController
@RequestMapping("/api")
public class TrayectoPruebaResource {

    private final Logger log = LoggerFactory.getLogger(TrayectoPruebaResource.class);

    private static final String ENTITY_NAME = "trayectoPrueba";

    private final TrayectoPruebaService trayectoPruebaService;

    public TrayectoPruebaResource(TrayectoPruebaService trayectoPruebaService) {
        this.trayectoPruebaService = trayectoPruebaService;
    }

    /**
     * POST  /trayecto-pruebas : Create a new trayectoPrueba.
     *
     * @param trayectoPruebaDTO the trayectoPruebaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trayectoPruebaDTO, or with status 400 (Bad Request) if the trayectoPrueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trayecto-pruebas")
    @Timed
    public ResponseEntity<TrayectoPruebaDTO> createTrayectoPrueba(@Valid @RequestBody TrayectoPruebaDTO trayectoPruebaDTO) throws URISyntaxException {
        log.debug("REST request to save TrayectoPrueba : {}", trayectoPruebaDTO);
        if (trayectoPruebaDTO.getId() != null) {
            throw new BadRequestAlertException("A new trayectoPrueba cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrayectoPruebaDTO result = trayectoPruebaService.save(trayectoPruebaDTO);
        return ResponseEntity.created(new URI("/api/trayecto-pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trayecto-pruebas : Updates an existing trayectoPrueba.
     *
     * @param trayectoPruebaDTO the trayectoPruebaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trayectoPruebaDTO,
     * or with status 400 (Bad Request) if the trayectoPruebaDTO is not valid,
     * or with status 500 (Internal Server Error) if the trayectoPruebaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trayecto-pruebas")
    @Timed
    public ResponseEntity<TrayectoPruebaDTO> updateTrayectoPrueba(@Valid @RequestBody TrayectoPruebaDTO trayectoPruebaDTO) throws URISyntaxException {
        log.debug("REST request to update TrayectoPrueba : {}", trayectoPruebaDTO);
        if (trayectoPruebaDTO.getId() == null) {
            return createTrayectoPrueba(trayectoPruebaDTO);
        }
        TrayectoPruebaDTO result = trayectoPruebaService.save(trayectoPruebaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trayectoPruebaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trayecto-pruebas : get all the trayectoPruebas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trayectoPruebas in body
     */
    @GetMapping("/trayecto-pruebas")
    @Timed
    public ResponseEntity<List<TrayectoPruebaDTO>> getAllTrayectoPruebas(Pageable pageable) {
        log.debug("REST request to get a page of TrayectoPruebas");
        Page<TrayectoPruebaDTO> page = trayectoPruebaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trayecto-pruebas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trayecto-pruebas/:id : get the "id" trayectoPrueba.
     *
     * @param id the id of the trayectoPruebaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trayectoPruebaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trayecto-pruebas/{id}")
    @Timed
    public ResponseEntity<TrayectoPruebaDTO> getTrayectoPrueba(@PathVariable Long id) {
        log.debug("REST request to get TrayectoPrueba : {}", id);
        TrayectoPruebaDTO trayectoPruebaDTO = trayectoPruebaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trayectoPruebaDTO));
    }

    /**
     * DELETE  /trayecto-pruebas/:id : delete the "id" trayectoPrueba.
     *
     * @param id the id of the trayectoPruebaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trayecto-pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrayectoPrueba(@PathVariable Long id) {
        log.debug("REST request to delete TrayectoPrueba : {}", id);
        trayectoPruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
