package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.JhcloudApp;
import com.vlarco.jhcloud.domain.PartyTypeAttribute;
import com.vlarco.jhcloud.repository.PartyTypeAttributeRepository;
import com.vlarco.jhcloud.service.PartyTypeAttributeService;
import com.vlarco.jhcloud.service.dto.PartyTypeAttributeDTO;
import com.vlarco.jhcloud.service.mapper.PartyTypeAttributeMapper;
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
 * Integration tests for the {@link PartyTypeAttributeResource} REST controller.
 */
@SpringBootTest(classes = JhcloudApp.class)
public class PartyTypeAttributeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PartyTypeAttributeRepository partyTypeAttributeRepository;

    @Autowired
    private PartyTypeAttributeMapper partyTypeAttributeMapper;

    @Autowired
    private PartyTypeAttributeService partyTypeAttributeService;

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

    private MockMvc restPartyTypeAttributeMockMvc;

    private PartyTypeAttribute partyTypeAttribute;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyTypeAttributeResource partyTypeAttributeResource = new PartyTypeAttributeResource(partyTypeAttributeService);
        this.restPartyTypeAttributeMockMvc = MockMvcBuilders.standaloneSetup(partyTypeAttributeResource)
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
    public static PartyTypeAttribute createEntity(EntityManager em) {
        PartyTypeAttribute partyTypeAttribute = new PartyTypeAttribute()
            .description(DEFAULT_DESCRIPTION);
        return partyTypeAttribute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyTypeAttribute createUpdatedEntity(EntityManager em) {
        PartyTypeAttribute partyTypeAttribute = new PartyTypeAttribute()
            .description(UPDATED_DESCRIPTION);
        return partyTypeAttribute;
    }

    @BeforeEach
    public void initTest() {
        partyTypeAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeCreate = partyTypeAttributeRepository.findAll().size();

        // Create the PartyTypeAttribute
        PartyTypeAttributeDTO partyTypeAttributeDTO = partyTypeAttributeMapper.toDto(partyTypeAttribute);
        restPartyTypeAttributeMockMvc.perform(post("/api/party-type-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeAttributeDTO)))
            .andExpect(status().isCreated());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyTypeAttribute testPartyTypeAttribute = partyTypeAttributeList.get(partyTypeAttributeList.size() - 1);
        assertThat(testPartyTypeAttribute.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPartyTypeAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyTypeAttributeRepository.findAll().size();

        // Create the PartyTypeAttribute with an existing ID
        partyTypeAttribute.setId(1L);
        PartyTypeAttributeDTO partyTypeAttributeDTO = partyTypeAttributeMapper.toDto(partyTypeAttribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyTypeAttributeMockMvc.perform(post("/api/party-type-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyTypeAttributes() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        // Get all the partyTypeAttributeList
        restPartyTypeAttributeMockMvc.perform(get("/api/party-type-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyTypeAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPartyTypeAttribute() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        // Get the partyTypeAttribute
        restPartyTypeAttributeMockMvc.perform(get("/api/party-type-attributes/{id}", partyTypeAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyTypeAttribute.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingPartyTypeAttribute() throws Exception {
        // Get the partyTypeAttribute
        restPartyTypeAttributeMockMvc.perform(get("/api/party-type-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyTypeAttribute() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();

        // Update the partyTypeAttribute
        PartyTypeAttribute updatedPartyTypeAttribute = partyTypeAttributeRepository.findById(partyTypeAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedPartyTypeAttribute are not directly saved in db
        em.detach(updatedPartyTypeAttribute);
        updatedPartyTypeAttribute
            .description(UPDATED_DESCRIPTION);
        PartyTypeAttributeDTO partyTypeAttributeDTO = partyTypeAttributeMapper.toDto(updatedPartyTypeAttribute);

        restPartyTypeAttributeMockMvc.perform(put("/api/party-type-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeAttributeDTO)))
            .andExpect(status().isOk());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
        PartyTypeAttribute testPartyTypeAttribute = partyTypeAttributeList.get(partyTypeAttributeList.size() - 1);
        assertThat(testPartyTypeAttribute.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyTypeAttribute() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeAttributeRepository.findAll().size();

        // Create the PartyTypeAttribute
        PartyTypeAttributeDTO partyTypeAttributeDTO = partyTypeAttributeMapper.toDto(partyTypeAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyTypeAttributeMockMvc.perform(put("/api/party-type-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyTypeAttribute in the database
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyTypeAttribute() throws Exception {
        // Initialize the database
        partyTypeAttributeRepository.saveAndFlush(partyTypeAttribute);

        int databaseSizeBeforeDelete = partyTypeAttributeRepository.findAll().size();

        // Delete the partyTypeAttribute
        restPartyTypeAttributeMockMvc.perform(delete("/api/party-type-attributes/{id}", partyTypeAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyTypeAttribute> partyTypeAttributeList = partyTypeAttributeRepository.findAll();
        assertThat(partyTypeAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
