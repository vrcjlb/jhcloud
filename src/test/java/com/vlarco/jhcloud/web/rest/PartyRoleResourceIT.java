package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.JhcloudApp;
import com.vlarco.jhcloud.domain.PartyRole;
import com.vlarco.jhcloud.repository.PartyRoleRepository;
import com.vlarco.jhcloud.service.PartyRoleService;
import com.vlarco.jhcloud.service.dto.PartyRoleDTO;
import com.vlarco.jhcloud.service.mapper.PartyRoleMapper;
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
 * Integration tests for the {@link PartyRoleResource} REST controller.
 */
@SpringBootTest(classes = JhcloudApp.class)
public class PartyRoleResourceIT {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private PartyRoleRepository partyRoleRepository;

    @Autowired
    private PartyRoleMapper partyRoleMapper;

    @Autowired
    private PartyRoleService partyRoleService;

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

    private MockMvc restPartyRoleMockMvc;

    private PartyRole partyRole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyRoleResource partyRoleResource = new PartyRoleResource(partyRoleService);
        this.restPartyRoleMockMvc = MockMvcBuilders.standaloneSetup(partyRoleResource)
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
    public static PartyRole createEntity(EntityManager em) {
        PartyRole partyRole = new PartyRole()
            .status(DEFAULT_STATUS);
        return partyRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRole createUpdatedEntity(EntityManager em) {
        PartyRole partyRole = new PartyRole()
            .status(UPDATED_STATUS);
        return partyRole;
    }

    @BeforeEach
    public void initTest() {
        partyRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyRole() throws Exception {
        int databaseSizeBeforeCreate = partyRoleRepository.findAll().size();

        // Create the PartyRole
        PartyRoleDTO partyRoleDTO = partyRoleMapper.toDto(partyRole);
        restPartyRoleMockMvc.perform(post("/api/party-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeCreate + 1);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
        assertThat(testPartyRole.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPartyRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRoleRepository.findAll().size();

        // Create the PartyRole with an existing ID
        partyRole.setId(1L);
        PartyRoleDTO partyRoleDTO = partyRoleMapper.toDto(partyRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyRoleMockMvc.perform(post("/api/party-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyRoles() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        // Get all the partyRoleList
        restPartyRoleMockMvc.perform(get("/api/party-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        // Get the partyRole
        restPartyRoleMockMvc.perform(get("/api/party-roles/{id}", partyRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyRole.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartyRole() throws Exception {
        // Get the partyRole
        restPartyRoleMockMvc.perform(get("/api/party-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // Update the partyRole
        PartyRole updatedPartyRole = partyRoleRepository.findById(partyRole.getId()).get();
        // Disconnect from session so that the updates on updatedPartyRole are not directly saved in db
        em.detach(updatedPartyRole);
        updatedPartyRole
            .status(UPDATED_STATUS);
        PartyRoleDTO partyRoleDTO = partyRoleMapper.toDto(updatedPartyRole);

        restPartyRoleMockMvc.perform(put("/api/party-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyRoleDTO)))
            .andExpect(status().isOk());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
        assertThat(testPartyRole.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // Create the PartyRole
        PartyRoleDTO partyRoleDTO = partyRoleMapper.toDto(partyRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc.perform(put("/api/party-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        int databaseSizeBeforeDelete = partyRoleRepository.findAll().size();

        // Delete the partyRole
        restPartyRoleMockMvc.perform(delete("/api/party-roles/{id}", partyRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
