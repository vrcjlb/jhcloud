package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.service.PartyAttributeService;
import com.vlarco.jhcloud.web.rest.errors.BadRequestAlertException;
import com.vlarco.jhcloud.service.dto.PartyAttributeDTO;

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
 * REST controller for managing {@link com.vlarco.jhcloud.domain.PartyAttribute}.
 */
@RestController
@RequestMapping("/api")
public class PartyAttributeResource {

    private final Logger log = LoggerFactory.getLogger(PartyAttributeResource.class);

    private static final String ENTITY_NAME = "jhcloudPartyAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyAttributeService partyAttributeService;

    public PartyAttributeResource(PartyAttributeService partyAttributeService) {
        this.partyAttributeService = partyAttributeService;
    }

    /**
     * {@code POST  /party-attributes} : Create a new partyAttribute.
     *
     * @param partyAttributeDTO the partyAttributeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyAttributeDTO, or with status {@code 400 (Bad Request)} if the partyAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-attributes")
    public ResponseEntity<PartyAttributeDTO> createPartyAttribute(@RequestBody PartyAttributeDTO partyAttributeDTO) throws URISyntaxException {
        log.debug("REST request to save PartyAttribute : {}", partyAttributeDTO);
        if (partyAttributeDTO.getId() != null) {
            throw new BadRequestAlertException("A new partyAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyAttributeDTO result = partyAttributeService.save(partyAttributeDTO);
        return ResponseEntity.created(new URI("/api/party-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-attributes} : Updates an existing partyAttribute.
     *
     * @param partyAttributeDTO the partyAttributeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyAttributeDTO,
     * or with status {@code 400 (Bad Request)} if the partyAttributeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyAttributeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-attributes")
    public ResponseEntity<PartyAttributeDTO> updatePartyAttribute(@RequestBody PartyAttributeDTO partyAttributeDTO) throws URISyntaxException {
        log.debug("REST request to update PartyAttribute : {}", partyAttributeDTO);
        if (partyAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyAttributeDTO result = partyAttributeService.save(partyAttributeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partyAttributeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-attributes} : get all the partyAttributes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyAttributes in body.
     */
    @GetMapping("/party-attributes")
    public List<PartyAttributeDTO> getAllPartyAttributes() {
        log.debug("REST request to get all PartyAttributes");
        return partyAttributeService.findAll();
    }

    /**
     * {@code GET  /party-attributes/:id} : get the "id" partyAttribute.
     *
     * @param id the id of the partyAttributeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyAttributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-attributes/{id}")
    public ResponseEntity<PartyAttributeDTO> getPartyAttribute(@PathVariable Long id) {
        log.debug("REST request to get PartyAttribute : {}", id);
        Optional<PartyAttributeDTO> partyAttributeDTO = partyAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyAttributeDTO);
    }

    /**
     * {@code DELETE  /party-attributes/:id} : delete the "id" partyAttribute.
     *
     * @param id the id of the partyAttributeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-attributes/{id}")
    public ResponseEntity<Void> deletePartyAttribute(@PathVariable Long id) {
        log.debug("REST request to delete PartyAttribute : {}", id);
        partyAttributeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
