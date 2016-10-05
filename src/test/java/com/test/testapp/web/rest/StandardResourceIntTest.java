package com.test.testapp.web.rest;

import com.test.testapp.Test1App;

import com.test.testapp.domain.Standard;
import com.test.testapp.repository.StandardRepository;
import com.test.testapp.service.StandardService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StandardResource REST controller.
 *
 * @see StandardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Test1App.class)
public class StandardResourceIntTest {

    private static final String DEFAULT_STANDARD = "AAAAA";
    private static final String UPDATED_STANDARD = "BBBBB";
    private static final String DEFAULT_DIVISION = "AAAAA";
    private static final String UPDATED_DIVISION = "BBBBB";

    @Inject
    private StandardRepository standardRepository;

    @Inject
    private StandardService standardService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStandardMockMvc;

    private Standard standard;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StandardResource standardResource = new StandardResource();
        ReflectionTestUtils.setField(standardResource, "standardService", standardService);
        this.restStandardMockMvc = MockMvcBuilders.standaloneSetup(standardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Standard createEntity(EntityManager em) {
        Standard standard = new Standard()
                .standard(DEFAULT_STANDARD)
                .division(DEFAULT_DIVISION);
        return standard;
    }

    @Before
    public void initTest() {
        standard = createEntity(em);
    }

    @Test
    @Transactional
    public void createStandard() throws Exception {
        int databaseSizeBeforeCreate = standardRepository.findAll().size();

        // Create the Standard

        restStandardMockMvc.perform(post("/api/standards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(standard)))
                .andExpect(status().isCreated());

        // Validate the Standard in the database
        List<Standard> standards = standardRepository.findAll();
        assertThat(standards).hasSize(databaseSizeBeforeCreate + 1);
        Standard testStandard = standards.get(standards.size() - 1);
        assertThat(testStandard.getStandard()).isEqualTo(DEFAULT_STANDARD);
        assertThat(testStandard.getDivision()).isEqualTo(DEFAULT_DIVISION);
    }

    @Test
    @Transactional
    public void getAllStandards() throws Exception {
        // Initialize the database
        standardRepository.saveAndFlush(standard);

        // Get all the standards
        restStandardMockMvc.perform(get("/api/standards?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(standard.getId().intValue())))
                .andExpect(jsonPath("$.[*].standard").value(hasItem(DEFAULT_STANDARD.toString())))
                .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION.toString())));
    }

    @Test
    @Transactional
    public void getStandard() throws Exception {
        // Initialize the database
        standardRepository.saveAndFlush(standard);

        // Get the standard
        restStandardMockMvc.perform(get("/api/standards/{id}", standard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(standard.getId().intValue()))
            .andExpect(jsonPath("$.standard").value(DEFAULT_STANDARD.toString()))
            .andExpect(jsonPath("$.division").value(DEFAULT_DIVISION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStandard() throws Exception {
        // Get the standard
        restStandardMockMvc.perform(get("/api/standards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStandard() throws Exception {
        // Initialize the database
        standardService.save(standard);

        int databaseSizeBeforeUpdate = standardRepository.findAll().size();

        // Update the standard
        Standard updatedStandard = standardRepository.findOne(standard.getId());
        updatedStandard
                .standard(UPDATED_STANDARD)
                .division(UPDATED_DIVISION);

        restStandardMockMvc.perform(put("/api/standards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStandard)))
                .andExpect(status().isOk());

        // Validate the Standard in the database
        List<Standard> standards = standardRepository.findAll();
        assertThat(standards).hasSize(databaseSizeBeforeUpdate);
        Standard testStandard = standards.get(standards.size() - 1);
        assertThat(testStandard.getStandard()).isEqualTo(UPDATED_STANDARD);
        assertThat(testStandard.getDivision()).isEqualTo(UPDATED_DIVISION);
    }

    @Test
    @Transactional
    public void deleteStandard() throws Exception {
        // Initialize the database
        standardService.save(standard);

        int databaseSizeBeforeDelete = standardRepository.findAll().size();

        // Get the standard
        restStandardMockMvc.perform(delete("/api/standards/{id}", standard.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Standard> standards = standardRepository.findAll();
        assertThat(standards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
