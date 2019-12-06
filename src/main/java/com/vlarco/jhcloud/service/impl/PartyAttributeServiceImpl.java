package com.vlarco.jhcloud.service.impl;

import com.vlarco.jhcloud.service.PartyAttributeService;
import com.vlarco.jhcloud.domain.PartyAttribute;
import com.vlarco.jhcloud.repository.PartyAttributeRepository;
import com.vlarco.jhcloud.service.dto.PartyAttributeDTO;
import com.vlarco.jhcloud.service.mapper.PartyAttributeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartyAttribute}.
 */
@Service
@Transactional
public class PartyAttributeServiceImpl implements PartyAttributeService {

    private final Logger log = LoggerFactory.getLogger(PartyAttributeServiceImpl.class);

    private final PartyAttributeRepository partyAttributeRepository;

    private final PartyAttributeMapper partyAttributeMapper;

    public PartyAttributeServiceImpl(PartyAttributeRepository partyAttributeRepository, PartyAttributeMapper partyAttributeMapper) {
        this.partyAttributeRepository = partyAttributeRepository;
        this.partyAttributeMapper = partyAttributeMapper;
    }

    /**
     * Save a partyAttribute.
     *
     * @param partyAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartyAttributeDTO save(PartyAttributeDTO partyAttributeDTO) {
        log.debug("Request to save PartyAttribute : {}", partyAttributeDTO);
        PartyAttribute partyAttribute = partyAttributeMapper.toEntity(partyAttributeDTO);
        partyAttribute = partyAttributeRepository.save(partyAttribute);
        return partyAttributeMapper.toDto(partyAttribute);
    }

    /**
     * Get all the partyAttributes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyAttributeDTO> findAll() {
        log.debug("Request to get all PartyAttributes");
        return partyAttributeRepository.findAll().stream()
            .map(partyAttributeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partyAttribute by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyAttributeDTO> findOne(Long id) {
        log.debug("Request to get PartyAttribute : {}", id);
        return partyAttributeRepository.findById(id)
            .map(partyAttributeMapper::toDto);
    }

    /**
     * Delete the partyAttribute by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyAttribute : {}", id);
        partyAttributeRepository.deleteById(id);
    }
}
