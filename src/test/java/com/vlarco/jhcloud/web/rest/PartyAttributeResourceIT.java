package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.JhcloudApp;
import com.vlarco.jhcloud.domain.PartyAttribute;
import com.vlarco.jhcloud.repository.PartyAttributeRepository;
import com.vlarco.jhcloud.service.PartyAttributeService;
import com.vlarco.jhcloud.service.dto.PartyAttributeDTO;
import com.vlarco.jhcloud.service.mapper.PartyAttributeMapper;
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
 * Integration tests for the {@link PartyAttributeResource} REST controller.
 */
@SpringBootTest(classes = JhcloudApp.class)
public class PartyAttributeResourceIT {

    private static final String DEFAULT_VALUE_ATTRIBUTE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_ATTRIBUTE = "BBBBBBBBBB";

    @Autowired
    private PartyAttributeRepository partyAttributeRepository;

    @Autowired
    private PartyAttributeMapper partyAttributeMapper;

    @Autowired
    private PartyAttributeService partyAttributeService;

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

    private MockMvc restPartyAttributeMockMvc;

    private PartyAttribute partyAttribute;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyAttributeResource partyAttributeResource = new PartyAttributeResource(partyAttributeService);
        this.restPartyAttributeMockMvc = MockMvcBuilders.standaloneSetup(partyAttributeResource)
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
    public static PartyAttribute createEntity(EntityManager em) {
        PartyAttribute partyAttribute = new PartyAttribute()
            .valueAttribute(DEFAULT_VALUE_ATTRIBUTE);
        return partyAttribute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyAttribute createUpdatedEntity(EntityManager em) {
        PartyAttribute partyAttribute = new PartyAttribute()
            .valueAttribute(UPDATED_VALUE_ATTRIBUTE);
        return partyAttribute;
    }

    @BeforeEach
    public void initTest() {
        partyAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyAttribute() throws Exception {
        int databaseSizeBeforeCreate = partyAttributeRepository.findAll().size();

        // Create the PartyAttribute
        PartyAttributeDTO partyAttributeDTO = partyAttributeMapper.toDto(partyAttribute);
        restPartyAttributeMockMvc.perform(post("/api/party-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyAttributeDTO)))
            .andExpect(status().isCreated());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyAttribute testPartyAttribute = partyAttributeList.get(partyAttributeList.size() - 1);
        assertThat(testPartyAttribute.getValueAttribute()).isEqualTo(DEFAULT_VALUE_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void createPartyAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyAttributeRepository.findAll().size();

        // Create the PartyAttribute with an existing ID
        partyAttribute.setId(1L);
        PartyAttributeDTO partyAttributeDTO = partyAttributeMapper.toDto(partyAttribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyAttributeMockMvc.perform(post("/api/party-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyAttributes() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        // Get all the partyAttributeList
        restPartyAttributeMockMvc.perform(get("/api/party-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].valueAttribute").value(hasItem(DEFAULT_VALUE_ATTRIBUTE)));
    }
    
    @Test
    @Transactional
    public void getPartyAttribute() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        // Get the partyAttribute
        restPartyAttributeMockMvc.perform(get("/api/party-attributes/{id}", partyAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyAttribute.getId().intValue()))
            .andExpect(jsonPath("$.valueAttribute").value(DEFAULT_VALUE_ATTRIBUTE));
    }

    @Test
    @Transactional
    public void getNonExistingPartyAttribute() throws Exception {
        // Get the partyAttribute
        restPartyAttributeMockMvc.perform(get("/api/party-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyAttribute() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();

        // Update the partyAttribute
        PartyAttribute updatedPartyAttribute = partyAttributeRepository.findById(partyAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedPartyAttribute are not directly saved in db
        em.detach(updatedPartyAttribute);
        updatedPartyAttribute
            .valueAttribute(UPDATED_VALUE_ATTRIBUTE);
        PartyAttributeDTO partyAttributeDTO = partyAttributeMapper.toDto(updatedPartyAttribute);

        restPartyAttributeMockMvc.perform(put("/api/party-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyAttributeDTO)))
            .andExpect(status().isOk());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
        PartyAttribute testPartyAttribute = partyAttributeList.get(partyAttributeList.size() - 1);
        assertThat(testPartyAttribute.getValueAttribute()).isEqualTo(UPDATED_VALUE_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyAttributeRepository.findAll().size();

        // Create the PartyAttribute
        PartyAttributeDTO partyAttributeDTO = partyAttributeMapper.toDto(partyAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyAttributeMockMvc.perform(put("/api/party-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyAttribute in the database
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyAttribute() throws Exception {
        // Initialize the database
        partyAttributeRepository.saveAndFlush(partyAttribute);

        int databaseSizeBeforeDelete = partyAttributeRepository.findAll().size();

        // Delete the partyAttribute
        restPartyAttributeMockMvc.perform(delete("/api/party-attributes/{id}", partyAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyAttribute> partyAttributeList = partyAttributeRepository.findAll();
        assertThat(partyAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
