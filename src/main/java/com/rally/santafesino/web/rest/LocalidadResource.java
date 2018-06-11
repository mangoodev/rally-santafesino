package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.LocalidadService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.LocalidadDTO;
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
 * REST controller for managing Localidad.
 */
@RestController
@RequestMapping("/api")
public class LocalidadResource {

    private final Logger log = LoggerFactory.getLogger(LocalidadResource.class);

    private static final String ENTITY_NAME = "localidad";

    private final LocalidadService localidadService;

    public LocalidadResource(LocalidadService localidadService) {
        this.localidadService = localidadService;
    }

    /**
     * POST  /localidads : Create a new localidad.
     *
     * @param localidadDTO the localidadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localidadDTO, or with status 400 (Bad Request) if the localidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/localidads")
    @Timed
    public ResponseEntity<LocalidadDTO> createLocalidad(@RequestBody LocalidadDTO localidadDTO) throws URISyntaxException {
        log.debug("REST request to save Localidad : {}", localidadDTO);
        if (localidadDTO.getId() != null) {
            throw new BadRequestAlertException("A new localidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalidadDTO result = localidadService.save(localidadDTO);
        return ResponseEntity.created(new URI("/api/localidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /localidads : Updates an existing localidad.
     *
     * @param localidadDTO the localidadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localidadDTO,
     * or with status 400 (Bad Request) if the localidadDTO is not valid,
     * or with status 500 (Internal Server Error) if the localidadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/localidads")
    @Timed
    public ResponseEntity<LocalidadDTO> updateLocalidad(@RequestBody LocalidadDTO localidadDTO) throws URISyntaxException {
        log.debug("REST request to update Localidad : {}", localidadDTO);
        if (localidadDTO.getId() == null) {
            return createLocalidad(localidadDTO);
        }
        LocalidadDTO result = localidadService.save(localidadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /localidads : get all the localidads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localidads in body
     */
    @GetMapping("/localidads")
    @Timed
    public ResponseEntity<List<LocalidadDTO>> getAllLocalidads(Pageable pageable) {
        log.debug("REST request to get a page of Localidads");
        Page<LocalidadDTO> page = localidadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/localidads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /localidads/:id : get the "id" localidad.
     *
     * @param id the id of the localidadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localidadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/localidads/{id}")
    @Timed
    public ResponseEntity<LocalidadDTO> getLocalidad(@PathVariable Long id) {
        log.debug("REST request to get Localidad : {}", id);
        LocalidadDTO localidadDTO = localidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localidadDTO));
    }

    /**
     * DELETE  /localidads/:id : delete the "id" localidad.
     *
     * @param id the id of the localidadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/localidads/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalidad(@PathVariable Long id) {
        log.debug("REST request to delete Localidad : {}", id);
        localidadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
