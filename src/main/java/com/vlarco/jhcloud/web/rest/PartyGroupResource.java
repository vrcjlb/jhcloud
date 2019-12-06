package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.service.PartyGroupService;
import com.vlarco.jhcloud.web.rest.errors.BadRequestAlertException;
import com.vlarco.jhcloud.service.dto.PartyGroupDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vlarco.jhcloud.domain.PartyGroup}.
 */
@RestController
@RequestMapping("/api")
public class PartyGroupResource {

    private final Logger log = LoggerFactory.getLogger(PartyGroupResource.class);

    private static final String ENTITY_NAME = "jhcloudPartyGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyGroupService partyGroupService;

    public PartyGroupResource(PartyGroupService partyGroupService) {
        this.partyGroupService = partyGroupService;
    }

    /**
     * {@code POST  /party-groups} : Create a new partyGroup.
     *
     * @param partyGroupDTO the partyGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyGroupDTO, or with status {@code 400 (Bad Request)} if the partyGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-groups")
    public ResponseEntity<PartyGroupDTO> createPartyGroup(@Valid @RequestBody PartyGroupDTO partyGroupDTO) throws URISyntaxException {
        log.debug("REST request to save PartyGroup : {}", partyGroupDTO);
        if (partyGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new partyGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyGroupDTO result = partyGroupService.save(partyGroupDTO);
        return ResponseEntity.created(new URI("/api/party-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-groups} : Updates an existing partyGroup.
     *
     * @param partyGroupDTO the partyGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyGroupDTO,
     * or with status {@code 400 (Bad Request)} if the partyGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-groups")
    public ResponseEntity<PartyGroupDTO> updatePartyGroup(@Valid @RequestBody PartyGroupDTO partyGroupDTO) throws URISyntaxException {
        log.debug("REST request to update PartyGroup : {}", partyGroupDTO);
        if (partyGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyGroupDTO result = partyGroupService.save(partyGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partyGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-groups} : get all the partyGroups.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyGroups in body.
     */
    @GetMapping("/party-groups")
    public List<PartyGroupDTO> getAllPartyGroups() {
        log.debug("REST request to get all PartyGroups");
        return partyGroupService.findAll();
    }

    /**
     * {@code GET  /party-groups/:id} : get the "id" partyGroup.
     *
     * @param id the id of the partyGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-groups/{id}")
    public ResponseEntity<PartyGroupDTO> getPartyGroup(@PathVariable Long id) {
        log.debug("REST request to get PartyGroup : {}", id);
        Optional<PartyGroupDTO> partyGroupDTO = partyGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyGroupDTO);
    }

    /**
     * {@code DELETE  /party-groups/:id} : delete the "id" partyGroup.
     *
     * @param id the id of the partyGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-groups/{id}")
    public ResponseEntity<Void> deletePartyGroup(@PathVariable Long id) {
        log.debug("REST request to delete PartyGroup : {}", id);
        partyGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
