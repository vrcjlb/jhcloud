package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.PartyGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.PartyGroup}.
 */
public interface PartyGroupService {

    /**
     * Save a partyGroup.
     *
     * @param partyGroupDTO the entity to save.
     * @return the persisted entity.
     */
    PartyGroupDTO save(PartyGroupDTO partyGroupDTO);

    /**
     * Get all the partyGroups.
     *
     * @return the list of entities.
     */
    List<PartyGroupDTO> findAll();


    /**
     * Get the "id" partyGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartyGroupDTO> findOne(Long id);

    /**
     * Delete the "id" partyGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
