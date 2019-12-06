package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.PartyRelationshipDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.PartyRelationship}.
 */
public interface PartyRelationshipService {

    /**
     * Save a partyRelationship.
     *
     * @param partyRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    PartyRelationshipDTO save(PartyRelationshipDTO partyRelationshipDTO);

    /**
     * Get all the partyRelationships.
     *
     * @return the list of entities.
     */
    List<PartyRelationshipDTO> findAll();


    /**
     * Get the "id" partyRelationship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartyRelationshipDTO> findOne(Long id);

    /**
     * Delete the "id" partyRelationship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
