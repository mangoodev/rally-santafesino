package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Coordenada_trayecto;
import com.rally.santafesino.domain.Coordenadas;
import com.rally.santafesino.domain.Trayecto;
import com.rally.santafesino.repository.Coordenada_trayectoRepository;
import com.rally.santafesino.service.Coordenada_trayectoService;
import com.rally.santafesino.service.dto.Coordenada_trayectoDTO;
import com.rally.santafesino.service.mapper.Coordenada_trayectoMapper;
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
 * Test class for the Coordenada_trayectoResource REST controller.
 *
 * @see Coordenada_trayectoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class Coordenada_trayectoResourceIntTest {

    @Autowired
    private Coordenada_trayectoRepository coordenada_trayectoRepository;

    @Autowired
    private Coordenada_trayectoMapper coordenada_trayectoMapper;

    @Autowired
    private Coordenada_trayectoService coordenada_trayectoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoordenada_trayectoMockMvc;

    private Coordenada_trayecto coordenada_trayecto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Coordenada_trayectoResource coordenada_trayectoResource = new Coordenada_trayectoResource(coordenada_trayectoService);
        this.restCoordenada_trayectoMockMvc = MockMvcBuilders.standaloneSetup(coordenada_trayectoResource)
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
    public static Coordenada_trayecto createEntity(EntityManager em) {
        Coordenada_trayecto coordenada_trayecto = new Coordenada_trayecto();
        // Add required entity
        Coordenadas id_coordenadas = CoordenadasResourceIntTest.createEntity(em);
        em.persist(id_coordenadas);
        em.flush();
        coordenada_trayecto.setId_coordenadas(id_coordenadas);
        // Add required entity
        Trayecto id_trayecto = TrayectoResourceIntTest.createEntity(em);
        em.persist(id_trayecto);
        em.flush();
        coordenada_trayecto.setId_trayecto(id_trayecto);
        return coordenada_trayecto;
    }

    @Before
    public void initTest() {
        coordenada_trayecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordenada_trayecto() throws Exception {
        int databaseSizeBeforeCreate = coordenada_trayectoRepository.findAll().size();

        // Create the Coordenada_trayecto
        Coordenada_trayectoDTO coordenada_trayectoDTO = coordenada_trayectoMapper.toDto(coordenada_trayecto);
        restCoordenada_trayectoMockMvc.perform(post("/api/coordenada-trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenada_trayectoDTO)))
            .andExpect(status().isCreated());

        // Validate the Coordenada_trayecto in the database
        List<Coordenada_trayecto> coordenada_trayectoList = coordenada_trayectoRepository.findAll();
        assertThat(coordenada_trayectoList).hasSize(databaseSizeBeforeCreate + 1);
        Coordenada_trayecto testCoordenada_trayecto = coordenada_trayectoList.get(coordenada_trayectoList.size() - 1);
    }

    @Test
    @Transactional
    public void createCoordenada_trayectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordenada_trayectoRepository.findAll().size();

        // Create the Coordenada_trayecto with an existing ID
        coordenada_trayecto.setId(1L);
        Coordenada_trayectoDTO coordenada_trayectoDTO = coordenada_trayectoMapper.toDto(coordenada_trayecto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordenada_trayectoMockMvc.perform(post("/api/coordenada-trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenada_trayectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coordenada_trayecto in the database
        List<Coordenada_trayecto> coordenada_trayectoList = coordenada_trayectoRepository.findAll();
        assertThat(coordenada_trayectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoordenada_trayectos() throws Exception {
        // Initialize the database
        coordenada_trayectoRepository.saveAndFlush(coordenada_trayecto);

        // Get all the coordenada_trayectoList
        restCoordenada_trayectoMockMvc.perform(get("/api/coordenada-trayectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordenada_trayecto.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCoordenada_trayecto() throws Exception {
        // Initialize the database
        coordenada_trayectoRepository.saveAndFlush(coordenada_trayecto);

        // Get the coordenada_trayecto
        restCoordenada_trayectoMockMvc.perform(get("/api/coordenada-trayectos/{id}", coordenada_trayecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordenada_trayecto.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoordenada_trayecto() throws Exception {
        // Get the coordenada_trayecto
        restCoordenada_trayectoMockMvc.perform(get("/api/coordenada-trayectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordenada_trayecto() throws Exception {
        // Initialize the database
        coordenada_trayectoRepository.saveAndFlush(coordenada_trayecto);
        int databaseSizeBeforeUpdate = coordenada_trayectoRepository.findAll().size();

        // Update the coordenada_trayecto
        Coordenada_trayecto updatedCoordenada_trayecto = coordenada_trayectoRepository.findOne(coordenada_trayecto.getId());
        // Disconnect from session so that the updates on updatedCoordenada_trayecto are not directly saved in db
        em.detach(updatedCoordenada_trayecto);
        Coordenada_trayectoDTO coordenada_trayectoDTO = coordenada_trayectoMapper.toDto(updatedCoordenada_trayecto);

        restCoordenada_trayectoMockMvc.perform(put("/api/coordenada-trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenada_trayectoDTO)))
            .andExpect(status().isOk());

        // Validate the Coordenada_trayecto in the database
        List<Coordenada_trayecto> coordenada_trayectoList = coordenada_trayectoRepository.findAll();
        assertThat(coordenada_trayectoList).hasSize(databaseSizeBeforeUpdate);
        Coordenada_trayecto testCoordenada_trayecto = coordenada_trayectoList.get(coordenada_trayectoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordenada_trayecto() throws Exception {
        int databaseSizeBeforeUpdate = coordenada_trayectoRepository.findAll().size();

        // Create the Coordenada_trayecto
        Coordenada_trayectoDTO coordenada_trayectoDTO = coordenada_trayectoMapper.toDto(coordenada_trayecto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoordenada_trayectoMockMvc.perform(put("/api/coordenada-trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenada_trayectoDTO)))
            .andExpect(status().isCreated());

        // Validate the Coordenada_trayecto in the database
        List<Coordenada_trayecto> coordenada_trayectoList = coordenada_trayectoRepository.findAll();
        assertThat(coordenada_trayectoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoordenada_trayecto() throws Exception {
        // Initialize the database
        coordenada_trayectoRepository.saveAndFlush(coordenada_trayecto);
        int databaseSizeBeforeDelete = coordenada_trayectoRepository.findAll().size();

        // Get the coordenada_trayecto
        restCoordenada_trayectoMockMvc.perform(delete("/api/coordenada-trayectos/{id}", coordenada_trayecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coordenada_trayecto> coordenada_trayectoList = coordenada_trayectoRepository.findAll();
        assertThat(coordenada_trayectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coordenada_trayecto.class);
        Coordenada_trayecto coordenada_trayecto1 = new Coordenada_trayecto();
        coordenada_trayecto1.setId(1L);
        Coordenada_trayecto coordenada_trayecto2 = new Coordenada_trayecto();
        coordenada_trayecto2.setId(coordenada_trayecto1.getId());
        assertThat(coordenada_trayecto1).isEqualTo(coordenada_trayecto2);
        coordenada_trayecto2.setId(2L);
        assertThat(coordenada_trayecto1).isNotEqualTo(coordenada_trayecto2);
        coordenada_trayecto1.setId(null);
        assertThat(coordenada_trayecto1).isNotEqualTo(coordenada_trayecto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coordenada_trayectoDTO.class);
        Coordenada_trayectoDTO coordenada_trayectoDTO1 = new Coordenada_trayectoDTO();
        coordenada_trayectoDTO1.setId(1L);
        Coordenada_trayectoDTO coordenada_trayectoDTO2 = new Coordenada_trayectoDTO();
        assertThat(coordenada_trayectoDTO1).isNotEqualTo(coordenada_trayectoDTO2);
        coordenada_trayectoDTO2.setId(coordenada_trayectoDTO1.getId());
        assertThat(coordenada_trayectoDTO1).isEqualTo(coordenada_trayectoDTO2);
        coordenada_trayectoDTO2.setId(2L);
        assertThat(coordenada_trayectoDTO1).isNotEqualTo(coordenada_trayectoDTO2);
        coordenada_trayectoDTO1.setId(null);
        assertThat(coordenada_trayectoDTO1).isNotEqualTo(coordenada_trayectoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coordenada_trayectoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coordenada_trayectoMapper.fromId(null)).isNull();
    }
}
