package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.CarreraEtapa;
import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.domain.Etapa;
import com.rally.santafesino.repository.CarreraEtapaRepository;
import com.rally.santafesino.service.CarreraEtapaService;
import com.rally.santafesino.service.dto.CarreraEtapaDTO;
import com.rally.santafesino.service.mapper.CarreraEtapaMapper;
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
 * Test class for the CarreraEtapaResource REST controller.
 *
 * @see CarreraEtapaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class CarreraEtapaResourceIntTest {

    @Autowired
    private CarreraEtapaRepository carreraEtapaRepository;

    @Autowired
    private CarreraEtapaMapper carreraEtapaMapper;

    @Autowired
    private CarreraEtapaService carreraEtapaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarreraEtapaMockMvc;

    private CarreraEtapa carreraEtapa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarreraEtapaResource carreraEtapaResource = new CarreraEtapaResource(carreraEtapaService);
        this.restCarreraEtapaMockMvc = MockMvcBuilders.standaloneSetup(carreraEtapaResource)
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
    public static CarreraEtapa createEntity(EntityManager em) {
        CarreraEtapa carreraEtapa = new CarreraEtapa();
        // Add required entity
        Carrera id_carrera = CarreraResourceIntTest.createEntity(em);
        em.persist(id_carrera);
        em.flush();
        carreraEtapa.setId_carrera(id_carrera);
        // Add required entity
        Etapa id_etapa = EtapaResourceIntTest.createEntity(em);
        em.persist(id_etapa);
        em.flush();
        carreraEtapa.setId_etapa(id_etapa);
        return carreraEtapa;
    }

    @Before
    public void initTest() {
        carreraEtapa = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarreraEtapa() throws Exception {
        int databaseSizeBeforeCreate = carreraEtapaRepository.findAll().size();

        // Create the CarreraEtapa
        CarreraEtapaDTO carreraEtapaDTO = carreraEtapaMapper.toDto(carreraEtapa);
        restCarreraEtapaMockMvc.perform(post("/api/carrera-etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraEtapaDTO)))
            .andExpect(status().isCreated());

        // Validate the CarreraEtapa in the database
        List<CarreraEtapa> carreraEtapaList = carreraEtapaRepository.findAll();
        assertThat(carreraEtapaList).hasSize(databaseSizeBeforeCreate + 1);
        CarreraEtapa testCarreraEtapa = carreraEtapaList.get(carreraEtapaList.size() - 1);
    }

    @Test
    @Transactional
    public void createCarreraEtapaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carreraEtapaRepository.findAll().size();

        // Create the CarreraEtapa with an existing ID
        carreraEtapa.setId(1L);
        CarreraEtapaDTO carreraEtapaDTO = carreraEtapaMapper.toDto(carreraEtapa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarreraEtapaMockMvc.perform(post("/api/carrera-etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraEtapaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarreraEtapa in the database
        List<CarreraEtapa> carreraEtapaList = carreraEtapaRepository.findAll();
        assertThat(carreraEtapaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCarreraEtapas() throws Exception {
        // Initialize the database
        carreraEtapaRepository.saveAndFlush(carreraEtapa);

        // Get all the carreraEtapaList
        restCarreraEtapaMockMvc.perform(get("/api/carrera-etapas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carreraEtapa.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCarreraEtapa() throws Exception {
        // Initialize the database
        carreraEtapaRepository.saveAndFlush(carreraEtapa);

        // Get the carreraEtapa
        restCarreraEtapaMockMvc.perform(get("/api/carrera-etapas/{id}", carreraEtapa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carreraEtapa.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarreraEtapa() throws Exception {
        // Get the carreraEtapa
        restCarreraEtapaMockMvc.perform(get("/api/carrera-etapas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarreraEtapa() throws Exception {
        // Initialize the database
        carreraEtapaRepository.saveAndFlush(carreraEtapa);
        int databaseSizeBeforeUpdate = carreraEtapaRepository.findAll().size();

        // Update the carreraEtapa
        CarreraEtapa updatedCarreraEtapa = carreraEtapaRepository.findOne(carreraEtapa.getId());
        // Disconnect from session so that the updates on updatedCarreraEtapa are not directly saved in db
        em.detach(updatedCarreraEtapa);
        CarreraEtapaDTO carreraEtapaDTO = carreraEtapaMapper.toDto(updatedCarreraEtapa);

        restCarreraEtapaMockMvc.perform(put("/api/carrera-etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraEtapaDTO)))
            .andExpect(status().isOk());

        // Validate the CarreraEtapa in the database
        List<CarreraEtapa> carreraEtapaList = carreraEtapaRepository.findAll();
        assertThat(carreraEtapaList).hasSize(databaseSizeBeforeUpdate);
        CarreraEtapa testCarreraEtapa = carreraEtapaList.get(carreraEtapaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCarreraEtapa() throws Exception {
        int databaseSizeBeforeUpdate = carreraEtapaRepository.findAll().size();

        // Create the CarreraEtapa
        CarreraEtapaDTO carreraEtapaDTO = carreraEtapaMapper.toDto(carreraEtapa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarreraEtapaMockMvc.perform(put("/api/carrera-etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraEtapaDTO)))
            .andExpect(status().isCreated());

        // Validate the CarreraEtapa in the database
        List<CarreraEtapa> carreraEtapaList = carreraEtapaRepository.findAll();
        assertThat(carreraEtapaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarreraEtapa() throws Exception {
        // Initialize the database
        carreraEtapaRepository.saveAndFlush(carreraEtapa);
        int databaseSizeBeforeDelete = carreraEtapaRepository.findAll().size();

        // Get the carreraEtapa
        restCarreraEtapaMockMvc.perform(delete("/api/carrera-etapas/{id}", carreraEtapa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarreraEtapa> carreraEtapaList = carreraEtapaRepository.findAll();
        assertThat(carreraEtapaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarreraEtapa.class);
        CarreraEtapa carreraEtapa1 = new CarreraEtapa();
        carreraEtapa1.setId(1L);
        CarreraEtapa carreraEtapa2 = new CarreraEtapa();
        carreraEtapa2.setId(carreraEtapa1.getId());
        assertThat(carreraEtapa1).isEqualTo(carreraEtapa2);
        carreraEtapa2.setId(2L);
        assertThat(carreraEtapa1).isNotEqualTo(carreraEtapa2);
        carreraEtapa1.setId(null);
        assertThat(carreraEtapa1).isNotEqualTo(carreraEtapa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarreraEtapaDTO.class);
        CarreraEtapaDTO carreraEtapaDTO1 = new CarreraEtapaDTO();
        carreraEtapaDTO1.setId(1L);
        CarreraEtapaDTO carreraEtapaDTO2 = new CarreraEtapaDTO();
        assertThat(carreraEtapaDTO1).isNotEqualTo(carreraEtapaDTO2);
        carreraEtapaDTO2.setId(carreraEtapaDTO1.getId());
        assertThat(carreraEtapaDTO1).isEqualTo(carreraEtapaDTO2);
        carreraEtapaDTO2.setId(2L);
        assertThat(carreraEtapaDTO1).isNotEqualTo(carreraEtapaDTO2);
        carreraEtapaDTO1.setId(null);
        assertThat(carreraEtapaDTO1).isNotEqualTo(carreraEtapaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carreraEtapaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carreraEtapaMapper.fromId(null)).isNull();
    }
}
