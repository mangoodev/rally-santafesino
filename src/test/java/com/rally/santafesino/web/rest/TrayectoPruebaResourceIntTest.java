package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.TrayectoPrueba;
import com.rally.santafesino.domain.Pruebas;
import com.rally.santafesino.domain.Trayecto;
import com.rally.santafesino.repository.TrayectoPruebaRepository;
import com.rally.santafesino.service.TrayectoPruebaService;
import com.rally.santafesino.service.dto.TrayectoPruebaDTO;
import com.rally.santafesino.service.mapper.TrayectoPruebaMapper;
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
 * Test class for the TrayectoPruebaResource REST controller.
 *
 * @see TrayectoPruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class TrayectoPruebaResourceIntTest {

    @Autowired
    private TrayectoPruebaRepository trayectoPruebaRepository;

    @Autowired
    private TrayectoPruebaMapper trayectoPruebaMapper;

    @Autowired
    private TrayectoPruebaService trayectoPruebaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrayectoPruebaMockMvc;

    private TrayectoPrueba trayectoPrueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrayectoPruebaResource trayectoPruebaResource = new TrayectoPruebaResource(trayectoPruebaService);
        this.restTrayectoPruebaMockMvc = MockMvcBuilders.standaloneSetup(trayectoPruebaResource)
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
    public static TrayectoPrueba createEntity(EntityManager em) {
        TrayectoPrueba trayectoPrueba = new TrayectoPrueba();
        // Add required entity
        Pruebas id_prueba = PruebasResourceIntTest.createEntity(em);
        em.persist(id_prueba);
        em.flush();
        trayectoPrueba.setId_prueba(id_prueba);
        // Add required entity
        Trayecto id_trayecto = TrayectoResourceIntTest.createEntity(em);
        em.persist(id_trayecto);
        em.flush();
        trayectoPrueba.setId_trayecto(id_trayecto);
        return trayectoPrueba;
    }

    @Before
    public void initTest() {
        trayectoPrueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrayectoPrueba() throws Exception {
        int databaseSizeBeforeCreate = trayectoPruebaRepository.findAll().size();

        // Create the TrayectoPrueba
        TrayectoPruebaDTO trayectoPruebaDTO = trayectoPruebaMapper.toDto(trayectoPrueba);
        restTrayectoPruebaMockMvc.perform(post("/api/trayecto-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayectoPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the TrayectoPrueba in the database
        List<TrayectoPrueba> trayectoPruebaList = trayectoPruebaRepository.findAll();
        assertThat(trayectoPruebaList).hasSize(databaseSizeBeforeCreate + 1);
        TrayectoPrueba testTrayectoPrueba = trayectoPruebaList.get(trayectoPruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void createTrayectoPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trayectoPruebaRepository.findAll().size();

        // Create the TrayectoPrueba with an existing ID
        trayectoPrueba.setId(1L);
        TrayectoPruebaDTO trayectoPruebaDTO = trayectoPruebaMapper.toDto(trayectoPrueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrayectoPruebaMockMvc.perform(post("/api/trayecto-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayectoPruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrayectoPrueba in the database
        List<TrayectoPrueba> trayectoPruebaList = trayectoPruebaRepository.findAll();
        assertThat(trayectoPruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrayectoPruebas() throws Exception {
        // Initialize the database
        trayectoPruebaRepository.saveAndFlush(trayectoPrueba);

        // Get all the trayectoPruebaList
        restTrayectoPruebaMockMvc.perform(get("/api/trayecto-pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trayectoPrueba.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTrayectoPrueba() throws Exception {
        // Initialize the database
        trayectoPruebaRepository.saveAndFlush(trayectoPrueba);

        // Get the trayectoPrueba
        restTrayectoPruebaMockMvc.perform(get("/api/trayecto-pruebas/{id}", trayectoPrueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trayectoPrueba.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrayectoPrueba() throws Exception {
        // Get the trayectoPrueba
        restTrayectoPruebaMockMvc.perform(get("/api/trayecto-pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrayectoPrueba() throws Exception {
        // Initialize the database
        trayectoPruebaRepository.saveAndFlush(trayectoPrueba);
        int databaseSizeBeforeUpdate = trayectoPruebaRepository.findAll().size();

        // Update the trayectoPrueba
        TrayectoPrueba updatedTrayectoPrueba = trayectoPruebaRepository.findOne(trayectoPrueba.getId());
        // Disconnect from session so that the updates on updatedTrayectoPrueba are not directly saved in db
        em.detach(updatedTrayectoPrueba);
        TrayectoPruebaDTO trayectoPruebaDTO = trayectoPruebaMapper.toDto(updatedTrayectoPrueba);

        restTrayectoPruebaMockMvc.perform(put("/api/trayecto-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayectoPruebaDTO)))
            .andExpect(status().isOk());

        // Validate the TrayectoPrueba in the database
        List<TrayectoPrueba> trayectoPruebaList = trayectoPruebaRepository.findAll();
        assertThat(trayectoPruebaList).hasSize(databaseSizeBeforeUpdate);
        TrayectoPrueba testTrayectoPrueba = trayectoPruebaList.get(trayectoPruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTrayectoPrueba() throws Exception {
        int databaseSizeBeforeUpdate = trayectoPruebaRepository.findAll().size();

        // Create the TrayectoPrueba
        TrayectoPruebaDTO trayectoPruebaDTO = trayectoPruebaMapper.toDto(trayectoPrueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrayectoPruebaMockMvc.perform(put("/api/trayecto-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayectoPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the TrayectoPrueba in the database
        List<TrayectoPrueba> trayectoPruebaList = trayectoPruebaRepository.findAll();
        assertThat(trayectoPruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrayectoPrueba() throws Exception {
        // Initialize the database
        trayectoPruebaRepository.saveAndFlush(trayectoPrueba);
        int databaseSizeBeforeDelete = trayectoPruebaRepository.findAll().size();

        // Get the trayectoPrueba
        restTrayectoPruebaMockMvc.perform(delete("/api/trayecto-pruebas/{id}", trayectoPrueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrayectoPrueba> trayectoPruebaList = trayectoPruebaRepository.findAll();
        assertThat(trayectoPruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrayectoPrueba.class);
        TrayectoPrueba trayectoPrueba1 = new TrayectoPrueba();
        trayectoPrueba1.setId(1L);
        TrayectoPrueba trayectoPrueba2 = new TrayectoPrueba();
        trayectoPrueba2.setId(trayectoPrueba1.getId());
        assertThat(trayectoPrueba1).isEqualTo(trayectoPrueba2);
        trayectoPrueba2.setId(2L);
        assertThat(trayectoPrueba1).isNotEqualTo(trayectoPrueba2);
        trayectoPrueba1.setId(null);
        assertThat(trayectoPrueba1).isNotEqualTo(trayectoPrueba2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrayectoPruebaDTO.class);
        TrayectoPruebaDTO trayectoPruebaDTO1 = new TrayectoPruebaDTO();
        trayectoPruebaDTO1.setId(1L);
        TrayectoPruebaDTO trayectoPruebaDTO2 = new TrayectoPruebaDTO();
        assertThat(trayectoPruebaDTO1).isNotEqualTo(trayectoPruebaDTO2);
        trayectoPruebaDTO2.setId(trayectoPruebaDTO1.getId());
        assertThat(trayectoPruebaDTO1).isEqualTo(trayectoPruebaDTO2);
        trayectoPruebaDTO2.setId(2L);
        assertThat(trayectoPruebaDTO1).isNotEqualTo(trayectoPruebaDTO2);
        trayectoPruebaDTO1.setId(null);
        assertThat(trayectoPruebaDTO1).isNotEqualTo(trayectoPruebaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trayectoPruebaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trayectoPruebaMapper.fromId(null)).isNull();
    }
}
