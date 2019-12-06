package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.JhcloudApp;
import com.vlarco.jhcloud.domain.PartyRelationship;
import com.vlarco.jhcloud.repository.PartyRelationshipRepository;
import com.vlarco.jhcloud.service.PartyRelationshipService;
import com.vlarco.jhcloud.service.dto.PartyRelationshipDTO;
import com.vlarco.jhcloud.service.mapper.PartyRelationshipMapper;
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
 * Integration tests for the {@link PartyRelationshipResource} REST controller.
 */
@SpringBootTest(classes = JhcloudApp.class)
public class PartyRelationshipResourceIT {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private PartyRelationshipRepository partyRelationshipRepository;

    @Autowired
    private PartyRelationshipMapper partyRelationshipMapper;

    @Autowired
    private PartyRelationshipService partyRelationshipService;

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

    private MockMvc restPartyRelationshipMockMvc;

    private PartyRelationship partyRelationship;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyRelationshipResource partyRelationshipResource = new PartyRelationshipResource(partyRelationshipService);
        this.restPartyRelationshipMockMvc = MockMvcBuilders.standaloneSetup(partyRelationshipResource)
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
    public static PartyRelationship createEntity(EntityManager em) {
        PartyRelationship partyRelationship = new PartyRelationship()
            .status(DEFAULT_STATUS);
        return partyRelationship;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRelationship createUpdatedEntity(EntityManager em) {
        PartyRelationship partyRelationship = new PartyRelationship()
            .status(UPDATED_STATUS);
        return partyRelationship;
    }

    @BeforeEach
    public void initTest() {
        partyRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyRelationship() throws Exception {
        int databaseSizeBeforeCreate = partyRelationshipRepository.findAll().size();

        // Create the PartyRelationship
        PartyRelationshipDTO partyRelationshipDTO = partyRelationshipMapper.toDto(partyRelationship);
        restPartyRelationshipMockMvc.perform(post("/api/party-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyRelationshipDTO)))
            .andExpect(status().isCreated());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        PartyRelationship testPartyRelationship = partyRelationshipList.get(partyRelationshipList.size() - 1);
        assertThat(testPartyRelationship.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPartyRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRelationshipRepository.findAll().size();

        // Create the PartyRelationship with an existing ID
        partyRelationship.setId(1L);
        PartyRelationshipDTO partyRelationshipDTO = partyRelationshipMapper.toDto(partyRelationship);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyRelationshipMockMvc.perform(post("/api/party-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyRelationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyRelationships() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        // Get all the partyRelationshipList
        restPartyRelationshipMockMvc.perform(get("/api/party-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        // Get the partyRelationship
        restPartyRelationshipMockMvc.perform(get("/api/party-relationships/{id}", partyRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyRelationship.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartyRelationship() throws Exception {
        // Get the partyRelationship
        restPartyRelationshipMockMvc.perform(get("/api/party-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();

        // Update the partyRelationship
        PartyRelationship updatedPartyRelationship = partyRelationshipRepository.findById(partyRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedPartyRelationship are not directly saved in db
        em.detach(updatedPartyRelationship);
        updatedPartyRelationship
            .status(UPDATED_STATUS);
        PartyRelationshipDTO partyRelationshipDTO = partyRelationshipMapper.toDto(updatedPartyRelationship);

        restPartyRelationshipMockMvc.perform(put("/api/party-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyRelationshipDTO)))
            .andExpect(status().isOk());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
        PartyRelationship testPartyRelationship = partyRelationshipList.get(partyRelationshipList.size() - 1);
        assertThat(testPartyRelationship.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();

        // Create the PartyRelationship
        PartyRelationshipDTO partyRelationshipDTO = partyRelationshipMapper.toDto(partyRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRelationshipMockMvc.perform(put("/api/party-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyRelationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        int databaseSizeBeforeDelete = partyRelationshipRepository.findAll().size();

        // Delete the partyRelationship
        restPartyRelationshipMockMvc.perform(delete("/api/party-relationships/{id}", partyRelationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
