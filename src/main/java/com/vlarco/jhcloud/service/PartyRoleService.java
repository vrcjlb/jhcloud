package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.PartyRoleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.PartyRole}.
 */
public interface PartyRoleService {

    /**
     * Save a partyRole.
     *
     * @param partyRoleDTO the entity to save.
     * @return the persisted entity.
     */
    PartyRoleDTO save(PartyRoleDTO partyRoleDTO);

    /**
     * Get all the partyRoles.
     *
     * @return the list of entities.
     */
    List<PartyRoleDTO> findAll();


    /**
     * Get the "id" partyRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartyRoleDTO> findOne(Long id);

    /**
     * Delete the "id" partyRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
