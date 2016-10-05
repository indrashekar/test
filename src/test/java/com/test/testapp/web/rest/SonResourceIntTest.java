package com.test.testapp.web.rest;

import com.test.testapp.Test1App;

import com.test.testapp.domain.Son;
import com.test.testapp.repository.SonRepository;
import com.test.testapp.service.SonService;

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
 * Test class for the SonResource REST controller.
 *
 * @see SonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Test1App.class)
public class SonResourceIntTest {

    private static final String DEFAULT_FISRT_NAME = "AAAAA";
    private static final String UPDATED_FISRT_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_FATHER_NAME = "AAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBB";

    @Inject
    private SonRepository sonRepository;

    @Inject
    private SonService sonService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSonMockMvc;

    private Son son;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SonResource sonResource = new SonResource();
        ReflectionTestUtils.setField(sonResource, "sonService", sonService);
        this.restSonMockMvc = MockMvcBuilders.standaloneSetup(sonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Son createEntity(EntityManager em) {
        Son son = new Son()
                .fisrtName(DEFAULT_FISRT_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .age(DEFAULT_AGE)
                .address(DEFAULT_ADDRESS)
                .fatherName(DEFAULT_FATHER_NAME);
        return son;
    }

    @Before
    public void initTest() {
        son = createEntity(em);
    }

    @Test
    @Transactional
    public void createSon() throws Exception {
        int databaseSizeBeforeCreate = sonRepository.findAll().size();

        // Create the Son

        restSonMockMvc.perform(post("/api/sons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(son)))
                .andExpect(status().isCreated());

        // Validate the Son in the database
        List<Son> sons = sonRepository.findAll();
        assertThat(sons).hasSize(databaseSizeBeforeCreate + 1);
        Son testSon = sons.get(sons.size() - 1);
        assertThat(testSon.getFisrtName()).isEqualTo(DEFAULT_FISRT_NAME);
        assertThat(testSon.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSon.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSon.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSon.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllSons() throws Exception {
        // Initialize the database
        sonRepository.saveAndFlush(son);

        // Get all the sons
        restSonMockMvc.perform(get("/api/sons?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(son.getId().intValue())))
                .andExpect(jsonPath("$.[*].fisrtName").value(hasItem(DEFAULT_FISRT_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSon() throws Exception {
        // Initialize the database
        sonRepository.saveAndFlush(son);

        // Get the son
        restSonMockMvc.perform(get("/api/sons/{id}", son.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(son.getId().intValue()))
            .andExpect(jsonPath("$.fisrtName").value(DEFAULT_FISRT_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSon() throws Exception {
        // Get the son
        restSonMockMvc.perform(get("/api/sons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSon() throws Exception {
        // Initialize the database
        sonService.save(son);

        int databaseSizeBeforeUpdate = sonRepository.findAll().size();

        // Update the son
        Son updatedSon = sonRepository.findOne(son.getId());
        updatedSon
                .fisrtName(UPDATED_FISRT_NAME)
                .lastName(UPDATED_LAST_NAME)
                .age(UPDATED_AGE)
                .address(UPDATED_ADDRESS)
                .fatherName(UPDATED_FATHER_NAME);

        restSonMockMvc.perform(put("/api/sons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSon)))
                .andExpect(status().isOk());

        // Validate the Son in the database
        List<Son> sons = sonRepository.findAll();
        assertThat(sons).hasSize(databaseSizeBeforeUpdate);
        Son testSon = sons.get(sons.size() - 1);
        assertThat(testSon.getFisrtName()).isEqualTo(UPDATED_FISRT_NAME);
        assertThat(testSon.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSon.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSon.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSon.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void deleteSon() throws Exception {
        // Initialize the database
        sonService.save(son);

        int databaseSizeBeforeDelete = sonRepository.findAll().size();

        // Get the son
        restSonMockMvc.perform(delete("/api/sons/{id}", son.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Son> sons = sonRepository.findAll();
        assertThat(sons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
