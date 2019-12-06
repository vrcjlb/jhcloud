package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.PartyTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.PartyType}.
 */
public interface PartyTypeService {

    /**
     * Save a partyType.
     *
     * @param partyTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PartyTypeDTO save(PartyTypeDTO partyTypeDTO);

    /**
     * Get all the partyTypes.
     *
     * @return the list of entities.
     */
    List<PartyTypeDTO> findAll();


    /**
     * Get the "id" partyType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartyTypeDTO> findOne(Long id);

    /**
     * Delete the "id" partyType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
