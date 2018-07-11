package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Carrera;
import com.rally.santafesino.repository.CarreraRepository;
import com.rally.santafesino.service.CarreraService;
import com.rally.santafesino.service.dto.CarreraDTO;
import com.rally.santafesino.service.mapper.CarreraMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.rally.santafesino.web.rest.TestUtil.sameInstant;
import static com.rally.santafesino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarreraResource REST controller.
 *
 * @see CarreraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class CarreraResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SEDE = "AAAAAAAAAA";
    private static final String UPDATED_SEDE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_INICIO_INSCRIPCION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INICIO_INSCRIPCION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FINAL_INSCRIPCION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINAL_INSCRIPCION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private CarreraMapper carreraMapper;

    @Autowired
    private CarreraService carreraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarreraMockMvc;

    private Carrera carrera;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarreraResource carreraResource = new CarreraResource(carreraService);
        this.restCarreraMockMvc = MockMvcBuilders.standaloneSetup(carreraResource)
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
    public static Carrera createEntity(EntityManager em) {
        Carrera carrera = new Carrera()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fecha(DEFAULT_FECHA)
            .sede(DEFAULT_SEDE)
            .inicioInscripcion(DEFAULT_INICIO_INSCRIPCION)
            .finalInscripcion(DEFAULT_FINAL_INSCRIPCION);
        return carrera;
    }

    @Before
    public void initTest() {
        carrera = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarrera() throws Exception {
        int databaseSizeBeforeCreate = carreraRepository.findAll().size();

        // Create the Carrera
        CarreraDTO carreraDTO = carreraMapper.toDto(carrera);
        restCarreraMockMvc.perform(post("/api/carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraDTO)))
            .andExpect(status().isCreated());

        // Validate the Carrera in the database
        List<Carrera> carreraList = carreraRepository.findAll();
        assertThat(carreraList).hasSize(databaseSizeBeforeCreate + 1);
        Carrera testCarrera = carreraList.get(carreraList.size() - 1);
        assertThat(testCarrera.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCarrera.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCarrera.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCarrera.getSede()).isEqualTo(DEFAULT_SEDE);
        assertThat(testCarrera.getInicioInscripcion()).isEqualTo(DEFAULT_INICIO_INSCRIPCION);
        assertThat(testCarrera.getFinalInscripcion()).isEqualTo(DEFAULT_FINAL_INSCRIPCION);
    }

    @Test
    @Transactional
    public void createCarreraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carreraRepository.findAll().size();

        // Create the Carrera with an existing ID
        carrera.setId(1L);
        CarreraDTO carreraDTO = carreraMapper.toDto(carrera);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarreraMockMvc.perform(post("/api/carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carrera in the database
        List<Carrera> carreraList = carreraRepository.findAll();
        assertThat(carreraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = carreraRepository.findAll().size();
        // set the field null
        carrera.setNombre(null);

        // Create the Carrera, which fails.
        CarreraDTO carreraDTO = carreraMapper.toDto(carrera);

        restCarreraMockMvc.perform(post("/api/carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraDTO)))
            .andExpect(status().isBadRequest());

        List<Carrera> carreraList = carreraRepository.findAll();
        assertThat(carreraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = carreraRepository.findAll().size();
        // set the field null
        carrera.setFecha(null);

        // Create the Carrera, which fails.
        CarreraDTO carreraDTO = carreraMapper.toDto(carrera);

        restCarreraMockMvc.perform(post("/api/carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraDTO)))
            .andExpect(status().isBadRequest());

        List<Carrera> carreraList = carreraRepository.findAll();
        assertThat(carreraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSedeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carreraRepository.findAll().size();
        // set the field null
        carrera.setSede(null);

        // Create the Carrera, which fails.
        CarreraDTO carreraDTO = carreraMapper.toDto(carrera);

        restCarreraMockMvc.perform(post("/api/carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraDTO)))
            .andExpect(status().isBadRequest());

        List<Carrera> carreraList = carreraRepository.findAll();
        assertThat(carreraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarreras() throws Exception {
        // Initialize the database
        carreraRepository.saveAndFlush(carrera);

        // Get all the carreraList
        restCarreraMockMvc.perform(get("/api/carreras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrera.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].sede").value(hasItem(DEFAULT_SEDE.toString())))
            .andExpect(jsonPath("$.[*].inicioInscripcion").value(hasItem(sameInstant(DEFAULT_INICIO_INSCRIPCION))))
            .andExpect(jsonPath("$.[*].finalInscripcion").value(hasItem(sameInstant(DEFAULT_FINAL_INSCRIPCION))));
    }

    @Test
    @Transactional
    public void getCarrera() throws Exception {
        // Initialize the database
        carreraRepository.saveAndFlush(carrera);

        // Get the carrera
        restCarreraMockMvc.perform(get("/api/carreras/{id}", carrera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carrera.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.sede").value(DEFAULT_SEDE.toString()))
            .andExpect(jsonPath("$.inicioInscripcion").value(sameInstant(DEFAULT_INICIO_INSCRIPCION)))
            .andExpect(jsonPath("$.finalInscripcion").value(sameInstant(DEFAULT_FINAL_INSCRIPCION)));
    }

    @Test
    @Transactional
    public void getNonExistingCarrera() throws Exception {
        // Get the carrera
        restCarreraMockMvc.perform(get("/api/carreras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarrera() throws Exception {
        // Initialize the database
        carreraRepository.saveAndFlush(carrera);
        int databaseSizeBeforeUpdate = carreraRepository.findAll().size();

        // Update the carrera
        Carrera updatedCarrera = carreraRepository.findOne(carrera.getId());
        // Disconnect from session so that the updates on updatedCarrera are not directly saved in db
        em.detach(updatedCarrera);
        updatedCarrera
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA)
            .sede(UPDATED_SEDE)
            .inicioInscripcion(UPDATED_INICIO_INSCRIPCION)
            .finalInscripcion(UPDATED_FINAL_INSCRIPCION);
        CarreraDTO carreraDTO = carreraMapper.toDto(updatedCarrera);

        restCarreraMockMvc.perform(put("/api/carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraDTO)))
            .andExpect(status().isOk());

        // Validate the Carrera in the database
        List<Carrera> carreraList = carreraRepository.findAll();
        assertThat(carreraList).hasSize(databaseSizeBeforeUpdate);
        Carrera testCarrera = carreraList.get(carreraList.size() - 1);
        assertThat(testCarrera.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCarrera.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCarrera.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCarrera.getSede()).isEqualTo(UPDATED_SEDE);
        assertThat(testCarrera.getInicioInscripcion()).isEqualTo(UPDATED_INICIO_INSCRIPCION);
        assertThat(testCarrera.getFinalInscripcion()).isEqualTo(UPDATED_FINAL_INSCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingCarrera() throws Exception {
        int databaseSizeBeforeUpdate = carreraRepository.findAll().size();

        // Create the Carrera
        CarreraDTO carreraDTO = carreraMapper.toDto(carrera);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarreraMockMvc.perform(put("/api/carreras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraDTO)))
            .andExpect(status().isCreated());

        // Validate the Carrera in the database
        List<Carrera> carreraList = carreraRepository.findAll();
        assertThat(carreraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarrera() throws Exception {
        // Initialize the database
        carreraRepository.saveAndFlush(carrera);
        int databaseSizeBeforeDelete = carreraRepository.findAll().size();

        // Get the carrera
        restCarreraMockMvc.perform(delete("/api/carreras/{id}", carrera.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Carrera> carreraList = carreraRepository.findAll();
        assertThat(carreraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carrera.class);
        Carrera carrera1 = new Carrera();
        carrera1.setId(1L);
        Carrera carrera2 = new Carrera();
        carrera2.setId(carrera1.getId());
        assertThat(carrera1).isEqualTo(carrera2);
        carrera2.setId(2L);
        assertThat(carrera1).isNotEqualTo(carrera2);
        carrera1.setId(null);
        assertThat(carrera1).isNotEqualTo(carrera2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarreraDTO.class);
        CarreraDTO carreraDTO1 = new CarreraDTO();
        carreraDTO1.setId(1L);
        CarreraDTO carreraDTO2 = new CarreraDTO();
        assertThat(carreraDTO1).isNotEqualTo(carreraDTO2);
        carreraDTO2.setId(carreraDTO1.getId());
        assertThat(carreraDTO1).isEqualTo(carreraDTO2);
        carreraDTO2.setId(2L);
        assertThat(carreraDTO1).isNotEqualTo(carreraDTO2);
        carreraDTO1.setId(null);
        assertThat(carreraDTO1).isNotEqualTo(carreraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carreraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carreraMapper.fromId(null)).isNull();
    }
}
