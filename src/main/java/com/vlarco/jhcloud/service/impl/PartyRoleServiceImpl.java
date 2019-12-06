package com.vlarco.jhcloud.service.impl;

import com.vlarco.jhcloud.service.PartyRoleService;
import com.vlarco.jhcloud.domain.PartyRole;
import com.vlarco.jhcloud.repository.PartyRoleRepository;
import com.vlarco.jhcloud.service.dto.PartyRoleDTO;
import com.vlarco.jhcloud.service.mapper.PartyRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartyRole}.
 */
@Service
@Transactional
public class PartyRoleServiceImpl implements PartyRoleService {

    private final Logger log = LoggerFactory.getLogger(PartyRoleServiceImpl.class);

    private final PartyRoleRepository partyRoleRepository;

    private final PartyRoleMapper partyRoleMapper;

    public PartyRoleServiceImpl(PartyRoleRepository partyRoleRepository, PartyRoleMapper partyRoleMapper) {
        this.partyRoleRepository = partyRoleRepository;
        this.partyRoleMapper = partyRoleMapper;
    }

    /**
     * Save a partyRole.
     *
     * @param partyRoleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartyRoleDTO save(PartyRoleDTO partyRoleDTO) {
        log.debug("Request to save PartyRole : {}", partyRoleDTO);
        PartyRole partyRole = partyRoleMapper.toEntity(partyRoleDTO);
        partyRole = partyRoleRepository.save(partyRole);
        return partyRoleMapper.toDto(partyRole);
    }

    /**
     * Get all the partyRoles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyRoleDTO> findAll() {
        log.debug("Request to get all PartyRoles");
        return partyRoleRepository.findAll().stream()
            .map(partyRoleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partyRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyRoleDTO> findOne(Long id) {
        log.debug("Request to get PartyRole : {}", id);
        return partyRoleRepository.findById(id)
            .map(partyRoleMapper::toDto);
    }

    /**
     * Delete the partyRole by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyRole : {}", id);
        partyRoleRepository.deleteById(id);
    }
}
