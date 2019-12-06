package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.service.PartyRelationshipService;
import com.vlarco.jhcloud.web.rest.errors.BadRequestAlertException;
import com.vlarco.jhcloud.service.dto.PartyRelationshipDTO;

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
 * REST controller for managing {@link com.vlarco.jhcloud.domain.PartyRelationship}.
 */
@RestController
@RequestMapping("/api")
public class PartyRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(PartyRelationshipResource.class);

    private static final String ENTITY_NAME = "jhcloudPartyRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRelationshipService partyRelationshipService;

    public PartyRelationshipResource(PartyRelationshipService partyRelationshipService) {
        this.partyRelationshipService = partyRelationshipService;
    }

    /**
     * {@code POST  /party-relationships} : Create a new partyRelationship.
     *
     * @param partyRelationshipDTO the partyRelationshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyRelationshipDTO, or with status {@code 400 (Bad Request)} if the partyRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-relationships")
    public ResponseEntity<PartyRelationshipDTO> createPartyRelationship(@RequestBody PartyRelationshipDTO partyRelationshipDTO) throws URISyntaxException {
        log.debug("REST request to save PartyRelationship : {}", partyRelationshipDTO);
        if (partyRelationshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new partyRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyRelationshipDTO result = partyRelationshipService.save(partyRelationshipDTO);
        return ResponseEntity.created(new URI("/api/party-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-relationships} : Updates an existing partyRelationship.
     *
     * @param partyRelationshipDTO the partyRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the partyRelationshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-relationships")
    public ResponseEntity<PartyRelationshipDTO> updatePartyRelationship(@RequestBody PartyRelationshipDTO partyRelationshipDTO) throws URISyntaxException {
        log.debug("REST request to update PartyRelationship : {}", partyRelationshipDTO);
        if (partyRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyRelationshipDTO result = partyRelationshipService.save(partyRelationshipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partyRelationshipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-relationships} : get all the partyRelationships.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyRelationships in body.
     */
    @GetMapping("/party-relationships")
    public List<PartyRelationshipDTO> getAllPartyRelationships() {
        log.debug("REST request to get all PartyRelationships");
        return partyRelationshipService.findAll();
    }

    /**
     * {@code GET  /party-relationships/:id} : get the "id" partyRelationship.
     *
     * @param id the id of the partyRelationshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyRelationshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-relationships/{id}")
    public ResponseEntity<PartyRelationshipDTO> getPartyRelationship(@PathVariable Long id) {
        log.debug("REST request to get PartyRelationship : {}", id);
        Optional<PartyRelationshipDTO> partyRelationshipDTO = partyRelationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyRelationshipDTO);
    }

    /**
     * {@code DELETE  /party-relationships/:id} : delete the "id" partyRelationship.
     *
     * @param id the id of the partyRelationshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-relationships/{id}")
    public ResponseEntity<Void> deletePartyRelationship(@PathVariable Long id) {
        log.debug("REST request to delete PartyRelationship : {}", id);
        partyRelationshipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
