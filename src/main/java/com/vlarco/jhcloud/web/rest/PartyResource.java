package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.service.PartyService;
import com.vlarco.jhcloud.web.rest.errors.BadRequestAlertException;
import com.vlarco.jhcloud.service.dto.PartyDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.vlarco.jhcloud.domain.Party}.
 */
@RestController
@RequestMapping("/api")
public class PartyResource {

    private final Logger log = LoggerFactory.getLogger(PartyResource.class);

    private static final String ENTITY_NAME = "jhcloudParty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyService partyService;

    public PartyResource(PartyService partyService) {
        this.partyService = partyService;
    }

    /**
     * {@code POST  /parties} : Create a new party.
     *
     * @param partyDTO the partyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyDTO, or with status {@code 400 (Bad Request)} if the party has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parties")
    public ResponseEntity<PartyDTO> createParty(@Valid @RequestBody PartyDTO partyDTO) throws URISyntaxException {
        log.debug("REST request to save Party : {}", partyDTO);
        if (partyDTO.getId() != null) {
            throw new BadRequestAlertException("A new party cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyDTO result = partyService.save(partyDTO);
        return ResponseEntity.created(new URI("/api/parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parties} : Updates an existing party.
     *
     * @param partyDTO the partyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyDTO,
     * or with status {@code 400 (Bad Request)} if the partyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parties")
    public ResponseEntity<PartyDTO> updateParty(@Valid @RequestBody PartyDTO partyDTO) throws URISyntaxException {
        log.debug("REST request to update Party : {}", partyDTO);
        if (partyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyDTO result = partyService.save(partyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parties} : get all the parties.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parties in body.
     */
    @GetMapping("/parties")
    public List<PartyDTO> getAllParties(@RequestParam(required = false) String filter) {
        if ("person-is-null".equals(filter)) {
            log.debug("REST request to get all Partys where person is null");
            return partyService.findAllWherePersonIsNull();
        }
        if ("partygroup-is-null".equals(filter)) {
            log.debug("REST request to get all Partys where partyGroup is null");
            return partyService.findAllWherePartyGroupIsNull();
        }
        log.debug("REST request to get all Parties");
        return partyService.findAll();
    }

    /**
     * {@code GET  /parties/:id} : get the "id" party.
     *
     * @param id the id of the partyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parties/{id}")
    public ResponseEntity<PartyDTO> getParty(@PathVariable Long id) {
        log.debug("REST request to get Party : {}", id);
        Optional<PartyDTO> partyDTO = partyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyDTO);
    }

    /**
     * {@code DELETE  /parties/:id} : delete the "id" party.
     *
     * @param id the id of the partyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parties/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        log.debug("REST request to delete Party : {}", id);
        partyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
