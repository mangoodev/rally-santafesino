package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Localidad_carrera;
import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.domain.Localidad;
import com.rally.santafesino.repository.Localidad_carreraRepository;
import com.rally.santafesino.service.Localidad_carreraService;
import com.rally.santafesino.service.dto.Localidad_carreraDTO;
import com.rally.santafesino.service.mapper.Localidad_carreraMapper;
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
 * Test class for the Localidad_carreraResource REST controller.
 *
 * @see Localidad_carreraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class Localidad_carreraResourceIntTest {

    @Autowired
    private Localidad_carreraRepository localidad_carreraRepository;

    @Autowired
    private Localidad_carreraMapper localidad_carreraMapper;

    @Autowired
    private Localidad_carreraService localidad_carreraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalidad_carreraMockMvc;

    private Localidad_carrera localidad_carrera;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Localidad_carreraResource localidad_carreraResource = new Localidad_carreraResource(localidad_carreraService);
        this.restLocalidad_carreraMockMvc = MockMvcBuilders.standaloneSetup(localidad_carreraResource)
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
    public static Localidad_carrera createEntity(EntityManager em) {
        Localidad_carrera localidad_carrera = new Localidad_carrera();
        // Add required entity
        Carrera id_carrera = CarreraResourceIntTest.createEntity(em);
        em.persist(id_carrera);
        em.flush();
        localidad_carrera.setId_carrera(id_carrera);
        // Add required entity
        Localidad id_localidad = LocalidadResourceIntTest.createEntity(em);
        em.persist(id_localidad);
        em.flush();
        localidad_carrera.setId_localidad(id_localidad);
        return localidad_carrera;
    }

    @Before
    public void initTest() {
        localidad_carrera = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalidad_carrera() throws Exception {
        int databaseSizeBeforeCreate = localidad_carreraRepository.findAll().size();

        // Create the Localidad_carrera
        Localidad_carreraDTO localidad_carreraDTO = localidad_carreraMapper.toDto(localidad_carrera);
        restLocalidad_carreraMockMvc.perform(post("/api/localidad-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidad_carreraDTO)))
            .andExpect(status().isCreated());

        // Validate the Localidad_carrera in the database
        List<Localidad_carrera> localidad_carreraList = localidad_carreraRepository.findAll();
        assertThat(localidad_carreraList).hasSize(databaseSizeBeforeCreate + 1);
        Localidad_carrera testLocalidad_carrera = localidad_carreraList.get(localidad_carreraList.size() - 1);
    }

    @Test
    @Transactional
    public void createLocalidad_carreraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localidad_carreraRepository.findAll().size();

        // Create the Localidad_carrera with an existing ID
        localidad_carrera.setId(1L);
        Localidad_carreraDTO localidad_carreraDTO = localidad_carreraMapper.toDto(localidad_carrera);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalidad_carreraMockMvc.perform(post("/api/localidad-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidad_carreraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localidad_carrera in the database
        List<Localidad_carrera> localidad_carreraList = localidad_carreraRepository.findAll();
        assertThat(localidad_carreraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocalidad_carreras() throws Exception {
        // Initialize the database
        localidad_carreraRepository.saveAndFlush(localidad_carrera);

        // Get all the localidad_carreraList
        restLocalidad_carreraMockMvc.perform(get("/api/localidad-carreras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidad_carrera.getId().intValue())));
    }

    @Test
    @Transactional
    public void getLocalidad_carrera() throws Exception {
        // Initialize the database
        localidad_carreraRepository.saveAndFlush(localidad_carrera);

        // Get the localidad_carrera
        restLocalidad_carreraMockMvc.perform(get("/api/localidad-carreras/{id}", localidad_carrera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localidad_carrera.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalidad_carrera() throws Exception {
        // Get the localidad_carrera
        restLocalidad_carreraMockMvc.perform(get("/api/localidad-carreras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalidad_carrera() throws Exception {
        // Initialize the database
        localidad_carreraRepository.saveAndFlush(localidad_carrera);
        int databaseSizeBeforeUpdate = localidad_carreraRepository.findAll().size();

        // Update the localidad_carrera
        Localidad_carrera updatedLocalidad_carrera = localidad_carreraRepository.findOne(localidad_carrera.getId());
        // Disconnect from session so that the updates on updatedLocalidad_carrera are not directly saved in db
        em.detach(updatedLocalidad_carrera);
        Localidad_carreraDTO localidad_carreraDTO = localidad_carreraMapper.toDto(updatedLocalidad_carrera);

        restLocalidad_carreraMockMvc.perform(put("/api/localidad-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidad_carreraDTO)))
            .andExpect(status().isOk());

        // Validate the Localidad_carrera in the database
        List<Localidad_carrera> localidad_carreraList = localidad_carreraRepository.findAll();
        assertThat(localidad_carreraList).hasSize(databaseSizeBeforeUpdate);
        Localidad_carrera testLocalidad_carrera = localidad_carreraList.get(localidad_carreraList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalidad_carrera() throws Exception {
        int databaseSizeBeforeUpdate = localidad_carreraRepository.findAll().size();

        // Create the Localidad_carrera
        Localidad_carreraDTO localidad_carreraDTO = localidad_carreraMapper.toDto(localidad_carrera);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocalidad_carreraMockMvc.perform(put("/api/localidad-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidad_carreraDTO)))
            .andExpect(status().isCreated());

        // Validate the Localidad_carrera in the database
        List<Localidad_carrera> localidad_carreraList = localidad_carreraRepository.findAll();
        assertThat(localidad_carreraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocalidad_carrera() throws Exception {
        // Initialize the database
        localidad_carreraRepository.saveAndFlush(localidad_carrera);
        int databaseSizeBeforeDelete = localidad_carreraRepository.findAll().size();

        // Get the localidad_carrera
        restLocalidad_carreraMockMvc.perform(delete("/api/localidad-carreras/{id}", localidad_carrera.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Localidad_carrera> localidad_carreraList = localidad_carreraRepository.findAll();
        assertThat(localidad_carreraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localidad_carrera.class);
        Localidad_carrera localidad_carrera1 = new Localidad_carrera();
        localidad_carrera1.setId(1L);
        Localidad_carrera localidad_carrera2 = new Localidad_carrera();
        localidad_carrera2.setId(localidad_carrera1.getId());
        assertThat(localidad_carrera1).isEqualTo(localidad_carrera2);
        localidad_carrera2.setId(2L);
        assertThat(localidad_carrera1).isNotEqualTo(localidad_carrera2);
        localidad_carrera1.setId(null);
        assertThat(localidad_carrera1).isNotEqualTo(localidad_carrera2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localidad_carreraDTO.class);
        Localidad_carreraDTO localidad_carreraDTO1 = new Localidad_carreraDTO();
        localidad_carreraDTO1.setId(1L);
        Localidad_carreraDTO localidad_carreraDTO2 = new Localidad_carreraDTO();
        assertThat(localidad_carreraDTO1).isNotEqualTo(localidad_carreraDTO2);
        localidad_carreraDTO2.setId(localidad_carreraDTO1.getId());
        assertThat(localidad_carreraDTO1).isEqualTo(localidad_carreraDTO2);
        localidad_carreraDTO2.setId(2L);
        assertThat(localidad_carreraDTO1).isNotEqualTo(localidad_carreraDTO2);
        localidad_carreraDTO1.setId(null);
        assertThat(localidad_carreraDTO1).isNotEqualTo(localidad_carreraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(localidad_carreraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(localidad_carreraMapper.fromId(null)).isNull();
    }
}
