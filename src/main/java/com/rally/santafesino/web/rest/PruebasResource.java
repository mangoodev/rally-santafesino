package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.PruebasService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.PruebasDTO;
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
 * REST controller for managing Pruebas.
 */
@RestController
@RequestMapping("/api")
public class PruebasResource {

    private final Logger log = LoggerFactory.getLogger(PruebasResource.class);

    private static final String ENTITY_NAME = "pruebas";

    private final PruebasService pruebasService;

    public PruebasResource(PruebasService pruebasService) {
        this.pruebasService = pruebasService;
    }

    /**
     * POST  /pruebas : Create a new pruebas.
     *
     * @param pruebasDTO the pruebasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pruebasDTO, or with status 400 (Bad Request) if the pruebas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pruebas")
    @Timed
    public ResponseEntity<PruebasDTO> createPruebas(@Valid @RequestBody PruebasDTO pruebasDTO) throws URISyntaxException {
        log.debug("REST request to save Pruebas : {}", pruebasDTO);
        if (pruebasDTO.getId() != null) {
            throw new BadRequestAlertException("A new pruebas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PruebasDTO result = pruebasService.save(pruebasDTO);
        return ResponseEntity.created(new URI("/api/pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pruebas : Updates an existing pruebas.
     *
     * @param pruebasDTO the pruebasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pruebasDTO,
     * or with status 400 (Bad Request) if the pruebasDTO is not valid,
     * or with status 500 (Internal Server Error) if the pruebasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pruebas")
    @Timed
    public ResponseEntity<PruebasDTO> updatePruebas(@Valid @RequestBody PruebasDTO pruebasDTO) throws URISyntaxException {
        log.debug("REST request to update Pruebas : {}", pruebasDTO);
        if (pruebasDTO.getId() == null) {
            return createPruebas(pruebasDTO);
        }
        PruebasDTO result = pruebasService.save(pruebasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pruebasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pruebas : get all the pruebas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pruebas in body
     */
    @GetMapping("/pruebas")
    @Timed
    public ResponseEntity<List<PruebasDTO>> getAllPruebas(Pageable pageable) {
        log.debug("REST request to get a page of Pruebas");
        Page<PruebasDTO> page = pruebasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pruebas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pruebas/:id : get the "id" pruebas.
     *
     * @param id the id of the pruebasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pruebasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pruebas/{id}")
    @Timed
    public ResponseEntity<PruebasDTO> getPruebas(@PathVariable Long id) {
        log.debug("REST request to get Pruebas : {}", id);
        PruebasDTO pruebasDTO = pruebasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pruebasDTO));
    }

    /**
     * DELETE  /pruebas/:id : delete the "id" pruebas.
     *
     * @param id the id of the pruebasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deletePruebas(@PathVariable Long id) {
        log.debug("REST request to delete Pruebas : {}", id);
        pruebasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
