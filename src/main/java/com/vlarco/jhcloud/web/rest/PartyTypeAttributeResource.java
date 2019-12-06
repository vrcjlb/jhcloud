package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.service.PartyTypeAttributeService;
import com.vlarco.jhcloud.web.rest.errors.BadRequestAlertException;
import com.vlarco.jhcloud.service.dto.PartyTypeAttributeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vlarco.jhcloud.domain.PartyTypeAttribute}.
 */
@RestController
@RequestMapping("/api")
public class PartyTypeAttributeResource {

    private final Logger log = LoggerFactory.getLogger(PartyTypeAttributeResource.class);

    private static final String ENTITY_NAME = "jhcloudPartyTypeAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyTypeAttributeService partyTypeAttributeService;

    public PartyTypeAttributeResource(PartyTypeAttributeService partyTypeAttributeService) {
        this.partyTypeAttributeService = partyTypeAttributeService;
    }

    /**
     * {@code POST  /party-type-attributes} : Create a new partyTypeAttribute.
     *
     * @param partyTypeAttributeDTO the partyTypeAttributeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyTypeAttributeDTO, or with status {@code 400 (Bad Request)} if the partyTypeAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-type-attributes")
    public ResponseEntity<PartyTypeAttributeDTO> createPartyTypeAttribute(@RequestBody PartyTypeAttributeDTO partyTypeAttributeDTO) throws URISyntaxException {
        log.debug("REST request to save PartyTypeAttribute : {}", partyTypeAttributeDTO);
        if (partyTypeAttributeDTO.getId() != null) {
            throw new BadRequestAlertException("A new partyTypeAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyTypeAttributeDTO result = partyTypeAttributeService.save(partyTypeAttributeDTO);
        return ResponseEntity.created(new URI("/api/party-type-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-type-attributes} : Updates an existing partyTypeAttribute.
     *
     * @param partyTypeAttributeDTO the partyTypeAttributeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyTypeAttributeDTO,
     * or with status {@code 400 (Bad Request)} if the partyTypeAttributeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyTypeAttributeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-type-attributes")
    public ResponseEntity<PartyTypeAttributeDTO> updatePartyTypeAttribute(@RequestBody PartyTypeAttributeDTO partyTypeAttributeDTO) throws URISyntaxException {
        log.debug("REST request to update PartyTypeAttribute : {}", partyTypeAttributeDTO);
        if (partyTypeAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyTypeAttributeDTO result = partyTypeAttributeService.save(partyTypeAttributeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partyTypeAttributeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-type-attributes} : get all the partyTypeAttributes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyTypeAttributes in body.
     */
    @GetMapping("/party-type-attributes")
    public List<PartyTypeAttributeDTO> getAllPartyTypeAttributes() {
        log.debug("REST request to get all PartyTypeAttributes");
        return partyTypeAttributeService.findAll();
    }

    /**
     * {@code GET  /party-type-attributes/:id} : get the "id" partyTypeAttribute.
     *
     * @param id the id of the partyTypeAttributeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyTypeAttributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-type-attributes/{id}")
    public ResponseEntity<PartyTypeAttributeDTO> getPartyTypeAttribute(@PathVariable Long id) {
        log.debug("REST request to get PartyTypeAttribute : {}", id);
        Optional<PartyTypeAttributeDTO> partyTypeAttributeDTO = partyTypeAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyTypeAttributeDTO);
    }

    /**
     * {@code DELETE  /party-type-attributes/:id} : delete the "id" partyTypeAttribute.
     *
     * @param id the id of the partyTypeAttributeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-type-attributes/{id}")
    public ResponseEntity<Void> deletePartyTypeAttribute(@PathVariable Long id) {
        log.debug("REST request to delete PartyTypeAttribute : {}", id);
        partyTypeAttributeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
