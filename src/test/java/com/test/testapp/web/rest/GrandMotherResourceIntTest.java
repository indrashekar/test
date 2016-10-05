package com.test.testapp.web.rest;

import com.test.testapp.Test1App;

import com.test.testapp.domain.GrandMother;
import com.test.testapp.repository.GrandMotherRepository;
import com.test.testapp.service.GrandMotherService;

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
 * Test class for the GrandMotherResource REST controller.
 *
 * @see GrandMotherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Test1App.class)
public class GrandMotherResourceIntTest {

    private static final String DEFAULT_MOTHER_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBB";

    private static final Integer DEFAULT_MOTHER_AGE = 1;
    private static final Integer UPDATED_MOTHER_AGE = 2;
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    @Inject
    private GrandMotherRepository grandMotherRepository;

    @Inject
    private GrandMotherService grandMotherService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGrandMotherMockMvc;

    private GrandMother grandMother;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GrandMotherResource grandMotherResource = new GrandMotherResource();
        ReflectionTestUtils.setField(grandMotherResource, "grandMotherService", grandMotherService);
        this.restGrandMotherMockMvc = MockMvcBuilders.standaloneSetup(grandMotherResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrandMother createEntity(EntityManager em) {
        GrandMother grandMother = new GrandMother()
                .motherName(DEFAULT_MOTHER_NAME)
                .motherAge(DEFAULT_MOTHER_AGE)
                .address(DEFAULT_ADDRESS);
        return grandMother;
    }

    @Before
    public void initTest() {
        grandMother = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrandMother() throws Exception {
        int databaseSizeBeforeCreate = grandMotherRepository.findAll().size();

        // Create the GrandMother

        restGrandMotherMockMvc.perform(post("/api/grand-mothers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grandMother)))
                .andExpect(status().isCreated());

        // Validate the GrandMother in the database
        List<GrandMother> grandMothers = grandMotherRepository.findAll();
        assertThat(grandMothers).hasSize(databaseSizeBeforeCreate + 1);
        GrandMother testGrandMother = grandMothers.get(grandMothers.size() - 1);
        assertThat(testGrandMother.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testGrandMother.getMotherAge()).isEqualTo(DEFAULT_MOTHER_AGE);
        assertThat(testGrandMother.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllGrandMothers() throws Exception {
        // Initialize the database
        grandMotherRepository.saveAndFlush(grandMother);

        // Get all the grandMothers
        restGrandMotherMockMvc.perform(get("/api/grand-mothers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(grandMother.getId().intValue())))
                .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].motherAge").value(hasItem(DEFAULT_MOTHER_AGE)))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getGrandMother() throws Exception {
        // Initialize the database
        grandMotherRepository.saveAndFlush(grandMother);

        // Get the grandMother
        restGrandMotherMockMvc.perform(get("/api/grand-mothers/{id}", grandMother.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grandMother.getId().intValue()))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME.toString()))
            .andExpect(jsonPath("$.motherAge").value(DEFAULT_MOTHER_AGE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrandMother() throws Exception {
        // Get the grandMother
        restGrandMotherMockMvc.perform(get("/api/grand-mothers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrandMother() throws Exception {
        // Initialize the database
        grandMotherService.save(grandMother);

        int databaseSizeBeforeUpdate = grandMotherRepository.findAll().size();

        // Update the grandMother
        GrandMother updatedGrandMother = grandMotherRepository.findOne(grandMother.getId());
        updatedGrandMother
                .motherName(UPDATED_MOTHER_NAME)
                .motherAge(UPDATED_MOTHER_AGE)
                .address(UPDATED_ADDRESS);

        restGrandMotherMockMvc.perform(put("/api/grand-mothers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGrandMother)))
                .andExpect(status().isOk());

        // Validate the GrandMother in the database
        List<GrandMother> grandMothers = grandMotherRepository.findAll();
        assertThat(grandMothers).hasSize(databaseSizeBeforeUpdate);
        GrandMother testGrandMother = grandMothers.get(grandMothers.size() - 1);
        assertThat(testGrandMother.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testGrandMother.getMotherAge()).isEqualTo(UPDATED_MOTHER_AGE);
        assertThat(testGrandMother.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteGrandMother() throws Exception {
        // Initialize the database
        grandMotherService.save(grandMother);

        int databaseSizeBeforeDelete = grandMotherRepository.findAll().size();

        // Get the grandMother
        restGrandMotherMockMvc.perform(delete("/api/grand-mothers/{id}", grandMother.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GrandMother> grandMothers = grandMotherRepository.findAll();
        assertThat(grandMothers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
