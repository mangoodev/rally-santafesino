package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.EtapaPrueba;
import com.rally.santafesino.domain.Etapa;
import com.rally.santafesino.domain.Pruebas;
import com.rally.santafesino.repository.EtapaPruebaRepository;
import com.rally.santafesino.service.EtapaPruebaService;
import com.rally.santafesino.service.dto.EtapaPruebaDTO;
import com.rally.santafesino.service.mapper.EtapaPruebaMapper;
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
 * Test class for the EtapaPruebaResource REST controller.
 *
 * @see EtapaPruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class EtapaPruebaResourceIntTest {

    @Autowired
    private EtapaPruebaRepository etapaPruebaRepository;

    @Autowired
    private EtapaPruebaMapper etapaPruebaMapper;

    @Autowired
    private EtapaPruebaService etapaPruebaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtapaPruebaMockMvc;

    private EtapaPrueba etapaPrueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtapaPruebaResource etapaPruebaResource = new EtapaPruebaResource(etapaPruebaService);
        this.restEtapaPruebaMockMvc = MockMvcBuilders.standaloneSetup(etapaPruebaResource)
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
    public static EtapaPrueba createEntity(EntityManager em) {
        EtapaPrueba etapaPrueba = new EtapaPrueba();
        // Add required entity
        Etapa id_etapa = EtapaResourceIntTest.createEntity(em);
        em.persist(id_etapa);
        em.flush();
        etapaPrueba.setId_etapa(id_etapa);
        // Add required entity
        Pruebas id_prueba = PruebasResourceIntTest.createEntity(em);
        em.persist(id_prueba);
        em.flush();
        etapaPrueba.setId_prueba(id_prueba);
        return etapaPrueba;
    }

    @Before
    public void initTest() {
        etapaPrueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtapaPrueba() throws Exception {
        int databaseSizeBeforeCreate = etapaPruebaRepository.findAll().size();

        // Create the EtapaPrueba
        EtapaPruebaDTO etapaPruebaDTO = etapaPruebaMapper.toDto(etapaPrueba);
        restEtapaPruebaMockMvc.perform(post("/api/etapa-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the EtapaPrueba in the database
        List<EtapaPrueba> etapaPruebaList = etapaPruebaRepository.findAll();
        assertThat(etapaPruebaList).hasSize(databaseSizeBeforeCreate + 1);
        EtapaPrueba testEtapaPrueba = etapaPruebaList.get(etapaPruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void createEtapaPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapaPruebaRepository.findAll().size();

        // Create the EtapaPrueba with an existing ID
        etapaPrueba.setId(1L);
        EtapaPruebaDTO etapaPruebaDTO = etapaPruebaMapper.toDto(etapaPrueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapaPruebaMockMvc.perform(post("/api/etapa-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaPruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtapaPrueba in the database
        List<EtapaPrueba> etapaPruebaList = etapaPruebaRepository.findAll();
        assertThat(etapaPruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEtapaPruebas() throws Exception {
        // Initialize the database
        etapaPruebaRepository.saveAndFlush(etapaPrueba);

        // Get all the etapaPruebaList
        restEtapaPruebaMockMvc.perform(get("/api/etapa-pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapaPrueba.getId().intValue())));
    }

    @Test
    @Transactional
    public void getEtapaPrueba() throws Exception {
        // Initialize the database
        etapaPruebaRepository.saveAndFlush(etapaPrueba);

        // Get the etapaPrueba
        restEtapaPruebaMockMvc.perform(get("/api/etapa-pruebas/{id}", etapaPrueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etapaPrueba.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEtapaPrueba() throws Exception {
        // Get the etapaPrueba
        restEtapaPruebaMockMvc.perform(get("/api/etapa-pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapaPrueba() throws Exception {
        // Initialize the database
        etapaPruebaRepository.saveAndFlush(etapaPrueba);
        int databaseSizeBeforeUpdate = etapaPruebaRepository.findAll().size();

        // Update the etapaPrueba
        EtapaPrueba updatedEtapaPrueba = etapaPruebaRepository.findOne(etapaPrueba.getId());
        // Disconnect from session so that the updates on updatedEtapaPrueba are not directly saved in db
        em.detach(updatedEtapaPrueba);
        EtapaPruebaDTO etapaPruebaDTO = etapaPruebaMapper.toDto(updatedEtapaPrueba);

        restEtapaPruebaMockMvc.perform(put("/api/etapa-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaPruebaDTO)))
            .andExpect(status().isOk());

        // Validate the EtapaPrueba in the database
        List<EtapaPrueba> etapaPruebaList = etapaPruebaRepository.findAll();
        assertThat(etapaPruebaList).hasSize(databaseSizeBeforeUpdate);
        EtapaPrueba testEtapaPrueba = etapaPruebaList.get(etapaPruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingEtapaPrueba() throws Exception {
        int databaseSizeBeforeUpdate = etapaPruebaRepository.findAll().size();

        // Create the EtapaPrueba
        EtapaPruebaDTO etapaPruebaDTO = etapaPruebaMapper.toDto(etapaPrueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtapaPruebaMockMvc.perform(put("/api/etapa-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the EtapaPrueba in the database
        List<EtapaPrueba> etapaPruebaList = etapaPruebaRepository.findAll();
        assertThat(etapaPruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtapaPrueba() throws Exception {
        // Initialize the database
        etapaPruebaRepository.saveAndFlush(etapaPrueba);
        int databaseSizeBeforeDelete = etapaPruebaRepository.findAll().size();

        // Get the etapaPrueba
        restEtapaPruebaMockMvc.perform(delete("/api/etapa-pruebas/{id}", etapaPrueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EtapaPrueba> etapaPruebaList = etapaPruebaRepository.findAll();
        assertThat(etapaPruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaPrueba.class);
        EtapaPrueba etapaPrueba1 = new EtapaPrueba();
        etapaPrueba1.setId(1L);
        EtapaPrueba etapaPrueba2 = new EtapaPrueba();
        etapaPrueba2.setId(etapaPrueba1.getId());
        assertThat(etapaPrueba1).isEqualTo(etapaPrueba2);
        etapaPrueba2.setId(2L);
        assertThat(etapaPrueba1).isNotEqualTo(etapaPrueba2);
        etapaPrueba1.setId(null);
        assertThat(etapaPrueba1).isNotEqualTo(etapaPrueba2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaPruebaDTO.class);
        EtapaPruebaDTO etapaPruebaDTO1 = new EtapaPruebaDTO();
        etapaPruebaDTO1.setId(1L);
        EtapaPruebaDTO etapaPruebaDTO2 = new EtapaPruebaDTO();
        assertThat(etapaPruebaDTO1).isNotEqualTo(etapaPruebaDTO2);
        etapaPruebaDTO2.setId(etapaPruebaDTO1.getId());
        assertThat(etapaPruebaDTO1).isEqualTo(etapaPruebaDTO2);
        etapaPruebaDTO2.setId(2L);
        assertThat(etapaPruebaDTO1).isNotEqualTo(etapaPruebaDTO2);
        etapaPruebaDTO1.setId(null);
        assertThat(etapaPruebaDTO1).isNotEqualTo(etapaPruebaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(etapaPruebaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(etapaPruebaMapper.fromId(null)).isNull();
    }
}
