package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Localidad;
import com.rally.santafesino.repository.LocalidadRepository;
import com.rally.santafesino.service.LocalidadService;
import com.rally.santafesino.service.dto.LocalidadDTO;
import com.rally.santafesino.service.mapper.LocalidadMapper;
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
 * Test class for the LocalidadResource REST controller.
 *
 * @see LocalidadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class LocalidadResourceIntTest {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private LocalidadMapper localidadMapper;

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalidadMockMvc;

    private Localidad localidad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalidadResource localidadResource = new LocalidadResource(localidadService);
        this.restLocalidadMockMvc = MockMvcBuilders.standaloneSetup(localidadResource)
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
    public static Localidad createEntity(EntityManager em) {
        Localidad localidad = new Localidad();
        return localidad;
    }

    @Before
    public void initTest() {
        localidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalidad() throws Exception {
        int databaseSizeBeforeCreate = localidadRepository.findAll().size();

        // Create the Localidad
        LocalidadDTO localidadDTO = localidadMapper.toDto(localidad);
        restLocalidadMockMvc.perform(post("/api/localidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadDTO)))
            .andExpect(status().isCreated());

        // Validate the Localidad in the database
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeCreate + 1);
        Localidad testLocalidad = localidadList.get(localidadList.size() - 1);
    }

    @Test
    @Transactional
    public void createLocalidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localidadRepository.findAll().size();

        // Create the Localidad with an existing ID
        localidad.setId(1L);
        LocalidadDTO localidadDTO = localidadMapper.toDto(localidad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalidadMockMvc.perform(post("/api/localidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localidad in the database
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocalidads() throws Exception {
        // Initialize the database
        localidadRepository.saveAndFlush(localidad);

        // Get all the localidadList
        restLocalidadMockMvc.perform(get("/api/localidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidad.getId().intValue())));
    }

    @Test
    @Transactional
    public void getLocalidad() throws Exception {
        // Initialize the database
        localidadRepository.saveAndFlush(localidad);

        // Get the localidad
        restLocalidadMockMvc.perform(get("/api/localidads/{id}", localidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localidad.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalidad() throws Exception {
        // Get the localidad
        restLocalidadMockMvc.perform(get("/api/localidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalidad() throws Exception {
        // Initialize the database
        localidadRepository.saveAndFlush(localidad);
        int databaseSizeBeforeUpdate = localidadRepository.findAll().size();

        // Update the localidad
        Localidad updatedLocalidad = localidadRepository.findOne(localidad.getId());
        // Disconnect from session so that the updates on updatedLocalidad are not directly saved in db
        em.detach(updatedLocalidad);
        LocalidadDTO localidadDTO = localidadMapper.toDto(updatedLocalidad);

        restLocalidadMockMvc.perform(put("/api/localidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadDTO)))
            .andExpect(status().isOk());

        // Validate the Localidad in the database
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeUpdate);
        Localidad testLocalidad = localidadList.get(localidadList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalidad() throws Exception {
        int databaseSizeBeforeUpdate = localidadRepository.findAll().size();

        // Create the Localidad
        LocalidadDTO localidadDTO = localidadMapper.toDto(localidad);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocalidadMockMvc.perform(put("/api/localidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadDTO)))
            .andExpect(status().isCreated());

        // Validate the Localidad in the database
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocalidad() throws Exception {
        // Initialize the database
        localidadRepository.saveAndFlush(localidad);
        int databaseSizeBeforeDelete = localidadRepository.findAll().size();

        // Get the localidad
        restLocalidadMockMvc.perform(delete("/api/localidads/{id}", localidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localidad.class);
        Localidad localidad1 = new Localidad();
        localidad1.setId(1L);
        Localidad localidad2 = new Localidad();
        localidad2.setId(localidad1.getId());
        assertThat(localidad1).isEqualTo(localidad2);
        localidad2.setId(2L);
        assertThat(localidad1).isNotEqualTo(localidad2);
        localidad1.setId(null);
        assertThat(localidad1).isNotEqualTo(localidad2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalidadDTO.class);
        LocalidadDTO localidadDTO1 = new LocalidadDTO();
        localidadDTO1.setId(1L);
        LocalidadDTO localidadDTO2 = new LocalidadDTO();
        assertThat(localidadDTO1).isNotEqualTo(localidadDTO2);
        localidadDTO2.setId(localidadDTO1.getId());
        assertThat(localidadDTO1).isEqualTo(localidadDTO2);
        localidadDTO2.setId(2L);
        assertThat(localidadDTO1).isNotEqualTo(localidadDTO2);
        localidadDTO1.setId(null);
        assertThat(localidadDTO1).isNotEqualTo(localidadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(localidadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(localidadMapper.fromId(null)).isNull();
    }
}
