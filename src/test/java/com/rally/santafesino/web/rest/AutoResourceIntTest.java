package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Auto;
import com.rally.santafesino.repository.AutoRepository;
import com.rally.santafesino.service.AutoService;
import com.rally.santafesino.service.dto.AutoDTO;
import com.rally.santafesino.service.mapper.AutoMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.rally.santafesino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AutoResource REST controller.
 *
 * @see AutoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class AutoResourceIntTest {

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CLASE = "AAAAAAAAAA";
    private static final String UPDATED_CLASE = "BBBBBBBBBB";

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private AutoMapper autoMapper;

    @Autowired
    private AutoService autoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAutoMockMvc;

    private Auto auto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutoResource autoResource = new AutoResource(autoService);
        this.restAutoMockMvc = MockMvcBuilders.standaloneSetup(autoResource)
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
    public static Auto createEntity(EntityManager em) {
        Auto auto = new Auto()
            .marca(DEFAULT_MARCA)
            .modelo(DEFAULT_MODELO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .descripcion(DEFAULT_DESCRIPCION)
            .clase(DEFAULT_CLASE);
        return auto;
    }

    @Before
    public void initTest() {
        auto = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuto() throws Exception {
        int databaseSizeBeforeCreate = autoRepository.findAll().size();

        // Create the Auto
        AutoDTO autoDTO = autoMapper.toDto(auto);
        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto in the database
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeCreate + 1);
        Auto testAuto = autoList.get(autoList.size() - 1);
        assertThat(testAuto.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testAuto.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testAuto.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testAuto.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testAuto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAuto.getClase()).isEqualTo(DEFAULT_CLASE);
    }

    @Test
    @Transactional
    public void createAutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autoRepository.findAll().size();

        // Create the Auto with an existing ID
        auto.setId(1L);
        AutoDTO autoDTO = autoMapper.toDto(auto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auto in the database
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setMarca(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setModelo(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setClase(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutos() throws Exception {
        // Initialize the database
        autoRepository.saveAndFlush(auto);

        // Get all the autoList
        restAutoMockMvc.perform(get("/api/autos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auto.getId().intValue())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].clase").value(hasItem(DEFAULT_CLASE.toString())));
    }

    @Test
    @Transactional
    public void getAuto() throws Exception {
        // Initialize the database
        autoRepository.saveAndFlush(auto);

        // Get the auto
        restAutoMockMvc.perform(get("/api/autos/{id}", auto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auto.getId().intValue()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.clase").value(DEFAULT_CLASE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuto() throws Exception {
        // Get the auto
        restAutoMockMvc.perform(get("/api/autos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuto() throws Exception {
        // Initialize the database
        autoRepository.saveAndFlush(auto);
        int databaseSizeBeforeUpdate = autoRepository.findAll().size();

        // Update the auto
        Auto updatedAuto = autoRepository.findOne(auto.getId());
        // Disconnect from session so that the updates on updatedAuto are not directly saved in db
        em.detach(updatedAuto);
        updatedAuto
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .descripcion(UPDATED_DESCRIPCION)
            .clase(UPDATED_CLASE);
        AutoDTO autoDTO = autoMapper.toDto(updatedAuto);

        restAutoMockMvc.perform(put("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isOk());

        // Validate the Auto in the database
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeUpdate);
        Auto testAuto = autoList.get(autoList.size() - 1);
        assertThat(testAuto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testAuto.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testAuto.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testAuto.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testAuto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAuto.getClase()).isEqualTo(UPDATED_CLASE);
    }

    @Test
    @Transactional
    public void updateNonExistingAuto() throws Exception {
        int databaseSizeBeforeUpdate = autoRepository.findAll().size();

        // Create the Auto
        AutoDTO autoDTO = autoMapper.toDto(auto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAutoMockMvc.perform(put("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto in the database
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuto() throws Exception {
        // Initialize the database
        autoRepository.saveAndFlush(auto);
        int databaseSizeBeforeDelete = autoRepository.findAll().size();

        // Get the auto
        restAutoMockMvc.perform(delete("/api/autos/{id}", auto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auto.class);
        Auto auto1 = new Auto();
        auto1.setId(1L);
        Auto auto2 = new Auto();
        auto2.setId(auto1.getId());
        assertThat(auto1).isEqualTo(auto2);
        auto2.setId(2L);
        assertThat(auto1).isNotEqualTo(auto2);
        auto1.setId(null);
        assertThat(auto1).isNotEqualTo(auto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoDTO.class);
        AutoDTO autoDTO1 = new AutoDTO();
        autoDTO1.setId(1L);
        AutoDTO autoDTO2 = new AutoDTO();
        assertThat(autoDTO1).isNotEqualTo(autoDTO2);
        autoDTO2.setId(autoDTO1.getId());
        assertThat(autoDTO1).isEqualTo(autoDTO2);
        autoDTO2.setId(2L);
        assertThat(autoDTO1).isNotEqualTo(autoDTO2);
        autoDTO1.setId(null);
        assertThat(autoDTO1).isNotEqualTo(autoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autoMapper.fromId(null)).isNull();
    }
}
