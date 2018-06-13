package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Pruebas;
import com.rally.santafesino.repository.PruebasRepository;
import com.rally.santafesino.service.PruebasService;
import com.rally.santafesino.service.dto.PruebasDTO;
import com.rally.santafesino.service.mapper.PruebasMapper;
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
 * Test class for the PruebasResource REST controller.
 *
 * @see PruebasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class PruebasResourceIntTest {

    private static final Integer DEFAULT_NUMERO_DE_PRUEBA = 1;
    private static final Integer UPDATED_NUMERO_DE_PRUEBA = 2;

    private static final Float DEFAULT_LONGITUD = 1F;
    private static final Float UPDATED_LONGITUD = 2F;

    @Autowired
    private PruebasRepository pruebasRepository;

    @Autowired
    private PruebasMapper pruebasMapper;

    @Autowired
    private PruebasService pruebasService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPruebasMockMvc;

    private Pruebas pruebas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PruebasResource pruebasResource = new PruebasResource(pruebasService);
        this.restPruebasMockMvc = MockMvcBuilders.standaloneSetup(pruebasResource)
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
    public static Pruebas createEntity(EntityManager em) {
        Pruebas pruebas = new Pruebas()
            .numeroDePrueba(DEFAULT_NUMERO_DE_PRUEBA)
            .longitud(DEFAULT_LONGITUD);
        return pruebas;
    }

    @Before
    public void initTest() {
        pruebas = createEntity(em);
    }

    @Test
    @Transactional
    public void createPruebas() throws Exception {
        int databaseSizeBeforeCreate = pruebasRepository.findAll().size();

        // Create the Pruebas
        PruebasDTO pruebasDTO = pruebasMapper.toDto(pruebas);
        restPruebasMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebasDTO)))
            .andExpect(status().isCreated());

        // Validate the Pruebas in the database
        List<Pruebas> pruebasList = pruebasRepository.findAll();
        assertThat(pruebasList).hasSize(databaseSizeBeforeCreate + 1);
        Pruebas testPruebas = pruebasList.get(pruebasList.size() - 1);
        assertThat(testPruebas.getNumeroDePrueba()).isEqualTo(DEFAULT_NUMERO_DE_PRUEBA);
        assertThat(testPruebas.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
    }

    @Test
    @Transactional
    public void createPruebasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pruebasRepository.findAll().size();

        // Create the Pruebas with an existing ID
        pruebas.setId(1L);
        PruebasDTO pruebasDTO = pruebasMapper.toDto(pruebas);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPruebasMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pruebas in the database
        List<Pruebas> pruebasList = pruebasRepository.findAll();
        assertThat(pruebasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroDePruebaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebasRepository.findAll().size();
        // set the field null
        pruebas.setNumeroDePrueba(null);

        // Create the Pruebas, which fails.
        PruebasDTO pruebasDTO = pruebasMapper.toDto(pruebas);

        restPruebasMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebasDTO)))
            .andExpect(status().isBadRequest());

        List<Pruebas> pruebasList = pruebasRepository.findAll();
        assertThat(pruebasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPruebas() throws Exception {
        // Initialize the database
        pruebasRepository.saveAndFlush(pruebas);

        // Get all the pruebasList
        restPruebasMockMvc.perform(get("/api/pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pruebas.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDePrueba").value(hasItem(DEFAULT_NUMERO_DE_PRUEBA)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())));
    }

    @Test
    @Transactional
    public void getPruebas() throws Exception {
        // Initialize the database
        pruebasRepository.saveAndFlush(pruebas);

        // Get the pruebas
        restPruebasMockMvc.perform(get("/api/pruebas/{id}", pruebas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pruebas.getId().intValue()))
            .andExpect(jsonPath("$.numeroDePrueba").value(DEFAULT_NUMERO_DE_PRUEBA))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPruebas() throws Exception {
        // Get the pruebas
        restPruebasMockMvc.perform(get("/api/pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePruebas() throws Exception {
        // Initialize the database
        pruebasRepository.saveAndFlush(pruebas);
        int databaseSizeBeforeUpdate = pruebasRepository.findAll().size();

        // Update the pruebas
        Pruebas updatedPruebas = pruebasRepository.findOne(pruebas.getId());
        // Disconnect from session so that the updates on updatedPruebas are not directly saved in db
        em.detach(updatedPruebas);
        updatedPruebas
            .numeroDePrueba(UPDATED_NUMERO_DE_PRUEBA)
            .longitud(UPDATED_LONGITUD);
        PruebasDTO pruebasDTO = pruebasMapper.toDto(updatedPruebas);

        restPruebasMockMvc.perform(put("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebasDTO)))
            .andExpect(status().isOk());

        // Validate the Pruebas in the database
        List<Pruebas> pruebasList = pruebasRepository.findAll();
        assertThat(pruebasList).hasSize(databaseSizeBeforeUpdate);
        Pruebas testPruebas = pruebasList.get(pruebasList.size() - 1);
        assertThat(testPruebas.getNumeroDePrueba()).isEqualTo(UPDATED_NUMERO_DE_PRUEBA);
        assertThat(testPruebas.getLongitud()).isEqualTo(UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    public void updateNonExistingPruebas() throws Exception {
        int databaseSizeBeforeUpdate = pruebasRepository.findAll().size();

        // Create the Pruebas
        PruebasDTO pruebasDTO = pruebasMapper.toDto(pruebas);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPruebasMockMvc.perform(put("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebasDTO)))
            .andExpect(status().isCreated());

        // Validate the Pruebas in the database
        List<Pruebas> pruebasList = pruebasRepository.findAll();
        assertThat(pruebasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePruebas() throws Exception {
        // Initialize the database
        pruebasRepository.saveAndFlush(pruebas);
        int databaseSizeBeforeDelete = pruebasRepository.findAll().size();

        // Get the pruebas
        restPruebasMockMvc.perform(delete("/api/pruebas/{id}", pruebas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pruebas> pruebasList = pruebasRepository.findAll();
        assertThat(pruebasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pruebas.class);
        Pruebas pruebas1 = new Pruebas();
        pruebas1.setId(1L);
        Pruebas pruebas2 = new Pruebas();
        pruebas2.setId(pruebas1.getId());
        assertThat(pruebas1).isEqualTo(pruebas2);
        pruebas2.setId(2L);
        assertThat(pruebas1).isNotEqualTo(pruebas2);
        pruebas1.setId(null);
        assertThat(pruebas1).isNotEqualTo(pruebas2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PruebasDTO.class);
        PruebasDTO pruebasDTO1 = new PruebasDTO();
        pruebasDTO1.setId(1L);
        PruebasDTO pruebasDTO2 = new PruebasDTO();
        assertThat(pruebasDTO1).isNotEqualTo(pruebasDTO2);
        pruebasDTO2.setId(pruebasDTO1.getId());
        assertThat(pruebasDTO1).isEqualTo(pruebasDTO2);
        pruebasDTO2.setId(2L);
        assertThat(pruebasDTO1).isNotEqualTo(pruebasDTO2);
        pruebasDTO1.setId(null);
        assertThat(pruebasDTO1).isNotEqualTo(pruebasDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pruebasMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pruebasMapper.fromId(null)).isNull();
    }
}
