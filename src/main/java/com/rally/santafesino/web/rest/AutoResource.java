package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.domain.Persona;
import com.rally.santafesino.service.AutoService;
import com.rally.santafesino.service.dto.PersonaDTO;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.AutoDTO;
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
 * REST controller for managing Auto.
 */
@RestController
@RequestMapping("/api")
public class AutoResource {

    private final Logger log = LoggerFactory.getLogger(AutoResource.class);

    private static final String ENTITY_NAME = "auto";

    private final AutoService autoService;

    public AutoResource(AutoService autoService) {
        this.autoService = autoService;
    }

    /**
     * POST  /autos : Create a new auto.
     *
     * @param autoDTO the autoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new autoDTO, or with status 400 (Bad Request) if the auto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/autos")
    @Timed
    public ResponseEntity<AutoDTO> createAuto(@Valid @RequestBody AutoDTO autoDTO) throws URISyntaxException {
        log.debug("REST request to save Auto : {}", autoDTO);
        if (autoDTO.getId() != null) {
            throw new BadRequestAlertException("A new auto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutoDTO result = autoService.save(autoDTO);
        return ResponseEntity.created(new URI("/api/autos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /autos : Updates an existing auto.
     *
     * @param autoDTO the autoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated autoDTO,
     * or with status 400 (Bad Request) if the autoDTO is not valid,
     * or with status 500 (Internal Server Error) if the autoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/autos")
    @Timed
    public ResponseEntity<AutoDTO> updateAuto(@Valid @RequestBody AutoDTO autoDTO) throws URISyntaxException {
        log.debug("REST request to update Auto : {}", autoDTO);
        if (autoDTO.getId() == null) {
            return createAuto(autoDTO);
        }
        AutoDTO result = autoService.save(autoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, autoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /autos : get all the autos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of autos in body
     */
    @GetMapping("/autos")
    @Timed
    public ResponseEntity<List<AutoDTO>> getAllAutos(Pageable pageable) {
        log.debug("REST request to get a page of Autos");
        Page<AutoDTO> page = autoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/autos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /autos/:id : get the "id" auto.
     *
     * @param id the id of the autoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the autoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/autos/{id}")
    @Timed
    public ResponseEntity<AutoDTO> getAuto(@PathVariable Long id) {
        log.debug("REST request to get Auto : {}", id);
        AutoDTO autoDTO = autoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(autoDTO));
    }

    /**
     * DELETE  /autos/:id : delete the "id" auto.
     *
     * @param id the id of the autoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/autos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuto(@PathVariable Long id) {
        log.debug("REST request to delete Auto : {}", id);
        autoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/autos/getByPersona/{personaId}")
    @Timed
    public ResponseEntity<List<AutoDTO>> getAutoByPersona(@PathVariable Long personaId) {
        log.debug("REST request to get Auto for Persona : {}", personaId);
        List<AutoDTO> autoDTOs = autoService.findByPersona(personaId);
        return ResponseEntity.ok().body(autoDTOs);
    }
}
