package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.EtapaPruebaService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.EtapaPruebaDTO;
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
 * REST controller for managing EtapaPrueba.
 */
@RestController
@RequestMapping("/api")
public class EtapaPruebaResource {

    private final Logger log = LoggerFactory.getLogger(EtapaPruebaResource.class);

    private static final String ENTITY_NAME = "etapaPrueba";

    private final EtapaPruebaService etapaPruebaService;

    public EtapaPruebaResource(EtapaPruebaService etapaPruebaService) {
        this.etapaPruebaService = etapaPruebaService;
    }

    /**
     * POST  /etapa-pruebas : Create a new etapaPrueba.
     *
     * @param etapaPruebaDTO the etapaPruebaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etapaPruebaDTO, or with status 400 (Bad Request) if the etapaPrueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etapa-pruebas")
    @Timed
    public ResponseEntity<EtapaPruebaDTO> createEtapaPrueba(@Valid @RequestBody EtapaPruebaDTO etapaPruebaDTO) throws URISyntaxException {
        log.debug("REST request to save EtapaPrueba : {}", etapaPruebaDTO);
        if (etapaPruebaDTO.getId() != null) {
            throw new BadRequestAlertException("A new etapaPrueba cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtapaPruebaDTO result = etapaPruebaService.save(etapaPruebaDTO);
        return ResponseEntity.created(new URI("/api/etapa-pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etapa-pruebas : Updates an existing etapaPrueba.
     *
     * @param etapaPruebaDTO the etapaPruebaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etapaPruebaDTO,
     * or with status 400 (Bad Request) if the etapaPruebaDTO is not valid,
     * or with status 500 (Internal Server Error) if the etapaPruebaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etapa-pruebas")
    @Timed
    public ResponseEntity<EtapaPruebaDTO> updateEtapaPrueba(@Valid @RequestBody EtapaPruebaDTO etapaPruebaDTO) throws URISyntaxException {
        log.debug("REST request to update EtapaPrueba : {}", etapaPruebaDTO);
        if (etapaPruebaDTO.getId() == null) {
            return createEtapaPrueba(etapaPruebaDTO);
        }
        EtapaPruebaDTO result = etapaPruebaService.save(etapaPruebaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etapaPruebaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etapa-pruebas : get all the etapaPruebas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of etapaPruebas in body
     */
    @GetMapping("/etapa-pruebas")
    @Timed
    public ResponseEntity<List<EtapaPruebaDTO>> getAllEtapaPruebas(Pageable pageable) {
        log.debug("REST request to get a page of EtapaPruebas");
        Page<EtapaPruebaDTO> page = etapaPruebaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etapa-pruebas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /etapa-pruebas/:id : get the "id" etapaPrueba.
     *
     * @param id the id of the etapaPruebaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etapaPruebaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/etapa-pruebas/{id}")
    @Timed
    public ResponseEntity<EtapaPruebaDTO> getEtapaPrueba(@PathVariable Long id) {
        log.debug("REST request to get EtapaPrueba : {}", id);
        EtapaPruebaDTO etapaPruebaDTO = etapaPruebaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(etapaPruebaDTO));
    }

    /**
     * DELETE  /etapa-pruebas/:id : delete the "id" etapaPrueba.
     *
     * @param id the id of the etapaPruebaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etapa-pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtapaPrueba(@PathVariable Long id) {
        log.debug("REST request to delete EtapaPrueba : {}", id);
        etapaPruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
