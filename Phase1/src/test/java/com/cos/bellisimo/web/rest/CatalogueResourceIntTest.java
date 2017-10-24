package com.cos.bellisimo.web.rest;

import com.cos.bellisimo.BellisimoApp;

import com.cos.bellisimo.domain.Catalogue;
import com.cos.bellisimo.repository.CatalogueRepository;
import com.cos.bellisimo.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CatalogueResource REST controller.
 *
 * @see CatalogueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BellisimoApp.class)
public class CatalogueResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Boolean DEFAULT_ONSPECIAL = false;
    private static final Boolean UPDATED_ONSPECIAL = true;

    private static final Double DEFAULT_SPECIAL_PRICE = 1D;
    private static final Double UPDATED_SPECIAL_PRICE = 2D;

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCatalogueMockMvc;

    private Catalogue catalogue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CatalogueResource catalogueResource = new CatalogueResource(catalogueRepository);
        this.restCatalogueMockMvc = MockMvcBuilders.standaloneSetup(catalogueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .onspecial(DEFAULT_ONSPECIAL)
            .specialPrice(DEFAULT_SPECIAL_PRICE)
            .path(DEFAULT_PATH)
            .valid(DEFAULT_VALID);
        return catalogue;
    }

    @Before
    public void initTest() {
        catalogue = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatalogue() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();

        // Create the Catalogue
        restCatalogueMockMvc.perform(post("/api/catalogues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isCreated());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate + 1);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCatalogue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCatalogue.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCatalogue.isOnspecial()).isEqualTo(DEFAULT_ONSPECIAL);
        assertThat(testCatalogue.getSpecialPrice()).isEqualTo(DEFAULT_SPECIAL_PRICE);
        assertThat(testCatalogue.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testCatalogue.isValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    public void createCatalogueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();

        // Create the Catalogue with an existing ID
        catalogue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogueMockMvc.perform(post("/api/catalogues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCatalogues() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList
        restCatalogueMockMvc.perform(get("/api/catalogues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogue.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].onspecial").value(hasItem(DEFAULT_ONSPECIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].specialPrice").value(hasItem(DEFAULT_SPECIAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
    }

    @Test
    @Transactional
    public void getCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get the catalogue
        restCatalogueMockMvc.perform(get("/api/catalogues/{id}", catalogue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(catalogue.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.onspecial").value(DEFAULT_ONSPECIAL.booleanValue()))
            .andExpect(jsonPath("$.specialPrice").value(DEFAULT_SPECIAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCatalogue() throws Exception {
        // Get the catalogue
        restCatalogueMockMvc.perform(get("/api/catalogues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue
        Catalogue updatedCatalogue = catalogueRepository.findOne(catalogue.getId());
        updatedCatalogue
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .onspecial(UPDATED_ONSPECIAL)
            .specialPrice(UPDATED_SPECIAL_PRICE)
            .path(UPDATED_PATH)
            .valid(UPDATED_VALID);

        restCatalogueMockMvc.perform(put("/api/catalogues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatalogue)))
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCatalogue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCatalogue.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCatalogue.isOnspecial()).isEqualTo(UPDATED_ONSPECIAL);
        assertThat(testCatalogue.getSpecialPrice()).isEqualTo(UPDATED_SPECIAL_PRICE);
        assertThat(testCatalogue.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testCatalogue.isValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    public void updateNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Create the Catalogue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCatalogueMockMvc.perform(put("/api/catalogues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isCreated());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);
        int databaseSizeBeforeDelete = catalogueRepository.findAll().size();

        // Get the catalogue
        restCatalogueMockMvc.perform(delete("/api/catalogues/{id}", catalogue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Catalogue.class);
        Catalogue catalogue1 = new Catalogue();
        catalogue1.setId(1L);
        Catalogue catalogue2 = new Catalogue();
        catalogue2.setId(catalogue1.getId());
        assertThat(catalogue1).isEqualTo(catalogue2);
        catalogue2.setId(2L);
        assertThat(catalogue1).isNotEqualTo(catalogue2);
        catalogue1.setId(null);
        assertThat(catalogue1).isNotEqualTo(catalogue2);
    }
}
