package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Auto_tiempo_prueba;
import com.rally.santafesino.domain.Auto;
import com.rally.santafesino.domain.Tiempos;
import com.rally.santafesino.domain.Pruebas;
import com.rally.santafesino.repository.Auto_tiempo_pruebaRepository;
import com.rally.santafesino.service.Auto_tiempo_pruebaService;
import com.rally.santafesino.service.dto.Auto_tiempo_pruebaDTO;
import com.rally.santafesino.service.mapper.Auto_tiempo_pruebaMapper;
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
 * Test class for the Auto_tiempo_pruebaResource REST controller.
 *
 * @see Auto_tiempo_pruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class Auto_tiempo_pruebaResourceIntTest {

    @Autowired
    private Auto_tiempo_pruebaRepository auto_tiempo_pruebaRepository;

    @Autowired
    private Auto_tiempo_pruebaMapper auto_tiempo_pruebaMapper;

    @Autowired
    private Auto_tiempo_pruebaService auto_tiempo_pruebaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuto_tiempo_pruebaMockMvc;

    private Auto_tiempo_prueba auto_tiempo_prueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Auto_tiempo_pruebaResource auto_tiempo_pruebaResource = new Auto_tiempo_pruebaResource(auto_tiempo_pruebaService);
        this.restAuto_tiempo_pruebaMockMvc = MockMvcBuilders.standaloneSetup(auto_tiempo_pruebaResource)
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
    public static Auto_tiempo_prueba createEntity(EntityManager em) {
        Auto_tiempo_prueba auto_tiempo_prueba = new Auto_tiempo_prueba();
        // Add required entity
        Auto id_auto = AutoResourceIntTest.createEntity(em);
        em.persist(id_auto);
        em.flush();
        auto_tiempo_prueba.setId_auto(id_auto);
        // Add required entity
        Tiempos id_tiempos = TiemposResourceIntTest.createEntity(em);
        em.persist(id_tiempos);
        em.flush();
        auto_tiempo_prueba.setId_tiempos(id_tiempos);
        // Add required entity
        Pruebas id_prueba = PruebasResourceIntTest.createEntity(em);
        em.persist(id_prueba);
        em.flush();
        auto_tiempo_prueba.setId_prueba(id_prueba);
        return auto_tiempo_prueba;
    }

    @Before
    public void initTest() {
        auto_tiempo_prueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuto_tiempo_prueba() throws Exception {
        int databaseSizeBeforeCreate = auto_tiempo_pruebaRepository.findAll().size();

        // Create the Auto_tiempo_prueba
        Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO = auto_tiempo_pruebaMapper.toDto(auto_tiempo_prueba);
        restAuto_tiempo_pruebaMockMvc.perform(post("/api/auto-tiempo-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_tiempo_pruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto_tiempo_prueba in the database
        List<Auto_tiempo_prueba> auto_tiempo_pruebaList = auto_tiempo_pruebaRepository.findAll();
        assertThat(auto_tiempo_pruebaList).hasSize(databaseSizeBeforeCreate + 1);
        Auto_tiempo_prueba testAuto_tiempo_prueba = auto_tiempo_pruebaList.get(auto_tiempo_pruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void createAuto_tiempo_pruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auto_tiempo_pruebaRepository.findAll().size();

        // Create the Auto_tiempo_prueba with an existing ID
        auto_tiempo_prueba.setId(1L);
        Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO = auto_tiempo_pruebaMapper.toDto(auto_tiempo_prueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuto_tiempo_pruebaMockMvc.perform(post("/api/auto-tiempo-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_tiempo_pruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auto_tiempo_prueba in the database
        List<Auto_tiempo_prueba> auto_tiempo_pruebaList = auto_tiempo_pruebaRepository.findAll();
        assertThat(auto_tiempo_pruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuto_tiempo_pruebas() throws Exception {
        // Initialize the database
        auto_tiempo_pruebaRepository.saveAndFlush(auto_tiempo_prueba);

        // Get all the auto_tiempo_pruebaList
        restAuto_tiempo_pruebaMockMvc.perform(get("/api/auto-tiempo-pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auto_tiempo_prueba.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAuto_tiempo_prueba() throws Exception {
        // Initialize the database
        auto_tiempo_pruebaRepository.saveAndFlush(auto_tiempo_prueba);

        // Get the auto_tiempo_prueba
        restAuto_tiempo_pruebaMockMvc.perform(get("/api/auto-tiempo-pruebas/{id}", auto_tiempo_prueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auto_tiempo_prueba.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuto_tiempo_prueba() throws Exception {
        // Get the auto_tiempo_prueba
        restAuto_tiempo_pruebaMockMvc.perform(get("/api/auto-tiempo-pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuto_tiempo_prueba() throws Exception {
        // Initialize the database
        auto_tiempo_pruebaRepository.saveAndFlush(auto_tiempo_prueba);
        int databaseSizeBeforeUpdate = auto_tiempo_pruebaRepository.findAll().size();

        // Update the auto_tiempo_prueba
        Auto_tiempo_prueba updatedAuto_tiempo_prueba = auto_tiempo_pruebaRepository.findOne(auto_tiempo_prueba.getId());
        // Disconnect from session so that the updates on updatedAuto_tiempo_prueba are not directly saved in db
        em.detach(updatedAuto_tiempo_prueba);
        Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO = auto_tiempo_pruebaMapper.toDto(updatedAuto_tiempo_prueba);

        restAuto_tiempo_pruebaMockMvc.perform(put("/api/auto-tiempo-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_tiempo_pruebaDTO)))
            .andExpect(status().isOk());

        // Validate the Auto_tiempo_prueba in the database
        List<Auto_tiempo_prueba> auto_tiempo_pruebaList = auto_tiempo_pruebaRepository.findAll();
        assertThat(auto_tiempo_pruebaList).hasSize(databaseSizeBeforeUpdate);
        Auto_tiempo_prueba testAuto_tiempo_prueba = auto_tiempo_pruebaList.get(auto_tiempo_pruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAuto_tiempo_prueba() throws Exception {
        int databaseSizeBeforeUpdate = auto_tiempo_pruebaRepository.findAll().size();

        // Create the Auto_tiempo_prueba
        Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO = auto_tiempo_pruebaMapper.toDto(auto_tiempo_prueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuto_tiempo_pruebaMockMvc.perform(put("/api/auto-tiempo-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_tiempo_pruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto_tiempo_prueba in the database
        List<Auto_tiempo_prueba> auto_tiempo_pruebaList = auto_tiempo_pruebaRepository.findAll();
        assertThat(auto_tiempo_pruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuto_tiempo_prueba() throws Exception {
        // Initialize the database
        auto_tiempo_pruebaRepository.saveAndFlush(auto_tiempo_prueba);
        int databaseSizeBeforeDelete = auto_tiempo_pruebaRepository.findAll().size();

        // Get the auto_tiempo_prueba
        restAuto_tiempo_pruebaMockMvc.perform(delete("/api/auto-tiempo-pruebas/{id}", auto_tiempo_prueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Auto_tiempo_prueba> auto_tiempo_pruebaList = auto_tiempo_pruebaRepository.findAll();
        assertThat(auto_tiempo_pruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auto_tiempo_prueba.class);
        Auto_tiempo_prueba auto_tiempo_prueba1 = new Auto_tiempo_prueba();
        auto_tiempo_prueba1.setId(1L);
        Auto_tiempo_prueba auto_tiempo_prueba2 = new Auto_tiempo_prueba();
        auto_tiempo_prueba2.setId(auto_tiempo_prueba1.getId());
        assertThat(auto_tiempo_prueba1).isEqualTo(auto_tiempo_prueba2);
        auto_tiempo_prueba2.setId(2L);
        assertThat(auto_tiempo_prueba1).isNotEqualTo(auto_tiempo_prueba2);
        auto_tiempo_prueba1.setId(null);
        assertThat(auto_tiempo_prueba1).isNotEqualTo(auto_tiempo_prueba2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auto_tiempo_pruebaDTO.class);
        Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO1 = new Auto_tiempo_pruebaDTO();
        auto_tiempo_pruebaDTO1.setId(1L);
        Auto_tiempo_pruebaDTO auto_tiempo_pruebaDTO2 = new Auto_tiempo_pruebaDTO();
        assertThat(auto_tiempo_pruebaDTO1).isNotEqualTo(auto_tiempo_pruebaDTO2);
        auto_tiempo_pruebaDTO2.setId(auto_tiempo_pruebaDTO1.getId());
        assertThat(auto_tiempo_pruebaDTO1).isEqualTo(auto_tiempo_pruebaDTO2);
        auto_tiempo_pruebaDTO2.setId(2L);
        assertThat(auto_tiempo_pruebaDTO1).isNotEqualTo(auto_tiempo_pruebaDTO2);
        auto_tiempo_pruebaDTO1.setId(null);
        assertThat(auto_tiempo_pruebaDTO1).isNotEqualTo(auto_tiempo_pruebaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(auto_tiempo_pruebaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(auto_tiempo_pruebaMapper.fromId(null)).isNull();
    }
}
