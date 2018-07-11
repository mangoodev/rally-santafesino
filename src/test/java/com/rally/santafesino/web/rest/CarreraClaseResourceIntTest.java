package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.CarreraClase;
import com.rally.santafesino.repository.CarreraClaseRepository;
import com.rally.santafesino.service.CarreraClaseService;
import com.rally.santafesino.service.dto.CarreraClaseDTO;
import com.rally.santafesino.service.mapper.CarreraClaseMapper;
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
 * Test class for the CarreraClaseResource REST controller.
 *
 * @see CarreraClaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class CarreraClaseResourceIntTest {

    @Autowired
    private CarreraClaseRepository carreraClaseRepository;

    @Autowired
    private CarreraClaseMapper carreraClaseMapper;

    @Autowired
    private CarreraClaseService carreraClaseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarreraClaseMockMvc;

    private CarreraClase carreraClase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarreraClaseResource carreraClaseResource = new CarreraClaseResource(carreraClaseService);
        this.restCarreraClaseMockMvc = MockMvcBuilders.standaloneSetup(carreraClaseResource)
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
    public static CarreraClase createEntity(EntityManager em) {
        CarreraClase carreraClase = new CarreraClase();
        return carreraClase;
    }

    @Before
    public void initTest() {
        carreraClase = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarreraClase() throws Exception {
        int databaseSizeBeforeCreate = carreraClaseRepository.findAll().size();

        // Create the CarreraClase
        CarreraClaseDTO carreraClaseDTO = carreraClaseMapper.toDto(carreraClase);
        restCarreraClaseMockMvc.perform(post("/api/carrera-clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraClaseDTO)))
            .andExpect(status().isCreated());

        // Validate the CarreraClase in the database
        List<CarreraClase> carreraClaseList = carreraClaseRepository.findAll();
        assertThat(carreraClaseList).hasSize(databaseSizeBeforeCreate + 1);
        CarreraClase testCarreraClase = carreraClaseList.get(carreraClaseList.size() - 1);
    }

    @Test
    @Transactional
    public void createCarreraClaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carreraClaseRepository.findAll().size();

        // Create the CarreraClase with an existing ID
        carreraClase.setId(1L);
        CarreraClaseDTO carreraClaseDTO = carreraClaseMapper.toDto(carreraClase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarreraClaseMockMvc.perform(post("/api/carrera-clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraClaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarreraClase in the database
        List<CarreraClase> carreraClaseList = carreraClaseRepository.findAll();
        assertThat(carreraClaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCarreraClases() throws Exception {
        // Initialize the database
        carreraClaseRepository.saveAndFlush(carreraClase);

        // Get all the carreraClaseList
        restCarreraClaseMockMvc.perform(get("/api/carrera-clases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carreraClase.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCarreraClase() throws Exception {
        // Initialize the database
        carreraClaseRepository.saveAndFlush(carreraClase);

        // Get the carreraClase
        restCarreraClaseMockMvc.perform(get("/api/carrera-clases/{id}", carreraClase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carreraClase.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarreraClase() throws Exception {
        // Get the carreraClase
        restCarreraClaseMockMvc.perform(get("/api/carrera-clases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarreraClase() throws Exception {
        // Initialize the database
        carreraClaseRepository.saveAndFlush(carreraClase);
        int databaseSizeBeforeUpdate = carreraClaseRepository.findAll().size();

        // Update the carreraClase
        CarreraClase updatedCarreraClase = carreraClaseRepository.findOne(carreraClase.getId());
        // Disconnect from session so that the updates on updatedCarreraClase are not directly saved in db
        em.detach(updatedCarreraClase);
        CarreraClaseDTO carreraClaseDTO = carreraClaseMapper.toDto(updatedCarreraClase);

        restCarreraClaseMockMvc.perform(put("/api/carrera-clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraClaseDTO)))
            .andExpect(status().isOk());

        // Validate the CarreraClase in the database
        List<CarreraClase> carreraClaseList = carreraClaseRepository.findAll();
        assertThat(carreraClaseList).hasSize(databaseSizeBeforeUpdate);
        CarreraClase testCarreraClase = carreraClaseList.get(carreraClaseList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCarreraClase() throws Exception {
        int databaseSizeBeforeUpdate = carreraClaseRepository.findAll().size();

        // Create the CarreraClase
        CarreraClaseDTO carreraClaseDTO = carreraClaseMapper.toDto(carreraClase);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarreraClaseMockMvc.perform(put("/api/carrera-clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraClaseDTO)))
            .andExpect(status().isCreated());

        // Validate the CarreraClase in the database
        List<CarreraClase> carreraClaseList = carreraClaseRepository.findAll();
        assertThat(carreraClaseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarreraClase() throws Exception {
        // Initialize the database
        carreraClaseRepository.saveAndFlush(carreraClase);
        int databaseSizeBeforeDelete = carreraClaseRepository.findAll().size();

        // Get the carreraClase
        restCarreraClaseMockMvc.perform(delete("/api/carrera-clases/{id}", carreraClase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarreraClase> carreraClaseList = carreraClaseRepository.findAll();
        assertThat(carreraClaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarreraClase.class);
        CarreraClase carreraClase1 = new CarreraClase();
        carreraClase1.setId(1L);
        CarreraClase carreraClase2 = new CarreraClase();
        carreraClase2.setId(carreraClase1.getId());
        assertThat(carreraClase1).isEqualTo(carreraClase2);
        carreraClase2.setId(2L);
        assertThat(carreraClase1).isNotEqualTo(carreraClase2);
        carreraClase1.setId(null);
        assertThat(carreraClase1).isNotEqualTo(carreraClase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarreraClaseDTO.class);
        CarreraClaseDTO carreraClaseDTO1 = new CarreraClaseDTO();
        carreraClaseDTO1.setId(1L);
        CarreraClaseDTO carreraClaseDTO2 = new CarreraClaseDTO();
        assertThat(carreraClaseDTO1).isNotEqualTo(carreraClaseDTO2);
        carreraClaseDTO2.setId(carreraClaseDTO1.getId());
        assertThat(carreraClaseDTO1).isEqualTo(carreraClaseDTO2);
        carreraClaseDTO2.setId(2L);
        assertThat(carreraClaseDTO1).isNotEqualTo(carreraClaseDTO2);
        carreraClaseDTO1.setId(null);
        assertThat(carreraClaseDTO1).isNotEqualTo(carreraClaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carreraClaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carreraClaseMapper.fromId(null)).isNull();
    }
}
