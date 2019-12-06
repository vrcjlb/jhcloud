package com.vlarco.jhcloud.service.impl;

import com.vlarco.jhcloud.service.PartyTypeAttributeService;
import com.vlarco.jhcloud.domain.PartyTypeAttribute;
import com.vlarco.jhcloud.repository.PartyTypeAttributeRepository;
import com.vlarco.jhcloud.service.dto.PartyTypeAttributeDTO;
import com.vlarco.jhcloud.service.mapper.PartyTypeAttributeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartyTypeAttribute}.
 */
@Service
@Transactional
public class PartyTypeAttributeServiceImpl implements PartyTypeAttributeService {

    private final Logger log = LoggerFactory.getLogger(PartyTypeAttributeServiceImpl.class);

    private final PartyTypeAttributeRepository partyTypeAttributeRepository;

    private final PartyTypeAttributeMapper partyTypeAttributeMapper;

    public PartyTypeAttributeServiceImpl(PartyTypeAttributeRepository partyTypeAttributeRepository, PartyTypeAttributeMapper partyTypeAttributeMapper) {
        this.partyTypeAttributeRepository = partyTypeAttributeRepository;
        this.partyTypeAttributeMapper = partyTypeAttributeMapper;
    }

    /**
     * Save a partyTypeAttribute.
     *
     * @param partyTypeAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartyTypeAttributeDTO save(PartyTypeAttributeDTO partyTypeAttributeDTO) {
        log.debug("Request to save PartyTypeAttribute : {}", partyTypeAttributeDTO);
        PartyTypeAttribute partyTypeAttribute = partyTypeAttributeMapper.toEntity(partyTypeAttributeDTO);
        partyTypeAttribute = partyTypeAttributeRepository.save(partyTypeAttribute);
        return partyTypeAttributeMapper.toDto(partyTypeAttribute);
    }

    /**
     * Get all the partyTypeAttributes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyTypeAttributeDTO> findAll() {
        log.debug("Request to get all PartyTypeAttributes");
        return partyTypeAttributeRepository.findAll().stream()
            .map(partyTypeAttributeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partyTypeAttribute by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyTypeAttributeDTO> findOne(Long id) {
        log.debug("Request to get PartyTypeAttribute : {}", id);
        return partyTypeAttributeRepository.findById(id)
            .map(partyTypeAttributeMapper::toDto);
    }

    /**
     * Delete the partyTypeAttribute by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyTypeAttribute : {}", id);
        partyTypeAttributeRepository.deleteById(id);
    }
}
