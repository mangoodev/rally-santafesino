package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.CoordenadaTrayecto;
import com.rally.santafesino.domain.Coordenadas;
import com.rally.santafesino.domain.Trayecto;
import com.rally.santafesino.repository.CoordenadaTrayectoRepository;
import com.rally.santafesino.service.CoordenadaTrayectoService;
import com.rally.santafesino.service.dto.CoordenadaTrayectoDTO;
import com.rally.santafesino.service.mapper.CoordenadaTrayectoMapper;
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
 * Test class for the CoordenadaTrayectoResource REST controller.
 *
 * @see CoordenadaTrayectoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class CoordenadaTrayectoResourceIntTest {

    @Autowired
    private CoordenadaTrayectoRepository coordenadaTrayectoRepository;

    @Autowired
    private CoordenadaTrayectoMapper coordenadaTrayectoMapper;

    @Autowired
    private CoordenadaTrayectoService coordenadaTrayectoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoordenadaTrayectoMockMvc;

    private CoordenadaTrayecto coordenadaTrayecto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoordenadaTrayectoResource coordenadaTrayectoResource = new CoordenadaTrayectoResource(coordenadaTrayectoService);
        this.restCoordenadaTrayectoMockMvc = MockMvcBuilders.standaloneSetup(coordenadaTrayectoResource)
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
    public static CoordenadaTrayecto createEntity(EntityManager em) {
        CoordenadaTrayecto coordenadaTrayecto = new CoordenadaTrayecto();
        // Add required entity
        Coordenadas id_coordenadas = CoordenadasResourceIntTest.createEntity(em);
        em.persist(id_coordenadas);
        em.flush();
        coordenadaTrayecto.setId_coordenadas(id_coordenadas);
        // Add required entity
        Trayecto id_trayecto = TrayectoResourceIntTest.createEntity(em);
        em.persist(id_trayecto);
        em.flush();
        coordenadaTrayecto.setId_trayecto(id_trayecto);
        return coordenadaTrayecto;
    }

    @Before
    public void initTest() {
        coordenadaTrayecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordenadaTrayecto() throws Exception {
        int databaseSizeBeforeCreate = coordenadaTrayectoRepository.findAll().size();

        // Create the CoordenadaTrayecto
        CoordenadaTrayectoDTO coordenadaTrayectoDTO = coordenadaTrayectoMapper.toDto(coordenadaTrayecto);
        restCoordenadaTrayectoMockMvc.perform(post("/api/coordenada-trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaTrayectoDTO)))
            .andExpect(status().isCreated());

        // Validate the CoordenadaTrayecto in the database
        List<CoordenadaTrayecto> coordenadaTrayectoList = coordenadaTrayectoRepository.findAll();
        assertThat(coordenadaTrayectoList).hasSize(databaseSizeBeforeCreate + 1);
        CoordenadaTrayecto testCoordenadaTrayecto = coordenadaTrayectoList.get(coordenadaTrayectoList.size() - 1);
    }

    @Test
    @Transactional
    public void createCoordenadaTrayectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordenadaTrayectoRepository.findAll().size();

        // Create the CoordenadaTrayecto with an existing ID
        coordenadaTrayecto.setId(1L);
        CoordenadaTrayectoDTO coordenadaTrayectoDTO = coordenadaTrayectoMapper.toDto(coordenadaTrayecto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordenadaTrayectoMockMvc.perform(post("/api/coordenada-trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaTrayectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoordenadaTrayecto in the database
        List<CoordenadaTrayecto> coordenadaTrayectoList = coordenadaTrayectoRepository.findAll();
        assertThat(coordenadaTrayectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoordenadaTrayectos() throws Exception {
        // Initialize the database
        coordenadaTrayectoRepository.saveAndFlush(coordenadaTrayecto);

        // Get all the coordenadaTrayectoList
        restCoordenadaTrayectoMockMvc.perform(get("/api/coordenada-trayectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordenadaTrayecto.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCoordenadaTrayecto() throws Exception {
        // Initialize the database
        coordenadaTrayectoRepository.saveAndFlush(coordenadaTrayecto);

        // Get the coordenadaTrayecto
        restCoordenadaTrayectoMockMvc.perform(get("/api/coordenada-trayectos/{id}", coordenadaTrayecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordenadaTrayecto.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoordenadaTrayecto() throws Exception {
        // Get the coordenadaTrayecto
        restCoordenadaTrayectoMockMvc.perform(get("/api/coordenada-trayectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordenadaTrayecto() throws Exception {
        // Initialize the database
        coordenadaTrayectoRepository.saveAndFlush(coordenadaTrayecto);
        int databaseSizeBeforeUpdate = coordenadaTrayectoRepository.findAll().size();

        // Update the coordenadaTrayecto
        CoordenadaTrayecto updatedCoordenadaTrayecto = coordenadaTrayectoRepository.findOne(coordenadaTrayecto.getId());
        // Disconnect from session so that the updates on updatedCoordenadaTrayecto are not directly saved in db
        em.detach(updatedCoordenadaTrayecto);
        CoordenadaTrayectoDTO coordenadaTrayectoDTO = coordenadaTrayectoMapper.toDto(updatedCoordenadaTrayecto);

        restCoordenadaTrayectoMockMvc.perform(put("/api/coordenada-trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaTrayectoDTO)))
            .andExpect(status().isOk());

        // Validate the CoordenadaTrayecto in the database
        List<CoordenadaTrayecto> coordenadaTrayectoList = coordenadaTrayectoRepository.findAll();
        assertThat(coordenadaTrayectoList).hasSize(databaseSizeBeforeUpdate);
        CoordenadaTrayecto testCoordenadaTrayecto = coordenadaTrayectoList.get(coordenadaTrayectoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordenadaTrayecto() throws Exception {
        int databaseSizeBeforeUpdate = coordenadaTrayectoRepository.findAll().size();

        // Create the CoordenadaTrayecto
        CoordenadaTrayectoDTO coordenadaTrayectoDTO = coordenadaTrayectoMapper.toDto(coordenadaTrayecto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoordenadaTrayectoMockMvc.perform(put("/api/coordenada-trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaTrayectoDTO)))
            .andExpect(status().isCreated());

        // Validate the CoordenadaTrayecto in the database
        List<CoordenadaTrayecto> coordenadaTrayectoList = coordenadaTrayectoRepository.findAll();
        assertThat(coordenadaTrayectoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoordenadaTrayecto() throws Exception {
        // Initialize the database
        coordenadaTrayectoRepository.saveAndFlush(coordenadaTrayecto);
        int databaseSizeBeforeDelete = coordenadaTrayectoRepository.findAll().size();

        // Get the coordenadaTrayecto
        restCoordenadaTrayectoMockMvc.perform(delete("/api/coordenada-trayectos/{id}", coordenadaTrayecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CoordenadaTrayecto> coordenadaTrayectoList = coordenadaTrayectoRepository.findAll();
        assertThat(coordenadaTrayectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoordenadaTrayecto.class);
        CoordenadaTrayecto coordenadaTrayecto1 = new CoordenadaTrayecto();
        coordenadaTrayecto1.setId(1L);
        CoordenadaTrayecto coordenadaTrayecto2 = new CoordenadaTrayecto();
        coordenadaTrayecto2.setId(coordenadaTrayecto1.getId());
        assertThat(coordenadaTrayecto1).isEqualTo(coordenadaTrayecto2);
        coordenadaTrayecto2.setId(2L);
        assertThat(coordenadaTrayecto1).isNotEqualTo(coordenadaTrayecto2);
        coordenadaTrayecto1.setId(null);
        assertThat(coordenadaTrayecto1).isNotEqualTo(coordenadaTrayecto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoordenadaTrayectoDTO.class);
        CoordenadaTrayectoDTO coordenadaTrayectoDTO1 = new CoordenadaTrayectoDTO();
        coordenadaTrayectoDTO1.setId(1L);
        CoordenadaTrayectoDTO coordenadaTrayectoDTO2 = new CoordenadaTrayectoDTO();
        assertThat(coordenadaTrayectoDTO1).isNotEqualTo(coordenadaTrayectoDTO2);
        coordenadaTrayectoDTO2.setId(coordenadaTrayectoDTO1.getId());
        assertThat(coordenadaTrayectoDTO1).isEqualTo(coordenadaTrayectoDTO2);
        coordenadaTrayectoDTO2.setId(2L);
        assertThat(coordenadaTrayectoDTO1).isNotEqualTo(coordenadaTrayectoDTO2);
        coordenadaTrayectoDTO1.setId(null);
        assertThat(coordenadaTrayectoDTO1).isNotEqualTo(coordenadaTrayectoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coordenadaTrayectoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coordenadaTrayectoMapper.fromId(null)).isNull();
    }
}
