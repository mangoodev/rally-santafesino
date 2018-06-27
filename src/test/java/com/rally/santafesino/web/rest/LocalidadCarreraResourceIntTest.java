package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.LocalidadCarrera;
import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.domain.Localidad;
import com.rally.santafesino.repository.LocalidadCarreraRepository;
import com.rally.santafesino.service.LocalidadCarreraService;
import com.rally.santafesino.service.dto.LocalidadCarreraDTO;
import com.rally.santafesino.service.mapper.LocalidadCarreraMapper;
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
 * Test class for the LocalidadCarreraResource REST controller.
 *
 * @see LocalidadCarreraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class LocalidadCarreraResourceIntTest {

    @Autowired
    private LocalidadCarreraRepository localidadCarreraRepository;

    @Autowired
    private LocalidadCarreraMapper localidadCarreraMapper;

    @Autowired
    private LocalidadCarreraService localidadCarreraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalidadCarreraMockMvc;

    private LocalidadCarrera localidadCarrera;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalidadCarreraResource localidadCarreraResource = new LocalidadCarreraResource(localidadCarreraService);
        this.restLocalidadCarreraMockMvc = MockMvcBuilders.standaloneSetup(localidadCarreraResource)
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
    public static LocalidadCarrera createEntity(EntityManager em) {
        LocalidadCarrera localidadCarrera = new LocalidadCarrera();
        // Add required entity
        Carrera id_carrera = CarreraResourceIntTest.createEntity(em);
        em.persist(id_carrera);
        em.flush();
        localidadCarrera.setId_carrera(id_carrera);
        // Add required entity
        Localidad id_localidad = LocalidadResourceIntTest.createEntity(em);
        em.persist(id_localidad);
        em.flush();
        localidadCarrera.setId_localidad(id_localidad);
        return localidadCarrera;
    }

    @Before
    public void initTest() {
        localidadCarrera = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalidadCarrera() throws Exception {
        int databaseSizeBeforeCreate = localidadCarreraRepository.findAll().size();

        // Create the LocalidadCarrera
        LocalidadCarreraDTO localidadCarreraDTO = localidadCarreraMapper.toDto(localidadCarrera);
        restLocalidadCarreraMockMvc.perform(post("/api/localidad-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadCarreraDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalidadCarrera in the database
        List<LocalidadCarrera> localidadCarreraList = localidadCarreraRepository.findAll();
        assertThat(localidadCarreraList).hasSize(databaseSizeBeforeCreate + 1);
        LocalidadCarrera testLocalidadCarrera = localidadCarreraList.get(localidadCarreraList.size() - 1);
    }

    @Test
    @Transactional
    public void createLocalidadCarreraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localidadCarreraRepository.findAll().size();

        // Create the LocalidadCarrera with an existing ID
        localidadCarrera.setId(1L);
        LocalidadCarreraDTO localidadCarreraDTO = localidadCarreraMapper.toDto(localidadCarrera);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalidadCarreraMockMvc.perform(post("/api/localidad-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadCarreraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalidadCarrera in the database
        List<LocalidadCarrera> localidadCarreraList = localidadCarreraRepository.findAll();
        assertThat(localidadCarreraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocalidadCarreras() throws Exception {
        // Initialize the database
        localidadCarreraRepository.saveAndFlush(localidadCarrera);

        // Get all the localidadCarreraList
        restLocalidadCarreraMockMvc.perform(get("/api/localidad-carreras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidadCarrera.getId().intValue())));
    }

    @Test
    @Transactional
    public void getLocalidadCarrera() throws Exception {
        // Initialize the database
        localidadCarreraRepository.saveAndFlush(localidadCarrera);

        // Get the localidadCarrera
        restLocalidadCarreraMockMvc.perform(get("/api/localidad-carreras/{id}", localidadCarrera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localidadCarrera.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalidadCarrera() throws Exception {
        // Get the localidadCarrera
        restLocalidadCarreraMockMvc.perform(get("/api/localidad-carreras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalidadCarrera() throws Exception {
        // Initialize the database
        localidadCarreraRepository.saveAndFlush(localidadCarrera);
        int databaseSizeBeforeUpdate = localidadCarreraRepository.findAll().size();

        // Update the localidadCarrera
        LocalidadCarrera updatedLocalidadCarrera = localidadCarreraRepository.findOne(localidadCarrera.getId());
        // Disconnect from session so that the updates on updatedLocalidadCarrera are not directly saved in db
        em.detach(updatedLocalidadCarrera);
        LocalidadCarreraDTO localidadCarreraDTO = localidadCarreraMapper.toDto(updatedLocalidadCarrera);

        restLocalidadCarreraMockMvc.perform(put("/api/localidad-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadCarreraDTO)))
            .andExpect(status().isOk());

        // Validate the LocalidadCarrera in the database
        List<LocalidadCarrera> localidadCarreraList = localidadCarreraRepository.findAll();
        assertThat(localidadCarreraList).hasSize(databaseSizeBeforeUpdate);
        LocalidadCarrera testLocalidadCarrera = localidadCarreraList.get(localidadCarreraList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalidadCarrera() throws Exception {
        int databaseSizeBeforeUpdate = localidadCarreraRepository.findAll().size();

        // Create the LocalidadCarrera
        LocalidadCarreraDTO localidadCarreraDTO = localidadCarreraMapper.toDto(localidadCarrera);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocalidadCarreraMockMvc.perform(put("/api/localidad-carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadCarreraDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalidadCarrera in the database
        List<LocalidadCarrera> localidadCarreraList = localidadCarreraRepository.findAll();
        assertThat(localidadCarreraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocalidadCarrera() throws Exception {
        // Initialize the database
        localidadCarreraRepository.saveAndFlush(localidadCarrera);
        int databaseSizeBeforeDelete = localidadCarreraRepository.findAll().size();

        // Get the localidadCarrera
        restLocalidadCarreraMockMvc.perform(delete("/api/localidad-carreras/{id}", localidadCarrera.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LocalidadCarrera> localidadCarreraList = localidadCarreraRepository.findAll();
        assertThat(localidadCarreraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalidadCarrera.class);
        LocalidadCarrera localidadCarrera1 = new LocalidadCarrera();
        localidadCarrera1.setId(1L);
        LocalidadCarrera localidadCarrera2 = new LocalidadCarrera();
        localidadCarrera2.setId(localidadCarrera1.getId());
        assertThat(localidadCarrera1).isEqualTo(localidadCarrera2);
        localidadCarrera2.setId(2L);
        assertThat(localidadCarrera1).isNotEqualTo(localidadCarrera2);
        localidadCarrera1.setId(null);
        assertThat(localidadCarrera1).isNotEqualTo(localidadCarrera2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalidadCarreraDTO.class);
        LocalidadCarreraDTO localidadCarreraDTO1 = new LocalidadCarreraDTO();
        localidadCarreraDTO1.setId(1L);
        LocalidadCarreraDTO localidadCarreraDTO2 = new LocalidadCarreraDTO();
        assertThat(localidadCarreraDTO1).isNotEqualTo(localidadCarreraDTO2);
        localidadCarreraDTO2.setId(localidadCarreraDTO1.getId());
        assertThat(localidadCarreraDTO1).isEqualTo(localidadCarreraDTO2);
        localidadCarreraDTO2.setId(2L);
        assertThat(localidadCarreraDTO1).isNotEqualTo(localidadCarreraDTO2);
        localidadCarreraDTO1.setId(null);
        assertThat(localidadCarreraDTO1).isNotEqualTo(localidadCarreraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(localidadCarreraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(localidadCarreraMapper.fromId(null)).isNull();
    }
}
