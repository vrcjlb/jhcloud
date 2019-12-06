package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.JhcloudApp;
import com.vlarco.jhcloud.domain.PartyGroup;
import com.vlarco.jhcloud.repository.PartyGroupRepository;
import com.vlarco.jhcloud.service.PartyGroupService;
import com.vlarco.jhcloud.service.dto.PartyGroupDTO;
import com.vlarco.jhcloud.service.mapper.PartyGroupMapper;
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
 * Integration tests for the {@link PartyGroupResource} REST controller.
 */
@SpringBootTest(classes = JhcloudApp.class)
public class PartyGroupResourceIT {

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_NAME_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SITE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private PartyGroupRepository partyGroupRepository;

    @Autowired
    private PartyGroupMapper partyGroupMapper;

    @Autowired
    private PartyGroupService partyGroupService;

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

    private MockMvc restPartyGroupMockMvc;

    private PartyGroup partyGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyGroupResource partyGroupResource = new PartyGroupResource(partyGroupService);
        this.restPartyGroupMockMvc = MockMvcBuilders.standaloneSetup(partyGroupResource)
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
    public static PartyGroup createEntity(EntityManager em) {
        PartyGroup partyGroup = new PartyGroup()
            .groupName(DEFAULT_GROUP_NAME)
            .groupNameLocal(DEFAULT_GROUP_NAME_LOCAL)
            .siteName(DEFAULT_SITE_NAME)
            .office(DEFAULT_OFFICE)
            .comments(DEFAULT_COMMENTS)
            .logoImageUrl(DEFAULT_LOGO_IMAGE_URL);
        return partyGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyGroup createUpdatedEntity(EntityManager em) {
        PartyGroup partyGroup = new PartyGroup()
            .groupName(UPDATED_GROUP_NAME)
            .groupNameLocal(UPDATED_GROUP_NAME_LOCAL)
            .siteName(UPDATED_SITE_NAME)
            .office(UPDATED_OFFICE)
            .comments(UPDATED_COMMENTS)
            .logoImageUrl(UPDATED_LOGO_IMAGE_URL);
        return partyGroup;
    }

    @BeforeEach
    public void initTest() {
        partyGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyGroup() throws Exception {
        int databaseSizeBeforeCreate = partyGroupRepository.findAll().size();

        // Create the PartyGroup
        PartyGroupDTO partyGroupDTO = partyGroupMapper.toDto(partyGroup);
        restPartyGroupMockMvc.perform(post("/api/party-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeCreate + 1);
        PartyGroup testPartyGroup = partyGroupList.get(partyGroupList.size() - 1);
        assertThat(testPartyGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testPartyGroup.getGroupNameLocal()).isEqualTo(DEFAULT_GROUP_NAME_LOCAL);
        assertThat(testPartyGroup.getSiteName()).isEqualTo(DEFAULT_SITE_NAME);
        assertThat(testPartyGroup.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testPartyGroup.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPartyGroup.getLogoImageUrl()).isEqualTo(DEFAULT_LOGO_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createPartyGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyGroupRepository.findAll().size();

        // Create the PartyGroup with an existing ID
        partyGroup.setId(1L);
        PartyGroupDTO partyGroupDTO = partyGroupMapper.toDto(partyGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyGroupMockMvc.perform(post("/api/party-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyGroupRepository.findAll().size();
        // set the field null
        partyGroup.setGroupName(null);

        // Create the PartyGroup, which fails.
        PartyGroupDTO partyGroupDTO = partyGroupMapper.toDto(partyGroup);

        restPartyGroupMockMvc.perform(post("/api/party-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyGroupDTO)))
            .andExpect(status().isBadRequest());

        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartyGroups() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        // Get all the partyGroupList
        restPartyGroupMockMvc.perform(get("/api/party-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].groupNameLocal").value(hasItem(DEFAULT_GROUP_NAME_LOCAL)))
            .andExpect(jsonPath("$.[*].siteName").value(hasItem(DEFAULT_SITE_NAME)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].logoImageUrl").value(hasItem(DEFAULT_LOGO_IMAGE_URL)));
    }
    
    @Test
    @Transactional
    public void getPartyGroup() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        // Get the partyGroup
        restPartyGroupMockMvc.perform(get("/api/party-groups/{id}", partyGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.groupNameLocal").value(DEFAULT_GROUP_NAME_LOCAL))
            .andExpect(jsonPath("$.siteName").value(DEFAULT_SITE_NAME))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.logoImageUrl").value(DEFAULT_LOGO_IMAGE_URL));
    }

    @Test
    @Transactional
    public void getNonExistingPartyGroup() throws Exception {
        // Get the partyGroup
        restPartyGroupMockMvc.perform(get("/api/party-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyGroup() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();

        // Update the partyGroup
        PartyGroup updatedPartyGroup = partyGroupRepository.findById(partyGroup.getId()).get();
        // Disconnect from session so that the updates on updatedPartyGroup are not directly saved in db
        em.detach(updatedPartyGroup);
        updatedPartyGroup
            .groupName(UPDATED_GROUP_NAME)
            .groupNameLocal(UPDATED_GROUP_NAME_LOCAL)
            .siteName(UPDATED_SITE_NAME)
            .office(UPDATED_OFFICE)
            .comments(UPDATED_COMMENTS)
            .logoImageUrl(UPDATED_LOGO_IMAGE_URL);
        PartyGroupDTO partyGroupDTO = partyGroupMapper.toDto(updatedPartyGroup);

        restPartyGroupMockMvc.perform(put("/api/party-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyGroupDTO)))
            .andExpect(status().isOk());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
        PartyGroup testPartyGroup = partyGroupList.get(partyGroupList.size() - 1);
        assertThat(testPartyGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testPartyGroup.getGroupNameLocal()).isEqualTo(UPDATED_GROUP_NAME_LOCAL);
        assertThat(testPartyGroup.getSiteName()).isEqualTo(UPDATED_SITE_NAME);
        assertThat(testPartyGroup.getOffice()).isEqualTo(UPDATED_OFFICE);
        assertThat(testPartyGroup.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPartyGroup.getLogoImageUrl()).isEqualTo(UPDATED_LOGO_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyGroup() throws Exception {
        int databaseSizeBeforeUpdate = partyGroupRepository.findAll().size();

        // Create the PartyGroup
        PartyGroupDTO partyGroupDTO = partyGroupMapper.toDto(partyGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyGroupMockMvc.perform(put("/api/party-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyGroup in the database
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyGroup() throws Exception {
        // Initialize the database
        partyGroupRepository.saveAndFlush(partyGroup);

        int databaseSizeBeforeDelete = partyGroupRepository.findAll().size();

        // Delete the partyGroup
        restPartyGroupMockMvc.perform(delete("/api/party-groups/{id}", partyGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyGroup> partyGroupList = partyGroupRepository.findAll();
        assertThat(partyGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
