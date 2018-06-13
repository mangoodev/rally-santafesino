package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Trayecto;
import com.rally.santafesino.repository.TrayectoRepository;
import com.rally.santafesino.service.TrayectoService;
import com.rally.santafesino.service.dto.TrayectoDTO;
import com.rally.santafesino.service.mapper.TrayectoMapper;
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
 * Test class for the TrayectoResource REST controller.
 *
 * @see TrayectoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class TrayectoResourceIntTest {

    @Autowired
    private TrayectoRepository trayectoRepository;

    @Autowired
    private TrayectoMapper trayectoMapper;

    @Autowired
    private TrayectoService trayectoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrayectoMockMvc;

    private Trayecto trayecto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrayectoResource trayectoResource = new TrayectoResource(trayectoService);
        this.restTrayectoMockMvc = MockMvcBuilders.standaloneSetup(trayectoResource)
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
    public static Trayecto createEntity(EntityManager em) {
        Trayecto trayecto = new Trayecto();
        return trayecto;
    }

    @Before
    public void initTest() {
        trayecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrayecto() throws Exception {
        int databaseSizeBeforeCreate = trayectoRepository.findAll().size();

        // Create the Trayecto
        TrayectoDTO trayectoDTO = trayectoMapper.toDto(trayecto);
        restTrayectoMockMvc.perform(post("/api/trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayectoDTO)))
            .andExpect(status().isCreated());

        // Validate the Trayecto in the database
        List<Trayecto> trayectoList = trayectoRepository.findAll();
        assertThat(trayectoList).hasSize(databaseSizeBeforeCreate + 1);
        Trayecto testTrayecto = trayectoList.get(trayectoList.size() - 1);
    }

    @Test
    @Transactional
    public void createTrayectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trayectoRepository.findAll().size();

        // Create the Trayecto with an existing ID
        trayecto.setId(1L);
        TrayectoDTO trayectoDTO = trayectoMapper.toDto(trayecto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrayectoMockMvc.perform(post("/api/trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trayecto in the database
        List<Trayecto> trayectoList = trayectoRepository.findAll();
        assertThat(trayectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrayectos() throws Exception {
        // Initialize the database
        trayectoRepository.saveAndFlush(trayecto);

        // Get all the trayectoList
        restTrayectoMockMvc.perform(get("/api/trayectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trayecto.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTrayecto() throws Exception {
        // Initialize the database
        trayectoRepository.saveAndFlush(trayecto);

        // Get the trayecto
        restTrayectoMockMvc.perform(get("/api/trayectos/{id}", trayecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trayecto.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrayecto() throws Exception {
        // Get the trayecto
        restTrayectoMockMvc.perform(get("/api/trayectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrayecto() throws Exception {
        // Initialize the database
        trayectoRepository.saveAndFlush(trayecto);
        int databaseSizeBeforeUpdate = trayectoRepository.findAll().size();

        // Update the trayecto
        Trayecto updatedTrayecto = trayectoRepository.findOne(trayecto.getId());
        // Disconnect from session so that the updates on updatedTrayecto are not directly saved in db
        em.detach(updatedTrayecto);
        TrayectoDTO trayectoDTO = trayectoMapper.toDto(updatedTrayecto);

        restTrayectoMockMvc.perform(put("/api/trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayectoDTO)))
            .andExpect(status().isOk());

        // Validate the Trayecto in the database
        List<Trayecto> trayectoList = trayectoRepository.findAll();
        assertThat(trayectoList).hasSize(databaseSizeBeforeUpdate);
        Trayecto testTrayecto = trayectoList.get(trayectoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTrayecto() throws Exception {
        int databaseSizeBeforeUpdate = trayectoRepository.findAll().size();

        // Create the Trayecto
        TrayectoDTO trayectoDTO = trayectoMapper.toDto(trayecto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrayectoMockMvc.perform(put("/api/trayectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trayectoDTO)))
            .andExpect(status().isCreated());

        // Validate the Trayecto in the database
        List<Trayecto> trayectoList = trayectoRepository.findAll();
        assertThat(trayectoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrayecto() throws Exception {
        // Initialize the database
        trayectoRepository.saveAndFlush(trayecto);
        int databaseSizeBeforeDelete = trayectoRepository.findAll().size();

        // Get the trayecto
        restTrayectoMockMvc.perform(delete("/api/trayectos/{id}", trayecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trayecto> trayectoList = trayectoRepository.findAll();
        assertThat(trayectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trayecto.class);
        Trayecto trayecto1 = new Trayecto();
        trayecto1.setId(1L);
        Trayecto trayecto2 = new Trayecto();
        trayecto2.setId(trayecto1.getId());
        assertThat(trayecto1).isEqualTo(trayecto2);
        trayecto2.setId(2L);
        assertThat(trayecto1).isNotEqualTo(trayecto2);
        trayecto1.setId(null);
        assertThat(trayecto1).isNotEqualTo(trayecto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrayectoDTO.class);
        TrayectoDTO trayectoDTO1 = new TrayectoDTO();
        trayectoDTO1.setId(1L);
        TrayectoDTO trayectoDTO2 = new TrayectoDTO();
        assertThat(trayectoDTO1).isNotEqualTo(trayectoDTO2);
        trayectoDTO2.setId(trayectoDTO1.getId());
        assertThat(trayectoDTO1).isEqualTo(trayectoDTO2);
        trayectoDTO2.setId(2L);
        assertThat(trayectoDTO1).isNotEqualTo(trayectoDTO2);
        trayectoDTO1.setId(null);
        assertThat(trayectoDTO1).isNotEqualTo(trayectoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trayectoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trayectoMapper.fromId(null)).isNull();
    }
}
