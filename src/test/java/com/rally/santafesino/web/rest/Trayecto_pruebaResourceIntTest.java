package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Trayecto_prueba;
import com.rally.santafesino.domain.Pruebas;
import com.rally.santafesino.domain.Trayecto;
import com.rally.santafesino.repository.Trayecto_pruebaRepository;
import com.rally.santafesino.service.Trayecto_pruebaService;
import com.rally.santafesino.service.dto.Trayecto_pruebaDTO;
import com.rally.santafesino.service.mapper.Trayecto_pruebaMapper;
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
 * Test class for the Trayecto_pruebaResource REST controller.
 *
 * @see Trayecto_pruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class Trayecto_pruebaResourceIntTest {

    @Autowired
    private Trayecto_pruebaRepository trayecto_pruebaRepository;

    @Autowired
    private Trayecto_pruebaMapper trayecto_pruebaMapper;

    @Autowired
    private Trayecto_pruebaService trayecto_pruebaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrayecto_pruebaMockMvc;

    private Trayecto_prueba trayecto_prueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Trayecto_pruebaResource trayecto_pruebaResource = new Trayecto_pruebaResource(trayecto_pruebaService);
        this.restTrayecto_pruebaMockMvc = MockMvcBuilders.standaloneSetup(trayecto_pruebaResource)
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
    public static Trayecto_prueba createEntity(EntityManager em) {
        Trayecto_prueba trayecto_prueba = new Trayecto_prueba();
        // Add required entity
        Pruebas id_prueba = PruebasResourceIntTest.createEntity(em);
        em.persist(id_prueba);
        em.flush();
        trayecto_prueba.setId_prueba(id_prueba);
        // Add required entity
        Trayecto id_trayecto = TrayectoResourceIntTest.createEntity(em);
        em.persist(id_trayecto);
        em.flush();
        trayecto_prueba.setId_trayecto(id_trayecto);
        return trayecto_prueba;
    }

    @Before
    public void initTest() {
        trayecto_prueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrayecto_prueba() throws Exception {
        int databaseSizeBeforeCreate = trayecto_pruebaRepository.findAll().size();

        // Create the Trayecto_prueba
        Trayecto_pruebaDTO trayecto_pruebaDTO = trayecto_pruebaMapper.toDto(trayecto_prueba);
        restTrayecto_pruebaMockMvc.perform(post("/api/trayecto-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayecto_pruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the Trayecto_prueba in the database
        List<Trayecto_prueba> trayecto_pruebaList = trayecto_pruebaRepository.findAll();
        assertThat(trayecto_pruebaList).hasSize(databaseSizeBeforeCreate + 1);
        Trayecto_prueba testTrayecto_prueba = trayecto_pruebaList.get(trayecto_pruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void createTrayecto_pruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trayecto_pruebaRepository.findAll().size();

        // Create the Trayecto_prueba with an existing ID
        trayecto_prueba.setId(1L);
        Trayecto_pruebaDTO trayecto_pruebaDTO = trayecto_pruebaMapper.toDto(trayecto_prueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrayecto_pruebaMockMvc.perform(post("/api/trayecto-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayecto_pruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trayecto_prueba in the database
        List<Trayecto_prueba> trayecto_pruebaList = trayecto_pruebaRepository.findAll();
        assertThat(trayecto_pruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrayecto_pruebas() throws Exception {
        // Initialize the database
        trayecto_pruebaRepository.saveAndFlush(trayecto_prueba);

        // Get all the trayecto_pruebaList
        restTrayecto_pruebaMockMvc.perform(get("/api/trayecto-pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trayecto_prueba.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTrayecto_prueba() throws Exception {
        // Initialize the database
        trayecto_pruebaRepository.saveAndFlush(trayecto_prueba);

        // Get the trayecto_prueba
        restTrayecto_pruebaMockMvc.perform(get("/api/trayecto-pruebas/{id}", trayecto_prueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trayecto_prueba.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrayecto_prueba() throws Exception {
        // Get the trayecto_prueba
        restTrayecto_pruebaMockMvc.perform(get("/api/trayecto-pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrayecto_prueba() throws Exception {
        // Initialize the database
        trayecto_pruebaRepository.saveAndFlush(trayecto_prueba);
        int databaseSizeBeforeUpdate = trayecto_pruebaRepository.findAll().size();

        // Update the trayecto_prueba
        Trayecto_prueba updatedTrayecto_prueba = trayecto_pruebaRepository.findOne(trayecto_prueba.getId());
        // Disconnect from session so that the updates on updatedTrayecto_prueba are not directly saved in db
        em.detach(updatedTrayecto_prueba);
        Trayecto_pruebaDTO trayecto_pruebaDTO = trayecto_pruebaMapper.toDto(updatedTrayecto_prueba);

        restTrayecto_pruebaMockMvc.perform(put("/api/trayecto-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayecto_pruebaDTO)))
            .andExpect(status().isOk());

        // Validate the Trayecto_prueba in the database
        List<Trayecto_prueba> trayecto_pruebaList = trayecto_pruebaRepository.findAll();
        assertThat(trayecto_pruebaList).hasSize(databaseSizeBeforeUpdate);
        Trayecto_prueba testTrayecto_prueba = trayecto_pruebaList.get(trayecto_pruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTrayecto_prueba() throws Exception {
        int databaseSizeBeforeUpdate = trayecto_pruebaRepository.findAll().size();

        // Create the Trayecto_prueba
        Trayecto_pruebaDTO trayecto_pruebaDTO = trayecto_pruebaMapper.toDto(trayecto_prueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrayecto_pruebaMockMvc.perform(put("/api/trayecto-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayecto_pruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the Trayecto_prueba in the database
        List<Trayecto_prueba> trayecto_pruebaList = trayecto_pruebaRepository.findAll();
        assertThat(trayecto_pruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrayecto_prueba() throws Exception {
        // Initialize the database
        trayecto_pruebaRepository.saveAndFlush(trayecto_prueba);
        int databaseSizeBeforeDelete = trayecto_pruebaRepository.findAll().size();

        // Get the trayecto_prueba
        restTrayecto_pruebaMockMvc.perform(delete("/api/trayecto-pruebas/{id}", trayecto_prueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trayecto_prueba> trayecto_pruebaList = trayecto_pruebaRepository.findAll();
        assertThat(trayecto_pruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trayecto_prueba.class);
        Trayecto_prueba trayecto_prueba1 = new Trayecto_prueba();
        trayecto_prueba1.setId(1L);
        Trayecto_prueba trayecto_prueba2 = new Trayecto_prueba();
        trayecto_prueba2.setId(trayecto_prueba1.getId());
        assertThat(trayecto_prueba1).isEqualTo(trayecto_prueba2);
        trayecto_prueba2.setId(2L);
        assertThat(trayecto_prueba1).isNotEqualTo(trayecto_prueba2);
        trayecto_prueba1.setId(null);
        assertThat(trayecto_prueba1).isNotEqualTo(trayecto_prueba2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trayecto_pruebaDTO.class);
        Trayecto_pruebaDTO trayecto_pruebaDTO1 = new Trayecto_pruebaDTO();
        trayecto_pruebaDTO1.setId(1L);
        Trayecto_pruebaDTO trayecto_pruebaDTO2 = new Trayecto_pruebaDTO();
        assertThat(trayecto_pruebaDTO1).isNotEqualTo(trayecto_pruebaDTO2);
        trayecto_pruebaDTO2.setId(trayecto_pruebaDTO1.getId());
        assertThat(trayecto_pruebaDTO1).isEqualTo(trayecto_pruebaDTO2);
        trayecto_pruebaDTO2.setId(2L);
        assertThat(trayecto_pruebaDTO1).isNotEqualTo(trayecto_pruebaDTO2);
        trayecto_pruebaDTO1.setId(null);
        assertThat(trayecto_pruebaDTO1).isNotEqualTo(trayecto_pruebaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trayecto_pruebaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trayecto_pruebaMapper.fromId(null)).isNull();
    }
}
