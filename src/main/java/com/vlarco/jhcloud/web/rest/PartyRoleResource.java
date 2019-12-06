package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.service.PartyRoleService;
import com.vlarco.jhcloud.web.rest.errors.BadRequestAlertException;
import com.vlarco.jhcloud.service.dto.PartyRoleDTO;

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
 * REST controller for managing {@link com.vlarco.jhcloud.domain.PartyRole}.
 */
@RestController
@RequestMapping("/api")
public class PartyRoleResource {

    private final Logger log = LoggerFactory.getLogger(PartyRoleResource.class);

    private static final String ENTITY_NAME = "jhcloudPartyRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRoleService partyRoleService;

    public PartyRoleResource(PartyRoleService partyRoleService) {
        this.partyRoleService = partyRoleService;
    }

    /**
     * {@code POST  /party-roles} : Create a new partyRole.
     *
     * @param partyRoleDTO the partyRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyRoleDTO, or with status {@code 400 (Bad Request)} if the partyRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-roles")
    public ResponseEntity<PartyRoleDTO> createPartyRole(@RequestBody PartyRoleDTO partyRoleDTO) throws URISyntaxException {
        log.debug("REST request to save PartyRole : {}", partyRoleDTO);
        if (partyRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new partyRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyRoleDTO result = partyRoleService.save(partyRoleDTO);
        return ResponseEntity.created(new URI("/api/party-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-roles} : Updates an existing partyRole.
     *
     * @param partyRoleDTO the partyRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRoleDTO,
     * or with status {@code 400 (Bad Request)} if the partyRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-roles")
    public ResponseEntity<PartyRoleDTO> updatePartyRole(@RequestBody PartyRoleDTO partyRoleDTO) throws URISyntaxException {
        log.debug("REST request to update PartyRole : {}", partyRoleDTO);
        if (partyRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyRoleDTO result = partyRoleService.save(partyRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partyRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-roles} : get all the partyRoles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyRoles in body.
     */
    @GetMapping("/party-roles")
    public List<PartyRoleDTO> getAllPartyRoles() {
        log.debug("REST request to get all PartyRoles");
        return partyRoleService.findAll();
    }

    /**
     * {@code GET  /party-roles/:id} : get the "id" partyRole.
     *
     * @param id the id of the partyRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-roles/{id}")
    public ResponseEntity<PartyRoleDTO> getPartyRole(@PathVariable Long id) {
        log.debug("REST request to get PartyRole : {}", id);
        Optional<PartyRoleDTO> partyRoleDTO = partyRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyRoleDTO);
    }

    /**
     * {@code DELETE  /party-roles/:id} : delete the "id" partyRole.
     *
     * @param id the id of the partyRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-roles/{id}")
    public ResponseEntity<Void> deletePartyRole(@PathVariable Long id) {
        log.debug("REST request to delete PartyRole : {}", id);
        partyRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
