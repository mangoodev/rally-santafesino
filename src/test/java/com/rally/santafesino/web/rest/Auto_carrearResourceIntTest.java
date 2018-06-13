package com.rally.santafesino.web.rest;

import com.rally.santafesino.RallyApp;

import com.rally.santafesino.domain.Auto_carrear;
import com.rally.santafesino.repository.Auto_carrearRepository;
import com.rally.santafesino.service.Auto_carrearService;
import com.rally.santafesino.service.dto.Auto_carrearDTO;
import com.rally.santafesino.service.mapper.Auto_carrearMapper;
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
 * Test class for the Auto_carrearResource REST controller.
 *
 * @see Auto_carrearResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RallyApp.class)
public class Auto_carrearResourceIntTest {

    @Autowired
    private Auto_carrearRepository auto_carrearRepository;

    @Autowired
    private Auto_carrearMapper auto_carrearMapper;

    @Autowired
    private Auto_carrearService auto_carrearService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuto_carrearMockMvc;

    private Auto_carrear auto_carrear;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Auto_carrearResource auto_carrearResource = new Auto_carrearResource(auto_carrearService);
        this.restAuto_carrearMockMvc = MockMvcBuilders.standaloneSetup(auto_carrearResource)
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
    public static Auto_carrear createEntity(EntityManager em) {
        Auto_carrear auto_carrear = new Auto_carrear();
        return auto_carrear;
    }

    @Before
    public void initTest() {
        auto_carrear = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuto_carrear() throws Exception {
        int databaseSizeBeforeCreate = auto_carrearRepository.findAll().size();

        // Create the Auto_carrear
        Auto_carrearDTO auto_carrearDTO = auto_carrearMapper.toDto(auto_carrear);
        restAuto_carrearMockMvc.perform(post("/api/auto-carrears")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carrearDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto_carrear in the database
        List<Auto_carrear> auto_carrearList = auto_carrearRepository.findAll();
        assertThat(auto_carrearList).hasSize(databaseSizeBeforeCreate + 1);
        Auto_carrear testAuto_carrear = auto_carrearList.get(auto_carrearList.size() - 1);
    }

    @Test
    @Transactional
    public void createAuto_carrearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auto_carrearRepository.findAll().size();

        // Create the Auto_carrear with an existing ID
        auto_carrear.setId(1L);
        Auto_carrearDTO auto_carrearDTO = auto_carrearMapper.toDto(auto_carrear);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuto_carrearMockMvc.perform(post("/api/auto-carrears")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carrearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auto_carrear in the database
        List<Auto_carrear> auto_carrearList = auto_carrearRepository.findAll();
        assertThat(auto_carrearList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuto_carrears() throws Exception {
        // Initialize the database
        auto_carrearRepository.saveAndFlush(auto_carrear);

        // Get all the auto_carrearList
        restAuto_carrearMockMvc.perform(get("/api/auto-carrears?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auto_carrear.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAuto_carrear() throws Exception {
        // Initialize the database
        auto_carrearRepository.saveAndFlush(auto_carrear);

        // Get the auto_carrear
        restAuto_carrearMockMvc.perform(get("/api/auto-carrears/{id}", auto_carrear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auto_carrear.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuto_carrear() throws Exception {
        // Get the auto_carrear
        restAuto_carrearMockMvc.perform(get("/api/auto-carrears/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuto_carrear() throws Exception {
        // Initialize the database
        auto_carrearRepository.saveAndFlush(auto_carrear);
        int databaseSizeBeforeUpdate = auto_carrearRepository.findAll().size();

        // Update the auto_carrear
        Auto_carrear updatedAuto_carrear = auto_carrearRepository.findOne(auto_carrear.getId());
        // Disconnect from session so that the updates on updatedAuto_carrear are not directly saved in db
        em.detach(updatedAuto_carrear);
        Auto_carrearDTO auto_carrearDTO = auto_carrearMapper.toDto(updatedAuto_carrear);

        restAuto_carrearMockMvc.perform(put("/api/auto-carrears")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carrearDTO)))
            .andExpect(status().isOk());

        // Validate the Auto_carrear in the database
        List<Auto_carrear> auto_carrearList = auto_carrearRepository.findAll();
        assertThat(auto_carrearList).hasSize(databaseSizeBeforeUpdate);
        Auto_carrear testAuto_carrear = auto_carrearList.get(auto_carrearList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAuto_carrear() throws Exception {
        int databaseSizeBeforeUpdate = auto_carrearRepository.findAll().size();

        // Create the Auto_carrear
        Auto_carrearDTO auto_carrearDTO = auto_carrearMapper.toDto(auto_carrear);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuto_carrearMockMvc.perform(put("/api/auto-carrears")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auto_carrearDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto_carrear in the database
        List<Auto_carrear> auto_carrearList = auto_carrearRepository.findAll();
        assertThat(auto_carrearList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuto_carrear() throws Exception {
        // Initialize the database
        auto_carrearRepository.saveAndFlush(auto_carrear);
        int databaseSizeBeforeDelete = auto_carrearRepository.findAll().size();

        // Get the auto_carrear
        restAuto_carrearMockMvc.perform(delete("/api/auto-carrears/{id}", auto_carrear.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Auto_carrear> auto_carrearList = auto_carrearRepository.findAll();
        assertThat(auto_carrearList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auto_carrear.class);
        Auto_carrear auto_carrear1 = new Auto_carrear();
        auto_carrear1.setId(1L);
        Auto_carrear auto_carrear2 = new Auto_carrear();
        auto_carrear2.setId(auto_carrear1.getId());
        assertThat(auto_carrear1).isEqualTo(auto_carrear2);
        auto_carrear2.setId(2L);
        assertThat(auto_carrear1).isNotEqualTo(auto_carrear2);
        auto_carrear1.setId(null);
        assertThat(auto_carrear1).isNotEqualTo(auto_carrear2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auto_carrearDTO.class);
        Auto_carrearDTO auto_carrearDTO1 = new Auto_carrearDTO();
        auto_carrearDTO1.setId(1L);
        Auto_carrearDTO auto_carrearDTO2 = new Auto_carrearDTO();
        assertThat(auto_carrearDTO1).isNotEqualTo(auto_carrearDTO2);
        auto_carrearDTO2.setId(auto_carrearDTO1.getId());
        assertThat(auto_carrearDTO1).isEqualTo(auto_carrearDTO2);
        auto_carrearDTO2.setId(2L);
        assertThat(auto_carrearDTO1).isNotEqualTo(auto_carrearDTO2);
        auto_carrearDTO1.setId(null);
        assertThat(auto_carrearDTO1).isNotEqualTo(auto_carrearDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(auto_carrearMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(auto_carrearMapper.fromId(null)).isNull();
    }
}
