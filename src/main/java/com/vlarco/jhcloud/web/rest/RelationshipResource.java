package com.vlarco.jhcloud.web.rest;

import com.vlarco.jhcloud.service.RelationshipService;
import com.vlarco.jhcloud.web.rest.errors.BadRequestAlertException;
import com.vlarco.jhcloud.service.dto.RelationshipDTO;

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
 * REST controller for managing {@link com.vlarco.jhcloud.domain.Relationship}.
 */
@RestController
@RequestMapping("/api")
public class RelationshipResource {

    private final Logger log = LoggerFactory.getLogger(RelationshipResource.class);

    private static final String ENTITY_NAME = "jhcloudRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelationshipService relationshipService;

    public RelationshipResource(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    /**
     * {@code POST  /relationships} : Create a new relationship.
     *
     * @param relationshipDTO the relationshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relationshipDTO, or with status {@code 400 (Bad Request)} if the relationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relationships")
    public ResponseEntity<RelationshipDTO> createRelationship(@RequestBody RelationshipDTO relationshipDTO) throws URISyntaxException {
        log.debug("REST request to save Relationship : {}", relationshipDTO);
        if (relationshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new relationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelationshipDTO result = relationshipService.save(relationshipDTO);
        return ResponseEntity.created(new URI("/api/relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relationships} : Updates an existing relationship.
     *
     * @param relationshipDTO the relationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relationshipDTO,
     * or with status {@code 400 (Bad Request)} if the relationshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relationships")
    public ResponseEntity<RelationshipDTO> updateRelationship(@RequestBody RelationshipDTO relationshipDTO) throws URISyntaxException {
        log.debug("REST request to update Relationship : {}", relationshipDTO);
        if (relationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RelationshipDTO result = relationshipService.save(relationshipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, relationshipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /relationships} : get all the relationships.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relationships in body.
     */
    @GetMapping("/relationships")
    public List<RelationshipDTO> getAllRelationships() {
        log.debug("REST request to get all Relationships");
        return relationshipService.findAll();
    }

    /**
     * {@code GET  /relationships/:id} : get the "id" relationship.
     *
     * @param id the id of the relationshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relationshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relationships/{id}")
    public ResponseEntity<RelationshipDTO> getRelationship(@PathVariable Long id) {
        log.debug("REST request to get Relationship : {}", id);
        Optional<RelationshipDTO> relationshipDTO = relationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relationshipDTO);
    }

    /**
     * {@code DELETE  /relationships/:id} : delete the "id" relationship.
     *
     * @param id the id of the relationshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        log.debug("REST request to delete Relationship : {}", id);
        relationshipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
