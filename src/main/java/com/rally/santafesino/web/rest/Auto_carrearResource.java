package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.Auto_carrearService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.Auto_carrearDTO;
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
 * REST controller for managing Auto_carrear.
 */
@RestController
@RequestMapping("/api")
public class Auto_carrearResource {

    private final Logger log = LoggerFactory.getLogger(Auto_carrearResource.class);

    private static final String ENTITY_NAME = "auto_carrear";

    private final Auto_carrearService auto_carrearService;

    public Auto_carrearResource(Auto_carrearService auto_carrearService) {
        this.auto_carrearService = auto_carrearService;
    }

    /**
     * POST  /auto-carrears : Create a new auto_carrear.
     *
     * @param auto_carrearDTO the auto_carrearDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auto_carrearDTO, or with status 400 (Bad Request) if the auto_carrear has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auto-carrears")
    @Timed
    public ResponseEntity<Auto_carrearDTO> createAuto_carrear(@RequestBody Auto_carrearDTO auto_carrearDTO) throws URISyntaxException {
        log.debug("REST request to save Auto_carrear : {}", auto_carrearDTO);
        if (auto_carrearDTO.getId() != null) {
            throw new BadRequestAlertException("A new auto_carrear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Auto_carrearDTO result = auto_carrearService.save(auto_carrearDTO);
        return ResponseEntity.created(new URI("/api/auto-carrears/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auto-carrears : Updates an existing auto_carrear.
     *
     * @param auto_carrearDTO the auto_carrearDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auto_carrearDTO,
     * or with status 400 (Bad Request) if the auto_carrearDTO is not valid,
     * or with status 500 (Internal Server Error) if the auto_carrearDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auto-carrears")
    @Timed
    public ResponseEntity<Auto_carrearDTO> updateAuto_carrear(@RequestBody Auto_carrearDTO auto_carrearDTO) throws URISyntaxException {
        log.debug("REST request to update Auto_carrear : {}", auto_carrearDTO);
        if (auto_carrearDTO.getId() == null) {
            return createAuto_carrear(auto_carrearDTO);
        }
        Auto_carrearDTO result = auto_carrearService.save(auto_carrearDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auto_carrearDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auto-carrears : get all the auto_carrears.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auto_carrears in body
     */
    @GetMapping("/auto-carrears")
    @Timed
    public ResponseEntity<List<Auto_carrearDTO>> getAllAuto_carrears(Pageable pageable) {
        log.debug("REST request to get a page of Auto_carrears");
        Page<Auto_carrearDTO> page = auto_carrearService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auto-carrears");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /auto-carrears/:id : get the "id" auto_carrear.
     *
     * @param id the id of the auto_carrearDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auto_carrearDTO, or with status 404 (Not Found)
     */
    @GetMapping("/auto-carrears/{id}")
    @Timed
    public ResponseEntity<Auto_carrearDTO> getAuto_carrear(@PathVariable Long id) {
        log.debug("REST request to get Auto_carrear : {}", id);
        Auto_carrearDTO auto_carrearDTO = auto_carrearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auto_carrearDTO));
    }

    /**
     * DELETE  /auto-carrears/:id : delete the "id" auto_carrear.
     *
     * @param id the id of the auto_carrearDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auto-carrears/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuto_carrear(@PathVariable Long id) {
        log.debug("REST request to delete Auto_carrear : {}", id);
        auto_carrearService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
