package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.ClaseService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.ClaseDTO;
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
 * REST controller for managing Clase.
 */
@RestController
@RequestMapping("/api")
public class ClaseResource {

    private final Logger log = LoggerFactory.getLogger(ClaseResource.class);

    private static final String ENTITY_NAME = "clase";

    private final ClaseService claseService;

    public ClaseResource(ClaseService claseService) {
        this.claseService = claseService;
    }

    /**
     * POST  /clases : Create a new clase.
     *
     * @param claseDTO the claseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new claseDTO, or with status 400 (Bad Request) if the clase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clases")
    @Timed
    public ResponseEntity<ClaseDTO> createClase(@Valid @RequestBody ClaseDTO claseDTO) throws URISyntaxException {
        log.debug("REST request to save Clase : {}", claseDTO);
        if (claseDTO.getId() != null) {
            throw new BadRequestAlertException("A new clase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaseDTO result = claseService.save(claseDTO);
        return ResponseEntity.created(new URI("/api/clases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clases : Updates an existing clase.
     *
     * @param claseDTO the claseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated claseDTO,
     * or with status 400 (Bad Request) if the claseDTO is not valid,
     * or with status 500 (Internal Server Error) if the claseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clases")
    @Timed
    public ResponseEntity<ClaseDTO> updateClase(@Valid @RequestBody ClaseDTO claseDTO) throws URISyntaxException {
        log.debug("REST request to update Clase : {}", claseDTO);
        if (claseDTO.getId() == null) {
            return createClase(claseDTO);
        }
        ClaseDTO result = claseService.save(claseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, claseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clases : get all the clases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clases in body
     */
    @GetMapping("/clases")
    @Timed
    public ResponseEntity<List<ClaseDTO>> getAllClases(Pageable pageable) {
        log.debug("REST request to get a page of Clases");
        Page<ClaseDTO> page = claseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clases/:id : get the "id" clase.
     *
     * @param id the id of the claseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the claseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clases/{id}")
    @Timed
    public ResponseEntity<ClaseDTO> getClase(@PathVariable Long id) {
        log.debug("REST request to get Clase : {}", id);
        ClaseDTO claseDTO = claseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(claseDTO));
    }

    /**
     * DELETE  /clases/:id : delete the "id" clase.
     *
     * @param id the id of the claseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clases/{id}")
    @Timed
    public ResponseEntity<Void> deleteClase(@PathVariable Long id) {
        log.debug("REST request to delete Clase : {}", id);
        claseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
