package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Clase;
import com.rally.santafesino.repository.ClaseRepository;
import com.rally.santafesino.service.ClaseService;
import com.rally.santafesino.service.dto.ClaseDTO;
import com.rally.santafesino.service.mapper.ClaseMapper;
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
 * Test class for the ClaseResource REST controller.
 *
 * @see ClaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class ClaseResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private ClaseMapper claseMapper;

    @Autowired
    private ClaseService claseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClaseMockMvc;

    private Clase clase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClaseResource claseResource = new ClaseResource(claseService);
        this.restClaseMockMvc = MockMvcBuilders.standaloneSetup(claseResource)
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
    public static Clase createEntity(EntityManager em) {
        Clase clase = new Clase()
            .nombre(DEFAULT_NOMBRE);
        return clase;
    }

    @Before
    public void initTest() {
        clase = createEntity(em);
    }

    @Test
    @Transactional
    public void createClase() throws Exception {
        int databaseSizeBeforeCreate = claseRepository.findAll().size();

        // Create the Clase
        ClaseDTO claseDTO = claseMapper.toDto(clase);
        restClaseMockMvc.perform(post("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isCreated());

        // Validate the Clase in the database
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeCreate + 1);
        Clase testClase = claseList.get(claseList.size() - 1);
        assertThat(testClase.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createClaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = claseRepository.findAll().size();

        // Create the Clase with an existing ID
        clase.setId(1L);
        ClaseDTO claseDTO = claseMapper.toDto(clase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaseMockMvc.perform(post("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clase in the database
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = claseRepository.findAll().size();
        // set the field null
        clase.setNombre(null);

        // Create the Clase, which fails.
        ClaseDTO claseDTO = claseMapper.toDto(clase);

        restClaseMockMvc.perform(post("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isBadRequest());

        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClases() throws Exception {
        // Initialize the database
        claseRepository.saveAndFlush(clase);

        // Get all the claseList
        restClaseMockMvc.perform(get("/api/clases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clase.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getClase() throws Exception {
        // Initialize the database
        claseRepository.saveAndFlush(clase);

        // Get the clase
        restClaseMockMvc.perform(get("/api/clases/{id}", clase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clase.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClase() throws Exception {
        // Get the clase
        restClaseMockMvc.perform(get("/api/clases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClase() throws Exception {
        // Initialize the database
        claseRepository.saveAndFlush(clase);
        int databaseSizeBeforeUpdate = claseRepository.findAll().size();

        // Update the clase
        Clase updatedClase = claseRepository.findOne(clase.getId());
        // Disconnect from session so that the updates on updatedClase are not directly saved in db
        em.detach(updatedClase);
        updatedClase
            .nombre(UPDATED_NOMBRE);
        ClaseDTO claseDTO = claseMapper.toDto(updatedClase);

        restClaseMockMvc.perform(put("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isOk());

        // Validate the Clase in the database
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeUpdate);
        Clase testClase = claseList.get(claseList.size() - 1);
        assertThat(testClase.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingClase() throws Exception {
        int databaseSizeBeforeUpdate = claseRepository.findAll().size();

        // Create the Clase
        ClaseDTO claseDTO = claseMapper.toDto(clase);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClaseMockMvc.perform(put("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isCreated());

        // Validate the Clase in the database
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClase() throws Exception {
        // Initialize the database
        claseRepository.saveAndFlush(clase);
        int databaseSizeBeforeDelete = claseRepository.findAll().size();

        // Get the clase
        restClaseMockMvc.perform(delete("/api/clases/{id}", clase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clase.class);
        Clase clase1 = new Clase();
        clase1.setId(1L);
        Clase clase2 = new Clase();
        clase2.setId(clase1.getId());
        assertThat(clase1).isEqualTo(clase2);
        clase2.setId(2L);
        assertThat(clase1).isNotEqualTo(clase2);
        clase1.setId(null);
        assertThat(clase1).isNotEqualTo(clase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaseDTO.class);
        ClaseDTO claseDTO1 = new ClaseDTO();
        claseDTO1.setId(1L);
        ClaseDTO claseDTO2 = new ClaseDTO();
        assertThat(claseDTO1).isNotEqualTo(claseDTO2);
        claseDTO2.setId(claseDTO1.getId());
        assertThat(claseDTO1).isEqualTo(claseDTO2);
        claseDTO2.setId(2L);
        assertThat(claseDTO1).isNotEqualTo(claseDTO2);
        claseDTO1.setId(null);
        assertThat(claseDTO1).isNotEqualTo(claseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(claseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(claseMapper.fromId(null)).isNull();
    }
}
