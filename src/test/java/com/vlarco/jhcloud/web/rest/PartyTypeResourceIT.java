package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.JhcloudApp;
import com.vlarco.jhcloud.domain.PartyType;
import com.vlarco.jhcloud.repository.PartyTypeRepository;
import com.vlarco.jhcloud.service.PartyTypeService;
import com.vlarco.jhcloud.service.dto.PartyTypeDTO;
import com.vlarco.jhcloud.service.mapper.PartyTypeMapper;
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
 * Integration tests for the {@link PartyTypeResource} REST controller.
 */
@SpringBootTest(classes = JhcloudApp.class)
public class PartyTypeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PartyTypeRepository partyTypeRepository;

    @Autowired
    private PartyTypeMapper partyTypeMapper;

    @Autowired
    private PartyTypeService partyTypeService;

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

    private MockMvc restPartyTypeMockMvc;

    private PartyType partyType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyTypeResource partyTypeResource = new PartyTypeResource(partyTypeService);
        this.restPartyTypeMockMvc = MockMvcBuilders.standaloneSetup(partyTypeResource)
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
    public static PartyType createEntity(EntityManager em) {
        PartyType partyType = new PartyType()
            .description(DEFAULT_DESCRIPTION);
        return partyType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyType createUpdatedEntity(EntityManager em) {
        PartyType partyType = new PartyType()
            .description(UPDATED_DESCRIPTION);
        return partyType;
    }

    @BeforeEach
    public void initTest() {
        partyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyType() throws Exception {
        int databaseSizeBeforeCreate = partyTypeRepository.findAll().size();

        // Create the PartyType
        PartyTypeDTO partyTypeDTO = partyTypeMapper.toDto(partyType);
        restPartyTypeMockMvc.perform(post("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
        assertThat(testPartyType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPartyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyTypeRepository.findAll().size();

        // Create the PartyType with an existing ID
        partyType.setId(1L);
        PartyTypeDTO partyTypeDTO = partyTypeMapper.toDto(partyType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyTypeMockMvc.perform(post("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyTypeRepository.findAll().size();
        // set the field null
        partyType.setDescription(null);

        // Create the PartyType, which fails.
        PartyTypeDTO partyTypeDTO = partyTypeMapper.toDto(partyType);

        restPartyTypeMockMvc.perform(post("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartyTypes() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        // Get all the partyTypeList
        restPartyTypeMockMvc.perform(get("/api/party-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        // Get the partyType
        restPartyTypeMockMvc.perform(get("/api/party-types/{id}", partyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingPartyType() throws Exception {
        // Get the partyType
        restPartyTypeMockMvc.perform(get("/api/party-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // Update the partyType
        PartyType updatedPartyType = partyTypeRepository.findById(partyType.getId()).get();
        // Disconnect from session so that the updates on updatedPartyType are not directly saved in db
        em.detach(updatedPartyType);
        updatedPartyType
            .description(UPDATED_DESCRIPTION);
        PartyTypeDTO partyTypeDTO = partyTypeMapper.toDto(updatedPartyType);

        restPartyTypeMockMvc.perform(put("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
        assertThat(testPartyType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // Create the PartyType
        PartyTypeDTO partyTypeDTO = partyTypeMapper.toDto(partyType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc.perform(put("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        int databaseSizeBeforeDelete = partyTypeRepository.findAll().size();

        // Delete the partyType
        restPartyTypeMockMvc.perform(delete("/api/party-types/{id}", partyType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
