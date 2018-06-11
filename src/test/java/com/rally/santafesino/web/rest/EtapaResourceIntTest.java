package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Etapa;
import com.rally.santafesino.repository.EtapaRepository;
import com.rally.santafesino.service.EtapaService;
import com.rally.santafesino.service.dto.EtapaDTO;
import com.rally.santafesino.service.mapper.EtapaMapper;
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
 * Test class for the EtapaResource REST controller.
 *
 * @see EtapaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class EtapaResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private EtapaRepository etapaRepository;

    @Autowired
    private EtapaMapper etapaMapper;

    @Autowired
    private EtapaService etapaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtapaMockMvc;

    private Etapa etapa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtapaResource etapaResource = new EtapaResource(etapaService);
        this.restEtapaMockMvc = MockMvcBuilders.standaloneSetup(etapaResource)
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
    public static Etapa createEntity(EntityManager em) {
        Etapa etapa = new Etapa()
            .descripcion(DEFAULT_DESCRIPCION);
        return etapa;
    }

    @Before
    public void initTest() {
        etapa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtapa() throws Exception {
        int databaseSizeBeforeCreate = etapaRepository.findAll().size();

        // Create the Etapa
        EtapaDTO etapaDTO = etapaMapper.toDto(etapa);
        restEtapaMockMvc.perform(post("/api/etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaDTO)))
            .andExpect(status().isCreated());

        // Validate the Etapa in the database
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeCreate + 1);
        Etapa testEtapa = etapaList.get(etapaList.size() - 1);
        assertThat(testEtapa.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createEtapaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapaRepository.findAll().size();

        // Create the Etapa with an existing ID
        etapa.setId(1L);
        EtapaDTO etapaDTO = etapaMapper.toDto(etapa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapaMockMvc.perform(post("/api/etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etapa in the database
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEtapas() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList
        restEtapaMockMvc.perform(get("/api/etapas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapa.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getEtapa() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get the etapa
        restEtapaMockMvc.perform(get("/api/etapas/{id}", etapa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etapa.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtapa() throws Exception {
        // Get the etapa
        restEtapaMockMvc.perform(get("/api/etapas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapa() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);
        int databaseSizeBeforeUpdate = etapaRepository.findAll().size();

        // Update the etapa
        Etapa updatedEtapa = etapaRepository.findOne(etapa.getId());
        // Disconnect from session so that the updates on updatedEtapa are not directly saved in db
        em.detach(updatedEtapa);
        updatedEtapa
            .descripcion(UPDATED_DESCRIPCION);
        EtapaDTO etapaDTO = etapaMapper.toDto(updatedEtapa);

        restEtapaMockMvc.perform(put("/api/etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaDTO)))
            .andExpect(status().isOk());

        // Validate the Etapa in the database
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeUpdate);
        Etapa testEtapa = etapaList.get(etapaList.size() - 1);
        assertThat(testEtapa.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingEtapa() throws Exception {
        int databaseSizeBeforeUpdate = etapaRepository.findAll().size();

        // Create the Etapa
        EtapaDTO etapaDTO = etapaMapper.toDto(etapa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtapaMockMvc.perform(put("/api/etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaDTO)))
            .andExpect(status().isCreated());

        // Validate the Etapa in the database
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtapa() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);
        int databaseSizeBeforeDelete = etapaRepository.findAll().size();

        // Get the etapa
        restEtapaMockMvc.perform(delete("/api/etapas/{id}", etapa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etapa.class);
        Etapa etapa1 = new Etapa();
        etapa1.setId(1L);
        Etapa etapa2 = new Etapa();
        etapa2.setId(etapa1.getId());
        assertThat(etapa1).isEqualTo(etapa2);
        etapa2.setId(2L);
        assertThat(etapa1).isNotEqualTo(etapa2);
        etapa1.setId(null);
        assertThat(etapa1).isNotEqualTo(etapa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaDTO.class);
        EtapaDTO etapaDTO1 = new EtapaDTO();
        etapaDTO1.setId(1L);
        EtapaDTO etapaDTO2 = new EtapaDTO();
        assertThat(etapaDTO1).isNotEqualTo(etapaDTO2);
        etapaDTO2.setId(etapaDTO1.getId());
        assertThat(etapaDTO1).isEqualTo(etapaDTO2);
        etapaDTO2.setId(2L);
        assertThat(etapaDTO1).isNotEqualTo(etapaDTO2);
        etapaDTO1.setId(null);
        assertThat(etapaDTO1).isNotEqualTo(etapaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(etapaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(etapaMapper.fromId(null)).isNull();
    }
}
