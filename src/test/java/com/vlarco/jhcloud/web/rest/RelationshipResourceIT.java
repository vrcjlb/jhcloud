package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.JhcloudApp;
import com.vlarco.jhcloud.domain.Relationship;
import com.vlarco.jhcloud.repository.RelationshipRepository;
import com.vlarco.jhcloud.service.RelationshipService;
import com.vlarco.jhcloud.service.dto.RelationshipDTO;
import com.vlarco.jhcloud.service.mapper.RelationshipMapper;
import com.vlarco.jhcloud.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vlarco.jhcloud.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RelationshipResource} REST controller.
 */
@SpringBootTest(classes = JhcloudApp.class)
public class RelationshipResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRelationshipMockMvc;

    private Relationship relationship;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RelationshipResource relationshipResource = new RelationshipResource(relationshipService);
        this.restRelationshipMockMvc = MockMvcBuilders.standaloneSetup(relationshipResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relationship createEntity(EntityManager em) {
        Relationship relationship = new Relationship()
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return relationship;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relationship createUpdatedEntity(EntityManager em) {
        Relationship relationship = new Relationship()
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return relationship;
    }

    @BeforeEach
    public void initTest() {
        relationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationship() throws Exception {
        int databaseSizeBeforeCreate = relationshipRepository.findAll().size();

        // Create the Relationship
        RelationshipDTO relationshipDTO = relationshipMapper.toDto(relationship);
        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipDTO)))
            .andExpect(status().isCreated());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeCreate + 1);
        Relationship testRelationship = relationshipList.get(relationshipList.size() - 1);
        assertThat(testRelationship.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRelationship.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipRepository.findAll().size();

        // Create the Relationship with an existing ID
        relationship.setId(1L);
        RelationshipDTO relationshipDTO = relationshipMapper.toDto(relationship);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipMockMvc.perform(post("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRelationships() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);

        // Get all the relationshipList
        restRelationshipMockMvc.perform(get("/api/relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);

        // Get the relationship
        restRelationshipMockMvc.perform(get("/api/relationships/{id}", relationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relationship.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRelationship() throws Exception {
        // Get the relationship
        restRelationshipMockMvc.perform(get("/api/relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);

        int databaseSizeBeforeUpdate = relationshipRepository.findAll().size();

        // Update the relationship
        Relationship updatedRelationship = relationshipRepository.findById(relationship.getId()).get();
        // Disconnect from session so that the updates on updatedRelationship are not directly saved in db
        em.detach(updatedRelationship);
        updatedRelationship
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        RelationshipDTO relationshipDTO = relationshipMapper.toDto(updatedRelationship);

        restRelationshipMockMvc.perform(put("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipDTO)))
            .andExpect(status().isOk());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeUpdate);
        Relationship testRelationship = relationshipList.get(relationshipList.size() - 1);
        assertThat(testRelationship.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRelationship.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationship() throws Exception {
        int databaseSizeBeforeUpdate = relationshipRepository.findAll().size();

        // Create the Relationship
        RelationshipDTO relationshipDTO = relationshipMapper.toDto(relationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationshipMockMvc.perform(put("/api/relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Relationship in the database
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRelationship() throws Exception {
        // Initialize the database
        relationshipRepository.saveAndFlush(relationship);

        int databaseSizeBeforeDelete = relationshipRepository.findAll().size();

        // Delete the relationship
        restRelationshipMockMvc.perform(delete("/api/relationships/{id}", relationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Relationship> relationshipList = relationshipRepository.findAll();
        assertThat(relationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
