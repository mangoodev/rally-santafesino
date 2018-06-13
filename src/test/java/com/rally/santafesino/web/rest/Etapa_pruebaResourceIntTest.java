package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Etapa_prueba;
import com.rally.santafesino.domain.Etapa;
import com.rally.santafesino.domain.Pruebas;
import com.rally.santafesino.repository.Etapa_pruebaRepository;
import com.rally.santafesino.service.Etapa_pruebaService;
import com.rally.santafesino.service.dto.Etapa_pruebaDTO;
import com.rally.santafesino.service.mapper.Etapa_pruebaMapper;
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
 * Test class for the Etapa_pruebaResource REST controller.
 *
 * @see Etapa_pruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class Etapa_pruebaResourceIntTest {

    @Autowired
    private Etapa_pruebaRepository etapa_pruebaRepository;

    @Autowired
    private Etapa_pruebaMapper etapa_pruebaMapper;

    @Autowired
    private Etapa_pruebaService etapa_pruebaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtapa_pruebaMockMvc;

    private Etapa_prueba etapa_prueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Etapa_pruebaResource etapa_pruebaResource = new Etapa_pruebaResource(etapa_pruebaService);
        this.restEtapa_pruebaMockMvc = MockMvcBuilders.standaloneSetup(etapa_pruebaResource)
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
    public static Etapa_prueba createEntity(EntityManager em) {
        Etapa_prueba etapa_prueba = new Etapa_prueba();
        // Add required entity
        Etapa id_etapa = EtapaResourceIntTest.createEntity(em);
        em.persist(id_etapa);
        em.flush();
        etapa_prueba.setId_etapa(id_etapa);
        // Add required entity
        Pruebas id_prueba = PruebasResourceIntTest.createEntity(em);
        em.persist(id_prueba);
        em.flush();
        etapa_prueba.setId_prueba(id_prueba);
        return etapa_prueba;
    }

    @Before
    public void initTest() {
        etapa_prueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtapa_prueba() throws Exception {
        int databaseSizeBeforeCreate = etapa_pruebaRepository.findAll().size();

        // Create the Etapa_prueba
        Etapa_pruebaDTO etapa_pruebaDTO = etapa_pruebaMapper.toDto(etapa_prueba);
        restEtapa_pruebaMockMvc.perform(post("/api/etapa-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapa_pruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the Etapa_prueba in the database
        List<Etapa_prueba> etapa_pruebaList = etapa_pruebaRepository.findAll();
        assertThat(etapa_pruebaList).hasSize(databaseSizeBeforeCreate + 1);
        Etapa_prueba testEtapa_prueba = etapa_pruebaList.get(etapa_pruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void createEtapa_pruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapa_pruebaRepository.findAll().size();

        // Create the Etapa_prueba with an existing ID
        etapa_prueba.setId(1L);
        Etapa_pruebaDTO etapa_pruebaDTO = etapa_pruebaMapper.toDto(etapa_prueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapa_pruebaMockMvc.perform(post("/api/etapa-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapa_pruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etapa_prueba in the database
        List<Etapa_prueba> etapa_pruebaList = etapa_pruebaRepository.findAll();
        assertThat(etapa_pruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEtapa_pruebas() throws Exception {
        // Initialize the database
        etapa_pruebaRepository.saveAndFlush(etapa_prueba);

        // Get all the etapa_pruebaList
        restEtapa_pruebaMockMvc.perform(get("/api/etapa-pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapa_prueba.getId().intValue())));
    }

    @Test
    @Transactional
    public void getEtapa_prueba() throws Exception {
        // Initialize the database
        etapa_pruebaRepository.saveAndFlush(etapa_prueba);

        // Get the etapa_prueba
        restEtapa_pruebaMockMvc.perform(get("/api/etapa-pruebas/{id}", etapa_prueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etapa_prueba.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEtapa_prueba() throws Exception {
        // Get the etapa_prueba
        restEtapa_pruebaMockMvc.perform(get("/api/etapa-pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapa_prueba() throws Exception {
        // Initialize the database
        etapa_pruebaRepository.saveAndFlush(etapa_prueba);
        int databaseSizeBeforeUpdate = etapa_pruebaRepository.findAll().size();

        // Update the etapa_prueba
        Etapa_prueba updatedEtapa_prueba = etapa_pruebaRepository.findOne(etapa_prueba.getId());
        // Disconnect from session so that the updates on updatedEtapa_prueba are not directly saved in db
        em.detach(updatedEtapa_prueba);
        Etapa_pruebaDTO etapa_pruebaDTO = etapa_pruebaMapper.toDto(updatedEtapa_prueba);

        restEtapa_pruebaMockMvc.perform(put("/api/etapa-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapa_pruebaDTO)))
            .andExpect(status().isOk());

        // Validate the Etapa_prueba in the database
        List<Etapa_prueba> etapa_pruebaList = etapa_pruebaRepository.findAll();
        assertThat(etapa_pruebaList).hasSize(databaseSizeBeforeUpdate);
        Etapa_prueba testEtapa_prueba = etapa_pruebaList.get(etapa_pruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingEtapa_prueba() throws Exception {
        int databaseSizeBeforeUpdate = etapa_pruebaRepository.findAll().size();

        // Create the Etapa_prueba
        Etapa_pruebaDTO etapa_pruebaDTO = etapa_pruebaMapper.toDto(etapa_prueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtapa_pruebaMockMvc.perform(put("/api/etapa-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapa_pruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the Etapa_prueba in the database
        List<Etapa_prueba> etapa_pruebaList = etapa_pruebaRepository.findAll();
        assertThat(etapa_pruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtapa_prueba() throws Exception {
        // Initialize the database
        etapa_pruebaRepository.saveAndFlush(etapa_prueba);
        int databaseSizeBeforeDelete = etapa_pruebaRepository.findAll().size();

        // Get the etapa_prueba
        restEtapa_pruebaMockMvc.perform(delete("/api/etapa-pruebas/{id}", etapa_prueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Etapa_prueba> etapa_pruebaList = etapa_pruebaRepository.findAll();
        assertThat(etapa_pruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etapa_prueba.class);
        Etapa_prueba etapa_prueba1 = new Etapa_prueba();
        etapa_prueba1.setId(1L);
        Etapa_prueba etapa_prueba2 = new Etapa_prueba();
        etapa_prueba2.setId(etapa_prueba1.getId());
        assertThat(etapa_prueba1).isEqualTo(etapa_prueba2);
        etapa_prueba2.setId(2L);
        assertThat(etapa_prueba1).isNotEqualTo(etapa_prueba2);
        etapa_prueba1.setId(null);
        assertThat(etapa_prueba1).isNotEqualTo(etapa_prueba2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etapa_pruebaDTO.class);
        Etapa_pruebaDTO etapa_pruebaDTO1 = new Etapa_pruebaDTO();
        etapa_pruebaDTO1.setId(1L);
        Etapa_pruebaDTO etapa_pruebaDTO2 = new Etapa_pruebaDTO();
        assertThat(etapa_pruebaDTO1).isNotEqualTo(etapa_pruebaDTO2);
        etapa_pruebaDTO2.setId(etapa_pruebaDTO1.getId());
        assertThat(etapa_pruebaDTO1).isEqualTo(etapa_pruebaDTO2);
        etapa_pruebaDTO2.setId(2L);
        assertThat(etapa_pruebaDTO1).isNotEqualTo(etapa_pruebaDTO2);
        etapa_pruebaDTO1.setId(null);
        assertThat(etapa_pruebaDTO1).isNotEqualTo(etapa_pruebaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(etapa_pruebaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(etapa_pruebaMapper.fromId(null)).isNull();
    }
}
