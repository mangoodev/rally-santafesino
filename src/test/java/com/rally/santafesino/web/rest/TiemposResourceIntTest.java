package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Tiempos;
import com.rally.santafesino.repository.TiemposRepository;
import com.rally.santafesino.service.TiemposService;
import com.rally.santafesino.service.dto.TiemposDTO;
import com.rally.santafesino.service.mapper.TiemposMapper;
import com.rally.santafesino.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.rally.santafesino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TiemposResource REST controller.
 *
 * @see TiemposResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class TiemposResourceIntTest {

    private static final Long DEFAULT_PARCIAL = 1L;
    private static final Long UPDATED_PARCIAL = 2L;

    @Autowired
    private TiemposRepository tiemposRepository;

    @Autowired
    private TiemposMapper tiemposMapper;

    @Autowired
    private TiemposService tiemposService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTiemposMockMvc;

    private Tiempos tiempos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TiemposResource tiemposResource = new TiemposResource(tiemposService);
        this.restTiemposMockMvc = MockMvcBuilders.standaloneSetup(tiemposResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tiempos createEntity(EntityManager em) {
        Tiempos tiempos = new Tiempos()
            .parcial(DEFAULT_PARCIAL);
        return tiempos;
    }

    @Before
    public void initTest() {
        tiempos = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiempos() throws Exception {
        int databaseSizeBeforeCreate = tiemposRepository.findAll().size();

        // Create the Tiempos
        TiemposDTO tiemposDTO = tiemposMapper.toDto(tiempos);
        restTiemposMockMvc.perform(post("/api/tiempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiemposDTO)))
            .andExpect(status().isCreated());

        // Validate the Tiempos in the database
        List<Tiempos> tiemposList = tiemposRepository.findAll();
        assertThat(tiemposList).hasSize(databaseSizeBeforeCreate + 1);
        Tiempos testTiempos = tiemposList.get(tiemposList.size() - 1);
        assertThat(testTiempos.getParcial()).isEqualTo(DEFAULT_PARCIAL);
    }

    @Test
    @Transactional
    public void createTiemposWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiemposRepository.findAll().size();

        // Create the Tiempos with an existing ID
        tiempos.setId(1L);
        TiemposDTO tiemposDTO = tiemposMapper.toDto(tiempos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiemposMockMvc.perform(post("/api/tiempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiemposDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tiempos in the database
        List<Tiempos> tiemposList = tiemposRepository.findAll();
        assertThat(tiemposList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkParcialIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiemposRepository.findAll().size();
        // set the field null
        tiempos.setParcial(null);

        // Create the Tiempos, which fails.
        TiemposDTO tiemposDTO = tiemposMapper.toDto(tiempos);

        restTiemposMockMvc.perform(post("/api/tiempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiemposDTO)))
            .andExpect(status().isBadRequest());

        List<Tiempos> tiemposList = tiemposRepository.findAll();
        assertThat(tiemposList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTiempos() throws Exception {
        // Initialize the database
        tiemposRepository.saveAndFlush(tiempos);

        // Get all the tiemposList
        restTiemposMockMvc.perform(get("/api/tiempos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiempos.getId().intValue())))
            .andExpect(jsonPath("$.[*].parcial").value(hasItem(DEFAULT_PARCIAL.intValue())));
    }

    @Test
    @Transactional
    public void getTiempos() throws Exception {
        // Initialize the database
        tiemposRepository.saveAndFlush(tiempos);

        // Get the tiempos
        restTiemposMockMvc.perform(get("/api/tiempos/{id}", tiempos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tiempos.getId().intValue()))
            .andExpect(jsonPath("$.parcial").value(DEFAULT_PARCIAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTiempos() throws Exception {
        // Get the tiempos
        restTiemposMockMvc.perform(get("/api/tiempos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiempos() throws Exception {
        // Initialize the database
        tiemposRepository.saveAndFlush(tiempos);
        int databaseSizeBeforeUpdate = tiemposRepository.findAll().size();

        // Update the tiempos
        Tiempos updatedTiempos = tiemposRepository.findOne(tiempos.getId());
        // Disconnect from session so that the updates on updatedTiempos are not directly saved in db
        em.detach(updatedTiempos);
        updatedTiempos
            .parcial(UPDATED_PARCIAL);
        TiemposDTO tiemposDTO = tiemposMapper.toDto(updatedTiempos);

        restTiemposMockMvc.perform(put("/api/tiempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiemposDTO)))
            .andExpect(status().isOk());

        // Validate the Tiempos in the database
        List<Tiempos> tiemposList = tiemposRepository.findAll();
        assertThat(tiemposList).hasSize(databaseSizeBeforeUpdate);
        Tiempos testTiempos = tiemposList.get(tiemposList.size() - 1);
        assertThat(testTiempos.getParcial()).isEqualTo(UPDATED_PARCIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingTiempos() throws Exception {
        int databaseSizeBeforeUpdate = tiemposRepository.findAll().size();

        // Create the Tiempos
        TiemposDTO tiemposDTO = tiemposMapper.toDto(tiempos);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTiemposMockMvc.perform(put("/api/tiempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiemposDTO)))
            .andExpect(status().isCreated());

        // Validate the Tiempos in the database
        List<Tiempos> tiemposList = tiemposRepository.findAll();
        assertThat(tiemposList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTiempos() throws Exception {
        // Initialize the database
        tiemposRepository.saveAndFlush(tiempos);
        int databaseSizeBeforeDelete = tiemposRepository.findAll().size();

        // Get the tiempos
        restTiemposMockMvc.perform(delete("/api/tiempos/{id}", tiempos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tiempos> tiemposList = tiemposRepository.findAll();
        assertThat(tiemposList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tiempos.class);
        Tiempos tiempos1 = new Tiempos();
        tiempos1.setId(1L);
        Tiempos tiempos2 = new Tiempos();
        tiempos2.setId(tiempos1.getId());
        assertThat(tiempos1).isEqualTo(tiempos2);
        tiempos2.setId(2L);
        assertThat(tiempos1).isNotEqualTo(tiempos2);
        tiempos1.setId(null);
        assertThat(tiempos1).isNotEqualTo(tiempos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiemposDTO.class);
        TiemposDTO tiemposDTO1 = new TiemposDTO();
        tiemposDTO1.setId(1L);
        TiemposDTO tiemposDTO2 = new TiemposDTO();
        assertThat(tiemposDTO1).isNotEqualTo(tiemposDTO2);
        tiemposDTO2.setId(tiemposDTO1.getId());
        assertThat(tiemposDTO1).isEqualTo(tiemposDTO2);
        tiemposDTO2.setId(2L);
        assertThat(tiemposDTO1).isNotEqualTo(tiemposDTO2);
        tiemposDTO1.setId(null);
        assertThat(tiemposDTO1).isNotEqualTo(tiemposDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tiemposMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tiemposMapper.fromId(null)).isNull();
    }
}
