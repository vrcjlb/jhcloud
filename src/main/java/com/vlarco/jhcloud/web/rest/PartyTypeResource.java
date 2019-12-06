package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.service.PartyTypeService;
import com.vlarco.jhcloud.web.rest.errors.BadRequestAlertException;
import com.vlarco.jhcloud.service.dto.PartyTypeDTO;

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
 * REST controller for managing {@link com.vlarco.jhcloud.domain.PartyType}.
 */
@RestController
@RequestMapping("/api")
public class PartyTypeResource {

    private final Logger log = LoggerFactory.getLogger(PartyTypeResource.class);

    private static final String ENTITY_NAME = "jhcloudPartyType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyTypeService partyTypeService;

    public PartyTypeResource(PartyTypeService partyTypeService) {
        this.partyTypeService = partyTypeService;
    }

    /**
     * {@code POST  /party-types} : Create a new partyType.
     *
     * @param partyTypeDTO the partyTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyTypeDTO, or with status {@code 400 (Bad Request)} if the partyType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-types")
    public ResponseEntity<PartyTypeDTO> createPartyType(@Valid @RequestBody PartyTypeDTO partyTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PartyType : {}", partyTypeDTO);
        if (partyTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new partyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyTypeDTO result = partyTypeService.save(partyTypeDTO);
        return ResponseEntity.created(new URI("/api/party-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-types} : Updates an existing partyType.
     *
     * @param partyTypeDTO the partyTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyTypeDTO,
     * or with status {@code 400 (Bad Request)} if the partyTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-types")
    public ResponseEntity<PartyTypeDTO> updatePartyType(@Valid @RequestBody PartyTypeDTO partyTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PartyType : {}", partyTypeDTO);
        if (partyTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyTypeDTO result = partyTypeService.save(partyTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partyTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-types} : get all the partyTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyTypes in body.
     */
    @GetMapping("/party-types")
    public List<PartyTypeDTO> getAllPartyTypes() {
        log.debug("REST request to get all PartyTypes");
        return partyTypeService.findAll();
    }

    /**
     * {@code GET  /party-types/:id} : get the "id" partyType.
     *
     * @param id the id of the partyTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-types/{id}")
    public ResponseEntity<PartyTypeDTO> getPartyType(@PathVariable Long id) {
        log.debug("REST request to get PartyType : {}", id);
        Optional<PartyTypeDTO> partyTypeDTO = partyTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyTypeDTO);
    }

    /**
     * {@code DELETE  /party-types/:id} : delete the "id" partyType.
     *
     * @param id the id of the partyTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-types/{id}")
    public ResponseEntity<Void> deletePartyType(@PathVariable Long id) {
        log.debug("REST request to delete PartyType : {}", id);
        partyTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
