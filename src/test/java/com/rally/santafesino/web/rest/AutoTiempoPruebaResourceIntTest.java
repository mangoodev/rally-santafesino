package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.AutoTiempoPrueba;
import com.rally.santafesino.domain.Auto;
import com.rally.santafesino.domain.Tiempos;
import com.rally.santafesino.domain.Pruebas;
import com.rally.santafesino.repository.AutoTiempoPruebaRepository;
import com.rally.santafesino.service.AutoTiempoPruebaService;
import com.rally.santafesino.service.dto.AutoTiempoPruebaDTO;
import com.rally.santafesino.service.mapper.AutoTiempoPruebaMapper;
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
 * Test class for the AutoTiempoPruebaResource REST controller.
 *
 * @see AutoTiempoPruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class AutoTiempoPruebaResourceIntTest {

    @Autowired
    private AutoTiempoPruebaRepository autoTiempoPruebaRepository;

    @Autowired
    private AutoTiempoPruebaMapper autoTiempoPruebaMapper;

    @Autowired
    private AutoTiempoPruebaService autoTiempoPruebaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAutoTiempoPruebaMockMvc;

    private AutoTiempoPrueba autoTiempoPrueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutoTiempoPruebaResource autoTiempoPruebaResource = new AutoTiempoPruebaResource(autoTiempoPruebaService);
        this.restAutoTiempoPruebaMockMvc = MockMvcBuilders.standaloneSetup(autoTiempoPruebaResource)
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
    public static AutoTiempoPrueba createEntity(EntityManager em) {
        AutoTiempoPrueba autoTiempoPrueba = new AutoTiempoPrueba();
        // Add required entity
        Auto id_auto = AutoResourceIntTest.createEntity(em);
        em.persist(id_auto);
        em.flush();
        autoTiempoPrueba.setId_auto(id_auto);
        // Add required entity
        Tiempos id_tiempos = TiemposResourceIntTest.createEntity(em);
        em.persist(id_tiempos);
        em.flush();
        autoTiempoPrueba.setId_tiempos(id_tiempos);
        // Add required entity
        Pruebas id_prueba = PruebasResourceIntTest.createEntity(em);
        em.persist(id_prueba);
        em.flush();
        autoTiempoPrueba.setId_prueba(id_prueba);
        return autoTiempoPrueba;
    }

    @Before
    public void initTest() {
        autoTiempoPrueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutoTiempoPrueba() throws Exception {
        int databaseSizeBeforeCreate = autoTiempoPruebaRepository.findAll().size();

        // Create the AutoTiempoPrueba
        AutoTiempoPruebaDTO autoTiempoPruebaDTO = autoTiempoPruebaMapper.toDto(autoTiempoPrueba);
        restAutoTiempoPruebaMockMvc.perform(post("/api/auto-tiempo-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoTiempoPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the AutoTiempoPrueba in the database
        List<AutoTiempoPrueba> autoTiempoPruebaList = autoTiempoPruebaRepository.findAll();
        assertThat(autoTiempoPruebaList).hasSize(databaseSizeBeforeCreate + 1);
        AutoTiempoPrueba testAutoTiempoPrueba = autoTiempoPruebaList.get(autoTiempoPruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void createAutoTiempoPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autoTiempoPruebaRepository.findAll().size();

        // Create the AutoTiempoPrueba with an existing ID
        autoTiempoPrueba.setId(1L);
        AutoTiempoPruebaDTO autoTiempoPruebaDTO = autoTiempoPruebaMapper.toDto(autoTiempoPrueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoTiempoPruebaMockMvc.perform(post("/api/auto-tiempo-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoTiempoPruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutoTiempoPrueba in the database
        List<AutoTiempoPrueba> autoTiempoPruebaList = autoTiempoPruebaRepository.findAll();
        assertThat(autoTiempoPruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAutoTiempoPruebas() throws Exception {
        // Initialize the database
        autoTiempoPruebaRepository.saveAndFlush(autoTiempoPrueba);

        // Get all the autoTiempoPruebaList
        restAutoTiempoPruebaMockMvc.perform(get("/api/auto-tiempo-pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoTiempoPrueba.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAutoTiempoPrueba() throws Exception {
        // Initialize the database
        autoTiempoPruebaRepository.saveAndFlush(autoTiempoPrueba);

        // Get the autoTiempoPrueba
        restAutoTiempoPruebaMockMvc.perform(get("/api/auto-tiempo-pruebas/{id}", autoTiempoPrueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autoTiempoPrueba.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAutoTiempoPrueba() throws Exception {
        // Get the autoTiempoPrueba
        restAutoTiempoPruebaMockMvc.perform(get("/api/auto-tiempo-pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutoTiempoPrueba() throws Exception {
        // Initialize the database
        autoTiempoPruebaRepository.saveAndFlush(autoTiempoPrueba);
        int databaseSizeBeforeUpdate = autoTiempoPruebaRepository.findAll().size();

        // Update the autoTiempoPrueba
        AutoTiempoPrueba updatedAutoTiempoPrueba = autoTiempoPruebaRepository.findOne(autoTiempoPrueba.getId());
        // Disconnect from session so that the updates on updatedAutoTiempoPrueba are not directly saved in db
        em.detach(updatedAutoTiempoPrueba);
        AutoTiempoPruebaDTO autoTiempoPruebaDTO = autoTiempoPruebaMapper.toDto(updatedAutoTiempoPrueba);

        restAutoTiempoPruebaMockMvc.perform(put("/api/auto-tiempo-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoTiempoPruebaDTO)))
            .andExpect(status().isOk());

        // Validate the AutoTiempoPrueba in the database
        List<AutoTiempoPrueba> autoTiempoPruebaList = autoTiempoPruebaRepository.findAll();
        assertThat(autoTiempoPruebaList).hasSize(databaseSizeBeforeUpdate);
        AutoTiempoPrueba testAutoTiempoPrueba = autoTiempoPruebaList.get(autoTiempoPruebaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAutoTiempoPrueba() throws Exception {
        int databaseSizeBeforeUpdate = autoTiempoPruebaRepository.findAll().size();

        // Create the AutoTiempoPrueba
        AutoTiempoPruebaDTO autoTiempoPruebaDTO = autoTiempoPruebaMapper.toDto(autoTiempoPrueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAutoTiempoPruebaMockMvc.perform(put("/api/auto-tiempo-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoTiempoPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the AutoTiempoPrueba in the database
        List<AutoTiempoPrueba> autoTiempoPruebaList = autoTiempoPruebaRepository.findAll();
        assertThat(autoTiempoPruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAutoTiempoPrueba() throws Exception {
        // Initialize the database
        autoTiempoPruebaRepository.saveAndFlush(autoTiempoPrueba);
        int databaseSizeBeforeDelete = autoTiempoPruebaRepository.findAll().size();

        // Get the autoTiempoPrueba
        restAutoTiempoPruebaMockMvc.perform(delete("/api/auto-tiempo-pruebas/{id}", autoTiempoPrueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AutoTiempoPrueba> autoTiempoPruebaList = autoTiempoPruebaRepository.findAll();
        assertThat(autoTiempoPruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoTiempoPrueba.class);
        AutoTiempoPrueba autoTiempoPrueba1 = new AutoTiempoPrueba();
        autoTiempoPrueba1.setId(1L);
        AutoTiempoPrueba autoTiempoPrueba2 = new AutoTiempoPrueba();
        autoTiempoPrueba2.setId(autoTiempoPrueba1.getId());
        assertThat(autoTiempoPrueba1).isEqualTo(autoTiempoPrueba2);
        autoTiempoPrueba2.setId(2L);
        assertThat(autoTiempoPrueba1).isNotEqualTo(autoTiempoPrueba2);
        autoTiempoPrueba1.setId(null);
        assertThat(autoTiempoPrueba1).isNotEqualTo(autoTiempoPrueba2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoTiempoPruebaDTO.class);
        AutoTiempoPruebaDTO autoTiempoPruebaDTO1 = new AutoTiempoPruebaDTO();
        autoTiempoPruebaDTO1.setId(1L);
        AutoTiempoPruebaDTO autoTiempoPruebaDTO2 = new AutoTiempoPruebaDTO();
        assertThat(autoTiempoPruebaDTO1).isNotEqualTo(autoTiempoPruebaDTO2);
        autoTiempoPruebaDTO2.setId(autoTiempoPruebaDTO1.getId());
        assertThat(autoTiempoPruebaDTO1).isEqualTo(autoTiempoPruebaDTO2);
        autoTiempoPruebaDTO2.setId(2L);
        assertThat(autoTiempoPruebaDTO1).isNotEqualTo(autoTiempoPruebaDTO2);
        autoTiempoPruebaDTO1.setId(null);
        assertThat(autoTiempoPruebaDTO1).isNotEqualTo(autoTiempoPruebaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autoTiempoPruebaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autoTiempoPruebaMapper.fromId(null)).isNull();
    }
}
