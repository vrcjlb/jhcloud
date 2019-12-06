package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.RelationshipDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.Relationship}.
 */
public interface RelationshipService {

    /**
     * Save a relationship.
     *
     * @param relationshipDTO the entity to save.
     * @return the persisted entity.
     */
    RelationshipDTO save(RelationshipDTO relationshipDTO);

    /**
     * Get all the relationships.
     *
     * @return the list of entities.
     */
    List<RelationshipDTO> findAll();


    /**
     * Get the "id" relationship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RelationshipDTO> findOne(Long id);

    /**
     * Delete the "id" relationship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
