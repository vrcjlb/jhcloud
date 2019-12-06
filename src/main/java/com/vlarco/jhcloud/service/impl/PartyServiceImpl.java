package com.vlarco.jhcloud.service.impl;

import com.vlarco.jhcloud.service.PartyService;
import com.vlarco.jhcloud.domain.Party;
import com.vlarco.jhcloud.repository.PartyRepository;
import com.vlarco.jhcloud.service.dto.PartyDTO;
import com.vlarco.jhcloud.service.mapper.PartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Party}.
 */
@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    private final Logger log = LoggerFactory.getLogger(PartyServiceImpl.class);

    private final PartyRepository partyRepository;

    private final PartyMapper partyMapper;

    public PartyServiceImpl(PartyRepository partyRepository, PartyMapper partyMapper) {
        this.partyRepository = partyRepository;
        this.partyMapper = partyMapper;
    }

    /**
     * Save a party.
     *
     * @param partyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartyDTO save(PartyDTO partyDTO) {
        log.debug("Request to save Party : {}", partyDTO);
        Party party = partyMapper.toEntity(partyDTO);
        party = partyRepository.save(party);
        return partyMapper.toDto(party);
    }

    /**
     * Get all the parties.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyDTO> findAll() {
        log.debug("Request to get all Parties");
        return partyRepository.findAll().stream()
            .map(partyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the parties where Person is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PartyDTO> findAllWherePersonIsNull() {
        log.debug("Request to get all parties where Person is null");
        return StreamSupport
            .stream(partyRepository.findAll().spliterator(), false)
            .filter(party -> party.getPerson() == null)
            .map(partyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the parties where PartyGroup is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PartyDTO> findAllWherePartyGroupIsNull() {
        log.debug("Request to get all parties where PartyGroup is null");
        return StreamSupport
            .stream(partyRepository.findAll().spliterator(), false)
            .filter(party -> party.getPartyGroup() == null)
            .map(partyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one party by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyDTO> findOne(Long id) {
        log.debug("Request to get Party : {}", id);
        return partyRepository.findById(id)
            .map(partyMapper::toDto);
    }

    /**
     * Delete the party by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Party : {}", id);
        partyRepository.deleteById(id);
    }
}
