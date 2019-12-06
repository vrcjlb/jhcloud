package com.vlarco.jhcloud.service;

import com.vlarco.jhcloud.service.dto.PersonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vlarco.jhcloud.domain.Person}.
 */
public interface PersonService {

    /**
     * Save a person.
     *
     * @param personDTO the entity to save.
     * @return the persisted entity.
     */
    PersonDTO save(PersonDTO personDTO);

    /**
     * Get all the people.
     *
     * @return the list of entities.
     */
    List<PersonDTO> findAll();


    /**
     * Get the "id" person.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonDTO> findOne(Long id);

    /**
     * Delete the "id" person.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
