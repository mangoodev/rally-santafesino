package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.LocalidadCarreraService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.LocalidadCarreraDTO;
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
 * REST controller for managing LocalidadCarrera.
 */
@RestController
@RequestMapping("/api")
public class LocalidadCarreraResource {

    private final Logger log = LoggerFactory.getLogger(LocalidadCarreraResource.class);

    private static final String ENTITY_NAME = "localidadCarrera";

    private final LocalidadCarreraService localidadCarreraService;

    public LocalidadCarreraResource(LocalidadCarreraService localidadCarreraService) {
        this.localidadCarreraService = localidadCarreraService;
    }

    /**
     * POST  /localidad-carreras : Create a new localidadCarrera.
     *
     * @param localidadCarreraDTO the localidadCarreraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localidadCarreraDTO, or with status 400 (Bad Request) if the localidadCarrera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/localidad-carreras")
    @Timed
    public ResponseEntity<LocalidadCarreraDTO> createLocalidadCarrera(@Valid @RequestBody LocalidadCarreraDTO localidadCarreraDTO) throws URISyntaxException {
        log.debug("REST request to save LocalidadCarrera : {}", localidadCarreraDTO);
        if (localidadCarreraDTO.getId() != null) {
            throw new BadRequestAlertException("A new localidadCarrera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalidadCarreraDTO result = localidadCarreraService.save(localidadCarreraDTO);
        return ResponseEntity.created(new URI("/api/localidad-carreras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /localidad-carreras : Updates an existing localidadCarrera.
     *
     * @param localidadCarreraDTO the localidadCarreraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localidadCarreraDTO,
     * or with status 400 (Bad Request) if the localidadCarreraDTO is not valid,
     * or with status 500 (Internal Server Error) if the localidadCarreraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/localidad-carreras")
    @Timed
    public ResponseEntity<LocalidadCarreraDTO> updateLocalidadCarrera(@Valid @RequestBody LocalidadCarreraDTO localidadCarreraDTO) throws URISyntaxException {
        log.debug("REST request to update LocalidadCarrera : {}", localidadCarreraDTO);
        if (localidadCarreraDTO.getId() == null) {
            return createLocalidadCarrera(localidadCarreraDTO);
        }
        LocalidadCarreraDTO result = localidadCarreraService.save(localidadCarreraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localidadCarreraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /localidad-carreras : get all the localidadCarreras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localidadCarreras in body
     */
    @GetMapping("/localidad-carreras")
    @Timed
    public ResponseEntity<List<LocalidadCarreraDTO>> getAllLocalidadCarreras(Pageable pageable) {
        log.debug("REST request to get a page of LocalidadCarreras");
        Page<LocalidadCarreraDTO> page = localidadCarreraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/localidad-carreras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /localidad-carreras/:id : get the "id" localidadCarrera.
     *
     * @param id the id of the localidadCarreraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localidadCarreraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/localidad-carreras/{id}")
    @Timed
    public ResponseEntity<LocalidadCarreraDTO> getLocalidadCarrera(@PathVariable Long id) {
        log.debug("REST request to get LocalidadCarrera : {}", id);
        LocalidadCarreraDTO localidadCarreraDTO = localidadCarreraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localidadCarreraDTO));
    }

    /**
     * DELETE  /localidad-carreras/:id : delete the "id" localidadCarrera.
     *
     * @param id the id of the localidadCarreraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/localidad-carreras/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalidadCarrera(@PathVariable Long id) {
        log.debug("REST request to delete LocalidadCarrera : {}", id);
        localidadCarreraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
