package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Carrera_etapa;
import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.domain.Etapa;
import com.rally.santafesino.repository.Carrera_etapaRepository;
import com.rally.santafesino.service.Carrera_etapaService;
import com.rally.santafesino.service.dto.Carrera_etapaDTO;
import com.rally.santafesino.service.mapper.Carrera_etapaMapper;
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
 * Test class for the Carrera_etapaResource REST controller.
 *
 * @see Carrera_etapaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class Carrera_etapaResourceIntTest {

    @Autowired
    private Carrera_etapaRepository carrera_etapaRepository;

    @Autowired
    private Carrera_etapaMapper carrera_etapaMapper;

    @Autowired
    private Carrera_etapaService carrera_etapaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarrera_etapaMockMvc;

    private Carrera_etapa carrera_etapa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Carrera_etapaResource carrera_etapaResource = new Carrera_etapaResource(carrera_etapaService);
        this.restCarrera_etapaMockMvc = MockMvcBuilders.standaloneSetup(carrera_etapaResource)
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
    public static Carrera_etapa createEntity(EntityManager em) {
        Carrera_etapa carrera_etapa = new Carrera_etapa();
        // Add required entity
        Carrera id_carrera = CarreraResourceIntTest.createEntity(em);
        em.persist(id_carrera);
        em.flush();
        carrera_etapa.setId_carrera(id_carrera);
        // Add required entity
        Etapa id_etapa = EtapaResourceIntTest.createEntity(em);
        em.persist(id_etapa);
        em.flush();
        carrera_etapa.setId_etapa(id_etapa);
        return carrera_etapa;
    }

    @Before
    public void initTest() {
        carrera_etapa = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarrera_etapa() throws Exception {
        int databaseSizeBeforeCreate = carrera_etapaRepository.findAll().size();

        // Create the Carrera_etapa
        Carrera_etapaDTO carrera_etapaDTO = carrera_etapaMapper.toDto(carrera_etapa);
        restCarrera_etapaMockMvc.perform(post("/api/carrera-etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrera_etapaDTO)))
            .andExpect(status().isCreated());

        // Validate the Carrera_etapa in the database
        List<Carrera_etapa> carrera_etapaList = carrera_etapaRepository.findAll();
        assertThat(carrera_etapaList).hasSize(databaseSizeBeforeCreate + 1);
        Carrera_etapa testCarrera_etapa = carrera_etapaList.get(carrera_etapaList.size() - 1);
    }

    @Test
    @Transactional
    public void createCarrera_etapaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carrera_etapaRepository.findAll().size();

        // Create the Carrera_etapa with an existing ID
        carrera_etapa.setId(1L);
        Carrera_etapaDTO carrera_etapaDTO = carrera_etapaMapper.toDto(carrera_etapa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrera_etapaMockMvc.perform(post("/api/carrera-etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrera_etapaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carrera_etapa in the database
        List<Carrera_etapa> carrera_etapaList = carrera_etapaRepository.findAll();
        assertThat(carrera_etapaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCarrera_etapas() throws Exception {
        // Initialize the database
        carrera_etapaRepository.saveAndFlush(carrera_etapa);

        // Get all the carrera_etapaList
        restCarrera_etapaMockMvc.perform(get("/api/carrera-etapas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrera_etapa.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCarrera_etapa() throws Exception {
        // Initialize the database
        carrera_etapaRepository.saveAndFlush(carrera_etapa);

        // Get the carrera_etapa
        restCarrera_etapaMockMvc.perform(get("/api/carrera-etapas/{id}", carrera_etapa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carrera_etapa.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarrera_etapa() throws Exception {
        // Get the carrera_etapa
        restCarrera_etapaMockMvc.perform(get("/api/carrera-etapas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarrera_etapa() throws Exception {
        // Initialize the database
        carrera_etapaRepository.saveAndFlush(carrera_etapa);
        int databaseSizeBeforeUpdate = carrera_etapaRepository.findAll().size();

        // Update the carrera_etapa
        Carrera_etapa updatedCarrera_etapa = carrera_etapaRepository.findOne(carrera_etapa.getId());
        // Disconnect from session so that the updates on updatedCarrera_etapa are not directly saved in db
        em.detach(updatedCarrera_etapa);
        Carrera_etapaDTO carrera_etapaDTO = carrera_etapaMapper.toDto(updatedCarrera_etapa);

        restCarrera_etapaMockMvc.perform(put("/api/carrera-etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrera_etapaDTO)))
            .andExpect(status().isOk());

        // Validate the Carrera_etapa in the database
        List<Carrera_etapa> carrera_etapaList = carrera_etapaRepository.findAll();
        assertThat(carrera_etapaList).hasSize(databaseSizeBeforeUpdate);
        Carrera_etapa testCarrera_etapa = carrera_etapaList.get(carrera_etapaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCarrera_etapa() throws Exception {
        int databaseSizeBeforeUpdate = carrera_etapaRepository.findAll().size();

        // Create the Carrera_etapa
        Carrera_etapaDTO carrera_etapaDTO = carrera_etapaMapper.toDto(carrera_etapa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarrera_etapaMockMvc.perform(put("/api/carrera-etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrera_etapaDTO)))
            .andExpect(status().isCreated());

        // Validate the Carrera_etapa in the database
        List<Carrera_etapa> carrera_etapaList = carrera_etapaRepository.findAll();
        assertThat(carrera_etapaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarrera_etapa() throws Exception {
        // Initialize the database
        carrera_etapaRepository.saveAndFlush(carrera_etapa);
        int databaseSizeBeforeDelete = carrera_etapaRepository.findAll().size();

        // Get the carrera_etapa
        restCarrera_etapaMockMvc.perform(delete("/api/carrera-etapas/{id}", carrera_etapa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Carrera_etapa> carrera_etapaList = carrera_etapaRepository.findAll();
        assertThat(carrera_etapaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carrera_etapa.class);
        Carrera_etapa carrera_etapa1 = new Carrera_etapa();
        carrera_etapa1.setId(1L);
        Carrera_etapa carrera_etapa2 = new Carrera_etapa();
        carrera_etapa2.setId(carrera_etapa1.getId());
        assertThat(carrera_etapa1).isEqualTo(carrera_etapa2);
        carrera_etapa2.setId(2L);
        assertThat(carrera_etapa1).isNotEqualTo(carrera_etapa2);
        carrera_etapa1.setId(null);
        assertThat(carrera_etapa1).isNotEqualTo(carrera_etapa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carrera_etapaDTO.class);
        Carrera_etapaDTO carrera_etapaDTO1 = new Carrera_etapaDTO();
        carrera_etapaDTO1.setId(1L);
        Carrera_etapaDTO carrera_etapaDTO2 = new Carrera_etapaDTO();
        assertThat(carrera_etapaDTO1).isNotEqualTo(carrera_etapaDTO2);
        carrera_etapaDTO2.setId(carrera_etapaDTO1.getId());
        assertThat(carrera_etapaDTO1).isEqualTo(carrera_etapaDTO2);
        carrera_etapaDTO2.setId(2L);
        assertThat(carrera_etapaDTO1).isNotEqualTo(carrera_etapaDTO2);
        carrera_etapaDTO1.setId(null);
        assertThat(carrera_etapaDTO1).isNotEqualTo(carrera_etapaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carrera_etapaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carrera_etapaMapper.fromId(null)).isNull();
    }
}
