package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.JhcloudApp;
import com.vlarco.jhcloud.domain.Party;
import com.vlarco.jhcloud.repository.PartyRepository;
import com.vlarco.jhcloud.service.PartyService;
import com.vlarco.jhcloud.service.dto.PartyDTO;
import com.vlarco.jhcloud.service.mapper.PartyMapper;
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
 * Integration tests for the {@link PartyResource} REST controller.
 */
@SpringBootTest(classes = JhcloudApp.class)
public class PartyResourceIT {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyMapper partyMapper;

    @Autowired
    private PartyService partyService;

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

    private MockMvc restPartyMockMvc;

    private Party party;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyResource partyResource = new PartyResource(partyService);
        this.restPartyMockMvc = MockMvcBuilders.standaloneSetup(partyResource)
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
    public static Party createEntity(EntityManager em) {
        Party party = new Party()
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION);
        return party;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Party createUpdatedEntity(EntityManager em) {
        Party party = new Party()
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION);
        return party;
    }

    @BeforeEach
    public void initTest() {
        party = createEntity(em);
    }

    @Test
    @Transactional
    public void createParty() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party
        PartyDTO partyDTO = partyMapper.toDto(party);
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isCreated());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate + 1);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testParty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPartyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party with an existing ID
        party.setId(1L);
        PartyDTO partyDTO = partyMapper.toDto(party);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setStatus(null);

        // Create the Party, which fails.
        PartyDTO partyDTO = partyMapper.toDto(party);

        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isBadRequest());

        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParties() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", party.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(party.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingParty() throws Exception {
        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Update the party
        Party updatedParty = partyRepository.findById(party.getId()).get();
        // Disconnect from session so that the updates on updatedParty are not directly saved in db
        em.detach(updatedParty);
        updatedParty
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION);
        PartyDTO partyDTO = partyMapper.toDto(updatedParty);

        restPartyMockMvc.perform(put("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isOk());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testParty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingParty() throws Exception {
        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Create the Party
        PartyDTO partyDTO = partyMapper.toDto(party);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyMockMvc.perform(put("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        int databaseSizeBeforeDelete = partyRepository.findAll().size();

        // Delete the party
        restPartyMockMvc.perform(delete("/api/parties/{id}", party.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
