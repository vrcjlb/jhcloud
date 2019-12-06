package com.vlarco.jhcloud.service.impl;

import com.vlarco.jhcloud.service.PartyGroupService;
import com.vlarco.jhcloud.domain.PartyGroup;
import com.vlarco.jhcloud.repository.PartyGroupRepository;
import com.vlarco.jhcloud.service.dto.PartyGroupDTO;
import com.vlarco.jhcloud.service.mapper.PartyGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartyGroup}.
 */
@Service
@Transactional
public class PartyGroupServiceImpl implements PartyGroupService {

    private final Logger log = LoggerFactory.getLogger(PartyGroupServiceImpl.class);

    private final PartyGroupRepository partyGroupRepository;

    private final PartyGroupMapper partyGroupMapper;

    public PartyGroupServiceImpl(PartyGroupRepository partyGroupRepository, PartyGroupMapper partyGroupMapper) {
        this.partyGroupRepository = partyGroupRepository;
        this.partyGroupMapper = partyGroupMapper;
    }

    /**
     * Save a partyGroup.
     *
     * @param partyGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartyGroupDTO save(PartyGroupDTO partyGroupDTO) {
        log.debug("Request to save PartyGroup : {}", partyGroupDTO);
        PartyGroup partyGroup = partyGroupMapper.toEntity(partyGroupDTO);
        partyGroup = partyGroupRepository.save(partyGroup);
        return partyGroupMapper.toDto(partyGroup);
    }

    /**
     * Get all the partyGroups.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyGroupDTO> findAll() {
        log.debug("Request to get all PartyGroups");
        return partyGroupRepository.findAll().stream()
            .map(partyGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partyGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyGroupDTO> findOne(Long id) {
        log.debug("Request to get PartyGroup : {}", id);
        return partyGroupRepository.findById(id)
            .map(partyGroupMapper::toDto);
    }

    /**
     * Delete the partyGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyGroup : {}", id);
        partyGroupRepository.deleteById(id);
    }
}
