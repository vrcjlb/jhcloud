package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.PartyAttributeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.PartyAttribute}.
 */
public interface PartyAttributeService {

    /**
     * Save a partyAttribute.
     *
     * @param partyAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    PartyAttributeDTO save(PartyAttributeDTO partyAttributeDTO);

    /**
     * Get all the partyAttributes.
     *
     * @return the list of entities.
     */
    List<PartyAttributeDTO> findAll();


    /**
     * Get the "id" partyAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartyAttributeDTO> findOne(Long id);

    /**
     * Delete the "id" partyAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
