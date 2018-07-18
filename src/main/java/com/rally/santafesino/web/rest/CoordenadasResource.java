package com.rally.santafesino.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rally.santafesino.service.CoordenadasService;
import com.rally.santafesino.service.dto.CarreraDTO;
import com.rally.santafesino.web.rest.errors.BadRequestAlertException;
import com.rally.santafesino.web.rest.util.HeaderUtil;
import com.rally.santafesino.web.rest.util.PaginationUtil;
import com.rally.santafesino.service.dto.CoordenadasDTO;
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
 * REST controller for managing Coordenadas.
 */
@RestController
@RequestMapping("/api")
public class CoordenadasResource {

    private final Logger log = LoggerFactory.getLogger(CoordenadasResource.class);

    private static final String ENTITY_NAME = "coordenadas";

    private final CoordenadasService coordenadasService;

    public CoordenadasResource(CoordenadasService coordenadasService) {
        this.coordenadasService = coordenadasService;
    }

    /**
     * POST  /coordenadas : Create a new coordenadas.
     *
     * @param coordenadasDTO the coordenadasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coordenadasDTO, or with status 400 (Bad Request) if the coordenadas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coordenadas")
    @Timed
    public ResponseEntity<CoordenadasDTO> createCoordenadas(@Valid @RequestBody CoordenadasDTO coordenadasDTO) throws URISyntaxException {
        log.debug("REST request to save Coordenadas : {}", coordenadasDTO);
        if (coordenadasDTO.getId() != null) {
            throw new BadRequestAlertException("A new coordenadas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoordenadasDTO result = coordenadasService.save(coordenadasDTO);
        return ResponseEntity.created(new URI("/api/coordenadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coordenadas : Updates an existing coordenadas.
     *
     * @param coordenadasDTO the coordenadasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coordenadasDTO,
     * or with status 400 (Bad Request) if the coordenadasDTO is not valid,
     * or with status 500 (Internal Server Error) if the coordenadasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coordenadas")
    @Timed
    public ResponseEntity<CoordenadasDTO> updateCoordenadas(@Valid @RequestBody CoordenadasDTO coordenadasDTO) throws URISyntaxException {
        log.debug("REST request to update Coordenadas : {}", coordenadasDTO);
        if (coordenadasDTO.getId() == null) {
            return createCoordenadas(coordenadasDTO);
        }
        CoordenadasDTO result = coordenadasService.save(coordenadasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coordenadasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coordenadas : get all the coordenadas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coordenadas in body
     */
    @GetMapping("/coordenadas")
    @Timed
    public ResponseEntity<List<CoordenadasDTO>> getAllCoordenadas(Pageable pageable) {
        log.debug("REST request to get a page of Coordenadas");
        Page<CoordenadasDTO> page = coordenadasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coordenadas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /coordenadas/:id : get the "id" coordenadas.
     *
     * @param id the id of the coordenadasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coordenadasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coordenadas/{id}")
    @Timed
    public ResponseEntity<CoordenadasDTO> getCoordenadas(@PathVariable Long id) {
        log.debug("REST request to get Coordenadas : {}", id);
        CoordenadasDTO coordenadasDTO = coordenadasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coordenadasDTO));
    }

    /**
     * DELETE  /coordenadas/:id : delete the "id" coordenadas.
     *
     * @param id the id of the coordenadasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coordenadas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoordenadas(@PathVariable Long id) {
        log.debug("REST request to delete Coordenadas : {}", id);
        coordenadasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/coordenadas/getRecorridoForCarrera/{carreraId}")
    @Timed
    public ResponseEntity<List<CoordenadasDTO>> getRecorridoForCarrera(@PathVariable Long carreraId) {
        List<CoordenadasDTO> carreraDTOs = coordenadasService.findRecorridoForCarrera(carreraId);
        return ResponseEntity.ok().body(carreraDTOs);
    }
}
