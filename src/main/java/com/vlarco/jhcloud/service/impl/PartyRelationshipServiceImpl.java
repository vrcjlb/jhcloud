package com.vlarco.jhcloud.service.impl;

import com.vlarco.jhcloud.service.PartyRelationshipService;
import com.vlarco.jhcloud.domain.PartyRelationship;
import com.vlarco.jhcloud.repository.PartyRelationshipRepository;
import com.vlarco.jhcloud.service.dto.PartyRelationshipDTO;
import com.vlarco.jhcloud.service.mapper.PartyRelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartyRelationship}.
 */
@Service
@Transactional
public class PartyRelationshipServiceImpl implements PartyRelationshipService {

    private final Logger log = LoggerFactory.getLogger(PartyRelationshipServiceImpl.class);

    private final PartyRelationshipRepository partyRelationshipRepository;

    private final PartyRelationshipMapper partyRelationshipMapper;

    public PartyRelationshipServiceImpl(PartyRelationshipRepository partyRelationshipRepository, PartyRelationshipMapper partyRelationshipMapper) {
        this.partyRelationshipRepository = partyRelationshipRepository;
        this.partyRelationshipMapper = partyRelationshipMapper;
    }

    /**
     * Save a partyRelationship.
     *
     * @param partyRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartyRelationshipDTO save(PartyRelationshipDTO partyRelationshipDTO) {
        log.debug("Request to save PartyRelationship : {}", partyRelationshipDTO);
        PartyRelationship partyRelationship = partyRelationshipMapper.toEntity(partyRelationshipDTO);
        partyRelationship = partyRelationshipRepository.save(partyRelationship);
        return partyRelationshipMapper.toDto(partyRelationship);
    }

    /**
     * Get all the partyRelationships.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyRelationshipDTO> findAll() {
        log.debug("Request to get all PartyRelationships");
        return partyRelationshipRepository.findAll().stream()
            .map(partyRelationshipMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partyRelationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyRelationshipDTO> findOne(Long id) {
        log.debug("Request to get PartyRelationship : {}", id);
        return partyRelationshipRepository.findById(id)
            .map(partyRelationshipMapper::toDto);
    }

    /**
     * Delete the partyRelationship by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyRelationship : {}", id);
        partyRelationshipRepository.deleteById(id);
    }
}
