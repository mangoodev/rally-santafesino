package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.Localidad_carreraService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.Localidad_carreraDTO;
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
 * REST controller for managing Localidad_carrera.
 */
@RestController
@RequestMapping("/api")
public class Localidad_carreraResource {

    private final Logger log = LoggerFactory.getLogger(Localidad_carreraResource.class);

    private static final String ENTITY_NAME = "localidad_carrera";

    private final Localidad_carreraService localidad_carreraService;

    public Localidad_carreraResource(Localidad_carreraService localidad_carreraService) {
        this.localidad_carreraService = localidad_carreraService;
    }

    /**
     * POST  /localidad-carreras : Create a new localidad_carrera.
     *
     * @param localidad_carreraDTO the localidad_carreraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localidad_carreraDTO, or with status 400 (Bad Request) if the localidad_carrera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/localidad-carreras")
    @Timed
    public ResponseEntity<Localidad_carreraDTO> createLocalidad_carrera(@Valid @RequestBody Localidad_carreraDTO localidad_carreraDTO) throws URISyntaxException {
        log.debug("REST request to save Localidad_carrera : {}", localidad_carreraDTO);
        if (localidad_carreraDTO.getId() != null) {
            throw new BadRequestAlertException("A new localidad_carrera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Localidad_carreraDTO result = localidad_carreraService.save(localidad_carreraDTO);
        return ResponseEntity.created(new URI("/api/localidad-carreras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /localidad-carreras : Updates an existing localidad_carrera.
     *
     * @param localidad_carreraDTO the localidad_carreraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localidad_carreraDTO,
     * or with status 400 (Bad Request) if the localidad_carreraDTO is not valid,
     * or with status 500 (Internal Server Error) if the localidad_carreraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/localidad-carreras")
    @Timed
    public ResponseEntity<Localidad_carreraDTO> updateLocalidad_carrera(@Valid @RequestBody Localidad_carreraDTO localidad_carreraDTO) throws URISyntaxException {
        log.debug("REST request to update Localidad_carrera : {}", localidad_carreraDTO);
        if (localidad_carreraDTO.getId() == null) {
            return createLocalidad_carrera(localidad_carreraDTO);
        }
        Localidad_carreraDTO result = localidad_carreraService.save(localidad_carreraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localidad_carreraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /localidad-carreras : get all the localidad_carreras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localidad_carreras in body
     */
    @GetMapping("/localidad-carreras")
    @Timed
    public ResponseEntity<List<Localidad_carreraDTO>> getAllLocalidad_carreras(Pageable pageable) {
        log.debug("REST request to get a page of Localidad_carreras");
        Page<Localidad_carreraDTO> page = localidad_carreraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/localidad-carreras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /localidad-carreras/:id : get the "id" localidad_carrera.
     *
     * @param id the id of the localidad_carreraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localidad_carreraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/localidad-carreras/{id}")
    @Timed
    public ResponseEntity<Localidad_carreraDTO> getLocalidad_carrera(@PathVariable Long id) {
        log.debug("REST request to get Localidad_carrera : {}", id);
        Localidad_carreraDTO localidad_carreraDTO = localidad_carreraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localidad_carreraDTO));
    }

    /**
     * DELETE  /localidad-carreras/:id : delete the "id" localidad_carrera.
     *
     * @param id the id of the localidad_carreraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/localidad-carreras/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalidad_carrera(@PathVariable Long id) {
        log.debug("REST request to delete Localidad_carrera : {}", id);
        localidad_carreraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
