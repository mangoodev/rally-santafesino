package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.AutoCarreraService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.AutoCarreraDTO;
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
 * REST controller for managing AutoCarrera.
 */
@RestController
@RequestMapping("/api")
public class AutoCarreraResource {

    private final Logger log = LoggerFactory.getLogger(AutoCarreraResource.class);

    private static final String ENTITY_NAME = "autoCarrera";

    private final AutoCarreraService autoCarreraService;

    public AutoCarreraResource(AutoCarreraService autoCarreraService) {
        this.autoCarreraService = autoCarreraService;
    }

    /**
     * POST  /auto-carreras : Create a new autoCarrera.
     *
     * @param autoCarreraDTO the autoCarreraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new autoCarreraDTO, or with status 400 (Bad Request) if the autoCarrera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auto-carreras")
    @Timed
    public ResponseEntity<AutoCarreraDTO> createAutoCarrera(@Valid @RequestBody AutoCarreraDTO autoCarreraDTO) throws URISyntaxException {
        log.debug("REST request to save AutoCarrera : {}", autoCarreraDTO);
        if (autoCarreraDTO.getId() != null) {
            throw new BadRequestAlertException("A new autoCarrera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutoCarreraDTO result = autoCarreraService.save(autoCarreraDTO);
        return ResponseEntity.created(new URI("/api/auto-carreras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auto-carreras : Updates an existing autoCarrera.
     *
     * @param autoCarreraDTO the autoCarreraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated autoCarreraDTO,
     * or with status 400 (Bad Request) if the autoCarreraDTO is not valid,
     * or with status 500 (Internal Server Error) if the autoCarreraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auto-carreras")
    @Timed
    public ResponseEntity<AutoCarreraDTO> updateAutoCarrera(@Valid @RequestBody AutoCarreraDTO autoCarreraDTO) throws URISyntaxException {
        log.debug("REST request to update AutoCarrera : {}", autoCarreraDTO);
        if (autoCarreraDTO.getId() == null) {
            return createAutoCarrera(autoCarreraDTO);
        }
        AutoCarreraDTO result = autoCarreraService.save(autoCarreraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, autoCarreraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auto-carreras : get all the autoCarreras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of autoCarreras in body
     */
    @GetMapping("/auto-carreras")
    @Timed
    public ResponseEntity<List<AutoCarreraDTO>> getAllAutoCarreras(Pageable pageable) {
        log.debug("REST request to get a page of AutoCarreras");
        Page<AutoCarreraDTO> page = autoCarreraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auto-carreras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /auto-carreras/:id : get the "id" autoCarrera.
     *
     * @param id the id of the autoCarreraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the autoCarreraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/auto-carreras/{id}")
    @Timed
    public ResponseEntity<AutoCarreraDTO> getAutoCarrera(@PathVariable Long id) {
        log.debug("REST request to get AutoCarrera : {}", id);
        AutoCarreraDTO autoCarreraDTO = autoCarreraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(autoCarreraDTO));
    }

    /**
     * DELETE  /auto-carreras/:id : delete the "id" autoCarrera.
     *
     * @param id the id of the autoCarreraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auto-carreras/{id}")
    @Timed
    public ResponseEntity<Void> deleteAutoCarrera(@PathVariable Long id) {
        log.debug("REST request to delete AutoCarrera : {}", id);
        autoCarreraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
