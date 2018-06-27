package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.TiemposService;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.TiemposDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Tiempos.
 */
@RestController
@RequestMapping("/api")
public class TiemposResource {

    private final Logger log = LoggerFactory.getLogger(TiemposResource.class);

    private static final String ENTITY_NAME = "tiempos";

    private final TiemposService tiemposService;

    public TiemposResource(TiemposService tiemposService) {
        this.tiemposService = tiemposService;
    }

    /**
     * POST  /tiempos : Create a new tiempos.
     *
     * @param tiemposDTO the tiemposDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tiemposDTO, or with status 400 (Bad Request) if the tiempos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tiempos")
    @Timed
    public ResponseEntity<TiemposDTO> createTiempos(@Valid @RequestBody TiemposDTO tiemposDTO) throws URISyntaxException {
        log.debug("REST request to save Tiempos : {}", tiemposDTO);
        if (tiemposDTO.getId() != null) {
            throw new BadRequestAlertException("A new tiempos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TiemposDTO result = tiemposService.save(tiemposDTO);
        return ResponseEntity.created(new URI("/api/tiempos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tiempos : Updates an existing tiempos.
     *
     * @param tiemposDTO the tiemposDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tiemposDTO,
     * or with status 400 (Bad Request) if the tiemposDTO is not valid,
     * or with status 500 (Internal Server Error) if the tiemposDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tiempos")
    @Timed
    public ResponseEntity<TiemposDTO> updateTiempos(@Valid @RequestBody TiemposDTO tiemposDTO) throws URISyntaxException {
        log.debug("REST request to update Tiempos : {}", tiemposDTO);
        if (tiemposDTO.getId() == null) {
            return createTiempos(tiemposDTO);
        }
        TiemposDTO result = tiemposService.save(tiemposDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tiemposDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tiempos : get all the tiempos.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of tiempos in body
     */
    @GetMapping("/tiempos")
    @Timed
    public ResponseEntity<List<TiemposDTO>> getAllTiempos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("autotiempoprueba-is-null".equals(filter)) {
            log.debug("REST request to get all Tiemposs where autoTiempoPrueba is null");
            return new ResponseEntity<>(tiemposService.findAllWhereAutoTiempoPruebaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Tiempos");
        Page<TiemposDTO> page = tiemposService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tiempos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tiempos/:id : get the "id" tiempos.
     *
     * @param id the id of the tiemposDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tiemposDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tiempos/{id}")
    @Timed
    public ResponseEntity<TiemposDTO> getTiempos(@PathVariable Long id) {
        log.debug("REST request to get Tiempos : {}", id);
        TiemposDTO tiemposDTO = tiemposService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tiemposDTO));
    }

    /**
     * DELETE  /tiempos/:id : delete the "id" tiempos.
     *
     * @param id the id of the tiemposDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tiempos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTiempos(@PathVariable Long id) {
        log.debug("REST request to delete Tiempos : {}", id);
        tiemposService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
