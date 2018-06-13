package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.Etapa_pruebaService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.Etapa_pruebaDTO;
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
 * REST controller for managing Etapa_prueba.
 */
@RestController
@RequestMapping("/api")
public class Etapa_pruebaResource {

    private final Logger log = LoggerFactory.getLogger(Etapa_pruebaResource.class);

    private static final String ENTITY_NAME = "etapa_prueba";

    private final Etapa_pruebaService etapa_pruebaService;

    public Etapa_pruebaResource(Etapa_pruebaService etapa_pruebaService) {
        this.etapa_pruebaService = etapa_pruebaService;
    }

    /**
     * POST  /etapa-pruebas : Create a new etapa_prueba.
     *
     * @param etapa_pruebaDTO the etapa_pruebaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etapa_pruebaDTO, or with status 400 (Bad Request) if the etapa_prueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etapa-pruebas")
    @Timed
    public ResponseEntity<Etapa_pruebaDTO> createEtapa_prueba(@Valid @RequestBody Etapa_pruebaDTO etapa_pruebaDTO) throws URISyntaxException {
        log.debug("REST request to save Etapa_prueba : {}", etapa_pruebaDTO);
        if (etapa_pruebaDTO.getId() != null) {
            throw new BadRequestAlertException("A new etapa_prueba cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etapa_pruebaDTO result = etapa_pruebaService.save(etapa_pruebaDTO);
        return ResponseEntity.created(new URI("/api/etapa-pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etapa-pruebas : Updates an existing etapa_prueba.
     *
     * @param etapa_pruebaDTO the etapa_pruebaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etapa_pruebaDTO,
     * or with status 400 (Bad Request) if the etapa_pruebaDTO is not valid,
     * or with status 500 (Internal Server Error) if the etapa_pruebaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etapa-pruebas")
    @Timed
    public ResponseEntity<Etapa_pruebaDTO> updateEtapa_prueba(@Valid @RequestBody Etapa_pruebaDTO etapa_pruebaDTO) throws URISyntaxException {
        log.debug("REST request to update Etapa_prueba : {}", etapa_pruebaDTO);
        if (etapa_pruebaDTO.getId() == null) {
            return createEtapa_prueba(etapa_pruebaDTO);
        }
        Etapa_pruebaDTO result = etapa_pruebaService.save(etapa_pruebaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etapa_pruebaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etapa-pruebas : get all the etapa_pruebas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of etapa_pruebas in body
     */
    @GetMapping("/etapa-pruebas")
    @Timed
    public ResponseEntity<List<Etapa_pruebaDTO>> getAllEtapa_pruebas(Pageable pageable) {
        log.debug("REST request to get a page of Etapa_pruebas");
        Page<Etapa_pruebaDTO> page = etapa_pruebaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etapa-pruebas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /etapa-pruebas/:id : get the "id" etapa_prueba.
     *
     * @param id the id of the etapa_pruebaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etapa_pruebaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/etapa-pruebas/{id}")
    @Timed
    public ResponseEntity<Etapa_pruebaDTO> getEtapa_prueba(@PathVariable Long id) {
        log.debug("REST request to get Etapa_prueba : {}", id);
        Etapa_pruebaDTO etapa_pruebaDTO = etapa_pruebaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(etapa_pruebaDTO));
    }

    /**
     * DELETE  /etapa-pruebas/:id : delete the "id" etapa_prueba.
     *
     * @param id the id of the etapa_pruebaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etapa-pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtapa_prueba(@PathVariable Long id) {
        log.debug("REST request to delete Etapa_prueba : {}", id);
        etapa_pruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
