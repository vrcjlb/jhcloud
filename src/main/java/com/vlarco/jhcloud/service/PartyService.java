package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.PartyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.Party}.
 */
public interface PartyService {

    /**
     * Save a party.
     *
     * @param partyDTO the entity to save.
     * @return the persisted entity.
     */
    PartyDTO save(PartyDTO partyDTO);

    /**
     * Get all the parties.
     *
     * @return the list of entities.
     */
    List<PartyDTO> findAll();
    /**
     * Get all the PartyDTO where Person is {@code null}.
     *
     * @return the list of entities.
     */
    List<PartyDTO> findAllWherePersonIsNull();
    /**
     * Get all the PartyDTO where PartyGroup is {@code null}.
     *
     * @return the list of entities.
     */
    List<PartyDTO> findAllWherePartyGroupIsNull();


    /**
     * Get the "id" party.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartyDTO> findOne(Long id);

    /**
     * Delete the "id" party.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
