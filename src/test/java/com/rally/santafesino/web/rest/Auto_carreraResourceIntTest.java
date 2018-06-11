package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Auto_carrera;
import com.rally.santafesino.domain.Auto;
import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.repository.Auto_carreraRepository;
import com.rally.santafesino.service.Auto_carreraService;
import com.rally.santafesino.service.dto.Auto_carreraDTO;
import com.rally.santafesino.service.mapper.Auto_carreraMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.rally.santafesino.web.rest.TestUtil.sameInstant;
import static com.rally.santafesino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Auto_carreraResource REST controller.
 *
 * @see Auto_carreraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class Auto_carreraResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private Auto_carreraRepository auto_carreraRepository;

    @Autowired
    private Auto_carreraMapper auto_carreraMapper;

    @Autowired
    private Auto_carreraService auto_carreraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuto_carreraMockMvc;

    private Auto_carrera auto_carrera;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Auto_carreraResource auto_carreraResource = new Auto_carreraResource(auto_carreraService);
        this.restAuto_carreraMockMvc = MockMvcBuilders.standaloneSetup(auto_carreraResource)
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
    public static Auto_carrera createEntity(EntityManager em) {
        Auto_carrera auto_carrera = new Auto_carrera()
            .creation_date(DEFAULT_CREATION_DATE);
        // Add required entity
        Auto auto = AutoResourceIntTest.createEntity(em);
        em.persist(auto);
        em.flush();
        auto_carrera.setAuto(auto);
        // Add required entity
        Carrera carrera = CarreraResourceIntTest.createEntity(em);
        em.persist(carrera);
        em.flush();
        auto_carrera.setCarrera(carrera);
        return auto_carrera;
    }

    @Before
    public void initTest() {
        auto_carrera = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuto_carrera() throws Exception {
        int databaseSizeBeforeCreate = auto_carreraRepository.findAll().size();

        // Create the Auto_carrera
        Auto_carreraDTO auto_carreraDTO = auto_carreraMapper.toDto(auto_carrera);
        restAuto_carreraMockMvc.perform(post("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carreraDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto_carrera in the database
        List<Auto_carrera> auto_carreraList = auto_carreraRepository.findAll();
        assertThat(auto_carreraList).hasSize(databaseSizeBeforeCreate + 1);
        Auto_carrera testAuto_carrera = auto_carreraList.get(auto_carreraList.size() - 1);
        assertThat(testAuto_carrera.getCreation_date()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createAuto_carreraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auto_carreraRepository.findAll().size();

        // Create the Auto_carrera with an existing ID
        auto_carrera.setId(1L);
        Auto_carreraDTO auto_carreraDTO = auto_carreraMapper.toDto(auto_carrera);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuto_carreraMockMvc.perform(post("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carreraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auto_carrera in the database
        List<Auto_carrera> auto_carreraList = auto_carreraRepository.findAll();
        assertThat(auto_carreraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreation_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = auto_carreraRepository.findAll().size();
        // set the field null
        auto_carrera.setCreation_date(null);

        // Create the Auto_carrera, which fails.
        Auto_carreraDTO auto_carreraDTO = auto_carreraMapper.toDto(auto_carrera);

        restAuto_carreraMockMvc.perform(post("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carreraDTO)))
            .andExpect(status().isBadRequest());

        List<Auto_carrera> auto_carreraList = auto_carreraRepository.findAll();
        assertThat(auto_carreraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuto_carreras() throws Exception {
        // Initialize the database
        auto_carreraRepository.saveAndFlush(auto_carrera);

        // Get all the auto_carreraList
        restAuto_carreraMockMvc.perform(get("/api/auto-carreras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auto_carrera.getId().intValue())))
            .andExpect(jsonPath("$.[*].creation_date").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))));
    }

    @Test
    @Transactional
    public void getAuto_carrera() throws Exception {
        // Initialize the database
        auto_carreraRepository.saveAndFlush(auto_carrera);

        // Get the auto_carrera
        restAuto_carreraMockMvc.perform(get("/api/auto-carreras/{id}", auto_carrera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auto_carrera.getId().intValue()))
            .andExpect(jsonPath("$.creation_date").value(sameInstant(DEFAULT_CREATION_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAuto_carrera() throws Exception {
        // Get the auto_carrera
        restAuto_carreraMockMvc.perform(get("/api/auto-carreras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuto_carrera() throws Exception {
        // Initialize the database
        auto_carreraRepository.saveAndFlush(auto_carrera);
        int databaseSizeBeforeUpdate = auto_carreraRepository.findAll().size();

        // Update the auto_carrera
        Auto_carrera updatedAuto_carrera = auto_carreraRepository.findOne(auto_carrera.getId());
        // Disconnect from session so that the updates on updatedAuto_carrera are not directly saved in db
        em.detach(updatedAuto_carrera);
        updatedAuto_carrera
            .creation_date(UPDATED_CREATION_DATE);
        Auto_carreraDTO auto_carreraDTO = auto_carreraMapper.toDto(updatedAuto_carrera);

        restAuto_carreraMockMvc.perform(put("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carreraDTO)))
            .andExpect(status().isOk());

        // Validate the Auto_carrera in the database
        List<Auto_carrera> auto_carreraList = auto_carreraRepository.findAll();
        assertThat(auto_carreraList).hasSize(databaseSizeBeforeUpdate);
        Auto_carrera testAuto_carrera = auto_carreraList.get(auto_carreraList.size() - 1);
        assertThat(testAuto_carrera.getCreation_date()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAuto_carrera() throws Exception {
        int databaseSizeBeforeUpdate = auto_carreraRepository.findAll().size();

        // Create the Auto_carrera
        Auto_carreraDTO auto_carreraDTO = auto_carreraMapper.toDto(auto_carrera);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuto_carreraMockMvc.perform(put("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carreraDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto_carrera in the database
        List<Auto_carrera> auto_carreraList = auto_carreraRepository.findAll();
        assertThat(auto_carreraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuto_carrera() throws Exception {
        // Initialize the database
        auto_carreraRepository.saveAndFlush(auto_carrera);
        int databaseSizeBeforeDelete = auto_carreraRepository.findAll().size();

        // Get the auto_carrera
        restAuto_carreraMockMvc.perform(delete("/api/auto-carreras/{id}", auto_carrera.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Auto_carrera> auto_carreraList = auto_carreraRepository.findAll();
        assertThat(auto_carreraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auto_carrera.class);
        Auto_carrera auto_carrera1 = new Auto_carrera();
        auto_carrera1.setId(1L);
        Auto_carrera auto_carrera2 = new Auto_carrera();
        auto_carrera2.setId(auto_carrera1.getId());
        assertThat(auto_carrera1).isEqualTo(auto_carrera2);
        auto_carrera2.setId(2L);
        assertThat(auto_carrera1).isNotEqualTo(auto_carrera2);
        auto_carrera1.setId(null);
        assertThat(auto_carrera1).isNotEqualTo(auto_carrera2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auto_carreraDTO.class);
        Auto_carreraDTO auto_carreraDTO1 = new Auto_carreraDTO();
        auto_carreraDTO1.setId(1L);
        Auto_carreraDTO auto_carreraDTO2 = new Auto_carreraDTO();
        assertThat(auto_carreraDTO1).isNotEqualTo(auto_carreraDTO2);
        auto_carreraDTO2.setId(auto_carreraDTO1.getId());
        assertThat(auto_carreraDTO1).isEqualTo(auto_carreraDTO2);
        auto_carreraDTO2.setId(2L);
        assertThat(auto_carreraDTO1).isNotEqualTo(auto_carreraDTO2);
        auto_carreraDTO1.setId(null);
        assertThat(auto_carreraDTO1).isNotEqualTo(auto_carreraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(auto_carreraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(auto_carreraMapper.fromId(null)).isNull();
    }
}
