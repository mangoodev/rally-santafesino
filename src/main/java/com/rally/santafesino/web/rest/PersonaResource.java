package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.PersonaService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.PersonaDTO;
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
 * REST controller for managing Persona.
 */
@RestController
@RequestMapping("/api")
public class PersonaResource {

    private final Logger log = LoggerFactory.getLogger(PersonaResource.class);

    private static final String ENTITY_NAME = "persona";

    private final PersonaService personaService;

    public PersonaResource(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * POST  /personas : Create a new persona.
     *
     * @param personaDTO the personaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personaDTO, or with status 400 (Bad Request) if the persona has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personas")
    @Timed
    public ResponseEntity<PersonaDTO> createPersona(@Valid @RequestBody PersonaDTO personaDTO) throws URISyntaxException {
        log.debug("REST request to save Persona : {}", personaDTO);
        if (personaDTO.getId() != null) {
            throw new BadRequestAlertException("A new persona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonaDTO result = personaService.save(personaDTO);
        return ResponseEntity.created(new URI("/api/personas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personas : Updates an existing persona.
     *
     * @param personaDTO the personaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personaDTO,
     * or with status 400 (Bad Request) if the personaDTO is not valid,
     * or with status 500 (Internal Server Error) if the personaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personas")
    @Timed
    public ResponseEntity<PersonaDTO> updatePersona(@Valid @RequestBody PersonaDTO personaDTO) throws URISyntaxException {
        log.debug("REST request to update Persona : {}", personaDTO);
        if (personaDTO.getId() == null) {
            return createPersona(personaDTO);
        }
        PersonaDTO result = personaService.save(personaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personas : get all the personas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personas in body
     */
    @GetMapping("/personas")
    @Timed
    public ResponseEntity<List<PersonaDTO>> getAllPersonas(Pageable pageable) {
        log.debug("REST request to get a page of Personas");
        Page<PersonaDTO> page = personaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personas/:id : get the "id" persona.
     *
     * @param id the id of the personaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/personas/{id}")
    @Timed
    public ResponseEntity<PersonaDTO> getPersona(@PathVariable Long id) {
        log.debug("REST request to get Persona : {}", id);
        PersonaDTO personaDTO = personaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personaDTO));
    }

    /**
     * DELETE  /personas/:id : delete the "id" persona.
     *
     * @param id the id of the personaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personas/{id}")
    @Timed
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        log.debug("REST request to delete Persona : {}", id);
        personaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
