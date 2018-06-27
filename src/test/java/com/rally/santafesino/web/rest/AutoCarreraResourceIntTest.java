package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.AutoCarrera;
import com.rally.santafesino.domain.Auto;
import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.repository.AutoCarreraRepository;
import com.rally.santafesino.service.AutoCarreraService;
import com.rally.santafesino.service.dto.AutoCarreraDTO;
import com.rally.santafesino.service.mapper.AutoCarreraMapper;
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
 * Test class for the AutoCarreraResource REST controller.
 *
 * @see AutoCarreraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class AutoCarreraResourceIntTest {

    @Autowired
    private AutoCarreraRepository autoCarreraRepository;

    @Autowired
    private AutoCarreraMapper autoCarreraMapper;

    @Autowired
    private AutoCarreraService autoCarreraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAutoCarreraMockMvc;

    private AutoCarrera autoCarrera;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutoCarreraResource autoCarreraResource = new AutoCarreraResource(autoCarreraService);
        this.restAutoCarreraMockMvc = MockMvcBuilders.standaloneSetup(autoCarreraResource)
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
    public static AutoCarrera createEntity(EntityManager em) {
        AutoCarrera autoCarrera = new AutoCarrera();
        // Add required entity
        Auto auto = AutoResourceIntTest.createEntity(em);
        em.persist(auto);
        em.flush();
        autoCarrera.setAuto(auto);
        // Add required entity
        Carrera carrera = CarreraResourceIntTest.createEntity(em);
        em.persist(carrera);
        em.flush();
        autoCarrera.setCarrera(carrera);
        return autoCarrera;
    }

    @Before
    public void initTest() {
        autoCarrera = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutoCarrera() throws Exception {
        int databaseSizeBeforeCreate = autoCarreraRepository.findAll().size();

        // Create the AutoCarrera
        AutoCarreraDTO autoCarreraDTO = autoCarreraMapper.toDto(autoCarrera);
        restAutoCarreraMockMvc.perform(post("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoCarreraDTO)))
            .andExpect(status().isCreated());

        // Validate the AutoCarrera in the database
        List<AutoCarrera> autoCarreraList = autoCarreraRepository.findAll();
        assertThat(autoCarreraList).hasSize(databaseSizeBeforeCreate + 1);
        AutoCarrera testAutoCarrera = autoCarreraList.get(autoCarreraList.size() - 1);
    }

    @Test
    @Transactional
    public void createAutoCarreraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autoCarreraRepository.findAll().size();

        // Create the AutoCarrera with an existing ID
        autoCarrera.setId(1L);
        AutoCarreraDTO autoCarreraDTO = autoCarreraMapper.toDto(autoCarrera);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoCarreraMockMvc.perform(post("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoCarreraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutoCarrera in the database
        List<AutoCarrera> autoCarreraList = autoCarreraRepository.findAll();
        assertThat(autoCarreraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAutoCarreras() throws Exception {
        // Initialize the database
        autoCarreraRepository.saveAndFlush(autoCarrera);

        // Get all the autoCarreraList
        restAutoCarreraMockMvc.perform(get("/api/auto-carreras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoCarrera.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAutoCarrera() throws Exception {
        // Initialize the database
        autoCarreraRepository.saveAndFlush(autoCarrera);

        // Get the autoCarrera
        restAutoCarreraMockMvc.perform(get("/api/auto-carreras/{id}", autoCarrera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autoCarrera.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAutoCarrera() throws Exception {
        // Get the autoCarrera
        restAutoCarreraMockMvc.perform(get("/api/auto-carreras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutoCarrera() throws Exception {
        // Initialize the database
        autoCarreraRepository.saveAndFlush(autoCarrera);
        int databaseSizeBeforeUpdate = autoCarreraRepository.findAll().size();

        // Update the autoCarrera
        AutoCarrera updatedAutoCarrera = autoCarreraRepository.findOne(autoCarrera.getId());
        // Disconnect from session so that the updates on updatedAutoCarrera are not directly saved in db
        em.detach(updatedAutoCarrera);
        AutoCarreraDTO autoCarreraDTO = autoCarreraMapper.toDto(updatedAutoCarrera);

        restAutoCarreraMockMvc.perform(put("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoCarreraDTO)))
            .andExpect(status().isOk());

        // Validate the AutoCarrera in the database
        List<AutoCarrera> autoCarreraList = autoCarreraRepository.findAll();
        assertThat(autoCarreraList).hasSize(databaseSizeBeforeUpdate);
        AutoCarrera testAutoCarrera = autoCarreraList.get(autoCarreraList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAutoCarrera() throws Exception {
        int databaseSizeBeforeUpdate = autoCarreraRepository.findAll().size();

        // Create the AutoCarrera
        AutoCarreraDTO autoCarreraDTO = autoCarreraMapper.toDto(autoCarrera);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAutoCarreraMockMvc.perform(put("/api/auto-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoCarreraDTO)))
            .andExpect(status().isCreated());

        // Validate the AutoCarrera in the database
        List<AutoCarrera> autoCarreraList = autoCarreraRepository.findAll();
        assertThat(autoCarreraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAutoCarrera() throws Exception {
        // Initialize the database
        autoCarreraRepository.saveAndFlush(autoCarrera);
        int databaseSizeBeforeDelete = autoCarreraRepository.findAll().size();

        // Get the autoCarrera
        restAutoCarreraMockMvc.perform(delete("/api/auto-carreras/{id}", autoCarrera.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AutoCarrera> autoCarreraList = autoCarreraRepository.findAll();
        assertThat(autoCarreraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoCarrera.class);
        AutoCarrera autoCarrera1 = new AutoCarrera();
        autoCarrera1.setId(1L);
        AutoCarrera autoCarrera2 = new AutoCarrera();
        autoCarrera2.setId(autoCarrera1.getId());
        assertThat(autoCarrera1).isEqualTo(autoCarrera2);
        autoCarrera2.setId(2L);
        assertThat(autoCarrera1).isNotEqualTo(autoCarrera2);
        autoCarrera1.setId(null);
        assertThat(autoCarrera1).isNotEqualTo(autoCarrera2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoCarreraDTO.class);
        AutoCarreraDTO autoCarreraDTO1 = new AutoCarreraDTO();
        autoCarreraDTO1.setId(1L);
        AutoCarreraDTO autoCarreraDTO2 = new AutoCarreraDTO();
        assertThat(autoCarreraDTO1).isNotEqualTo(autoCarreraDTO2);
        autoCarreraDTO2.setId(autoCarreraDTO1.getId());
        assertThat(autoCarreraDTO1).isEqualTo(autoCarreraDTO2);
        autoCarreraDTO2.setId(2L);
        assertThat(autoCarreraDTO1).isNotEqualTo(autoCarreraDTO2);
        autoCarreraDTO1.setId(null);
        assertThat(autoCarreraDTO1).isNotEqualTo(autoCarreraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autoCarreraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autoCarreraMapper.fromId(null)).isNull();
    }
}
