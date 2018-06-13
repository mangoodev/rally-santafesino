package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.Auto_tiempo_pruebaService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.Auto_tiempo_pruebaDTO;
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
 * REST controller for managing Auto_tiempo_prueba.
 */
@RestController
@RequestMapping("/api")
public class Auto_tiempo_pruebaResource {

    private final Logger log = LoggerFactory.getLogger(Auto_tiempo_pruebaResource.class);

    private static final String ENTITY_NAME = "auto_tiempo_prueba";

    private final Auto_tiempo_pruebaService auto_tiempo_pruebaService;

    public Auto_tiempo_pruebaResource(Auto_tiempo_pruebaService auto_tiempo_pruebaService) {
        this.auto_tiempo_pruebaService = auto_tiempo_pruebaService;
    }

    /**
     * POST  /auto-tiempo-pruebas : Create a new auto_tiempo_prueba.
     *
     * @param auto_tiempo_pruebaDTO the auto_tiempo_pruebaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auto_tiempo_pruebaDTO, or with status 400 (Bad Request) if the auto_tiempo_prueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auto-tiempo-pruebas")
    @Timed
    public ResponseEntity<Auto_tiempo_pruebaDTO> createAuto_tiempo_prueba(@Valid @RequestBody Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO) throws URISyntaxException {
        log.debug("REST request to save Auto_tiempo_prueba : {}", auto_tiempo_pruebaDTO);
        if (auto_tiempo_pruebaDTO.getId() != null) {
            throw new BadRequestAlertException("A new auto_tiempo_prueba cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Auto_tiempo_pruebaDTO result = auto_tiempo_pruebaService.save(auto_tiempo_pruebaDTO);
        return ResponseEntity.created(new URI("/api/auto-tiempo-pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auto-tiempo-pruebas : Updates an existing auto_tiempo_prueba.
     *
     * @param auto_tiempo_pruebaDTO the auto_tiempo_pruebaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auto_tiempo_pruebaDTO,
     * or with status 400 (Bad Request) if the auto_tiempo_pruebaDTO is not valid,
     * or with status 500 (Internal Server Error) if the auto_tiempo_pruebaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auto-tiempo-pruebas")
    @Timed
    public ResponseEntity<Auto_tiempo_pruebaDTO> updateAuto_tiempo_prueba(@Valid @RequestBody Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO) throws URISyntaxException {
        log.debug("REST request to update Auto_tiempo_prueba : {}", auto_tiempo_pruebaDTO);
        if (auto_tiempo_pruebaDTO.getId() == null) {
            return createAuto_tiempo_prueba(auto_tiempo_pruebaDTO);
        }
        Auto_tiempo_pruebaDTO result = auto_tiempo_pruebaService.save(auto_tiempo_pruebaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auto_tiempo_pruebaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auto-tiempo-pruebas : get all the auto_tiempo_pruebas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auto_tiempo_pruebas in body
     */
    @GetMapping("/auto-tiempo-pruebas")
    @Timed
    public ResponseEntity<List<Auto_tiempo_pruebaDTO>> getAllAuto_tiempo_pruebas(Pageable pageable) {
        log.debug("REST request to get a page of Auto_tiempo_pruebas");
        Page<Auto_tiempo_pruebaDTO> page = auto_tiempo_pruebaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auto-tiempo-pruebas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /auto-tiempo-pruebas/:id : get the "id" auto_tiempo_prueba.
     *
     * @param id the id of the auto_tiempo_pruebaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auto_tiempo_pruebaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/auto-tiempo-pruebas/{id}")
    @Timed
    public ResponseEntity<Auto_tiempo_pruebaDTO> getAuto_tiempo_prueba(@PathVariable Long id) {
        log.debug("REST request to get Auto_tiempo_prueba : {}", id);
        Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO = auto_tiempo_pruebaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auto_tiempo_pruebaDTO));
    }

    /**
     * DELETE  /auto-tiempo-pruebas/:id : delete the "id" auto_tiempo_prueba.
     *
     * @param id the id of the auto_tiempo_pruebaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auto-tiempo-pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuto_tiempo_prueba(@PathVariable Long id) {
        log.debug("REST request to delete Auto_tiempo_prueba : {}", id);
        auto_tiempo_pruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
