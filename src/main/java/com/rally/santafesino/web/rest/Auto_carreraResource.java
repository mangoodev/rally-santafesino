package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.Auto_carreraService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.Auto_carreraDTO;
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
 * REST controller for managing Auto_carrera.
 */
@RestController
@RequestMapping("/api")
public class Auto_carreraResource {

    private final Logger log = LoggerFactory.getLogger(Auto_carreraResource.class);

    private static final String ENTITY_NAME = "auto_carrera";

    private final Auto_carreraService auto_carreraService;

    public Auto_carreraResource(Auto_carreraService auto_carreraService) {
        this.auto_carreraService = auto_carreraService;
    }

    /**
     * POST  /auto-carreras : Create a new auto_carrera.
     *
     * @param auto_carreraDTO the auto_carreraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auto_carreraDTO, or with status 400 (Bad Request) if the auto_carrera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auto-carreras")
    @Timed
    public ResponseEntity<Auto_carreraDTO> createAuto_carrera(@Valid @RequestBody Auto_carreraDTO auto_carreraDTO) throws URISyntaxException {
        log.debug("REST request to save Auto_carrera : {}", auto_carreraDTO);
        if (auto_carreraDTO.getId() != null) {
            throw new BadRequestAlertException("A new auto_carrera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Auto_carreraDTO result = auto_carreraService.save(auto_carreraDTO);
        return ResponseEntity.created(new URI("/api/auto-carreras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auto-carreras : Updates an existing auto_carrera.
     *
     * @param auto_carreraDTO the auto_carreraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auto_carreraDTO,
     * or with status 400 (Bad Request) if the auto_carreraDTO is not valid,
     * or with status 500 (Internal Server Error) if the auto_carreraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auto-carreras")
    @Timed
    public ResponseEntity<Auto_carreraDTO> updateAuto_carrera(@Valid @RequestBody Auto_carreraDTO auto_carreraDTO) throws URISyntaxException {
        log.debug("REST request to update Auto_carrera : {}", auto_carreraDTO);
        if (auto_carreraDTO.getId() == null) {
            return createAuto_carrera(auto_carreraDTO);
        }
        Auto_carreraDTO result = auto_carreraService.save(auto_carreraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auto_carreraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auto-carreras : get all the auto_carreras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auto_carreras in body
     */
    @GetMapping("/auto-carreras")
    @Timed
    public ResponseEntity<List<Auto_carreraDTO>> getAllAuto_carreras(Pageable pageable) {
        log.debug("REST request to get a page of Auto_carreras");
        Page<Auto_carreraDTO> page = auto_carreraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auto-carreras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /auto-carreras/:id : get the "id" auto_carrera.
     *
     * @param id the id of the auto_carreraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auto_carreraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/auto-carreras/{id}")
    @Timed
    public ResponseEntity<Auto_carreraDTO> getAuto_carrera(@PathVariable Long id) {
        log.debug("REST request to get Auto_carrera : {}", id);
        Auto_carreraDTO auto_carreraDTO = auto_carreraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auto_carreraDTO));
    }

    /**
     * DELETE  /auto-carreras/:id : delete the "id" auto_carrera.
     *
     * @param id the id of the auto_carreraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auto-carreras/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuto_carrera(@PathVariable Long id) {
        log.debug("REST request to delete Auto_carrera : {}", id);
        auto_carreraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
