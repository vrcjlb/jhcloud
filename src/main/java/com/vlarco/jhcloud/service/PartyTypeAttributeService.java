package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.PartyTypeAttributeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.PartyTypeAttribute}.
 */
public interface PartyTypeAttributeService {

    /**
     * Save a partyTypeAttribute.
     *
     * @param partyTypeAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    PartyTypeAttributeDTO save(PartyTypeAttributeDTO partyTypeAttributeDTO);

    /**
     * Get all the partyTypeAttributes.
     *
     * @return the list of entities.
     */
    List<PartyTypeAttributeDTO> findAll();


    /**
     * Get the "id" partyTypeAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartyTypeAttributeDTO> findOne(Long id);

    /**
     * Delete the "id" partyTypeAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
