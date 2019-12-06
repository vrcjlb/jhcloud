package com.vlarco.jhcloud.service.impl;

import com.vlarco.jhcloud.service.PartyTypeService;
import com.vlarco.jhcloud.domain.PartyType;
import com.vlarco.jhcloud.repository.PartyTypeRepository;
import com.vlarco.jhcloud.service.dto.PartyTypeDTO;
import com.vlarco.jhcloud.service.mapper.PartyTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartyType}.
 */
@Service
@Transactional
public class PartyTypeServiceImpl implements PartyTypeService {

    private final Logger log = LoggerFactory.getLogger(PartyTypeServiceImpl.class);

    private final PartyTypeRepository partyTypeRepository;

    private final PartyTypeMapper partyTypeMapper;

    public PartyTypeServiceImpl(PartyTypeRepository partyTypeRepository, PartyTypeMapper partyTypeMapper) {
        this.partyTypeRepository = partyTypeRepository;
        this.partyTypeMapper = partyTypeMapper;
    }

    /**
     * Save a partyType.
     *
     * @param partyTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartyTypeDTO save(PartyTypeDTO partyTypeDTO) {
        log.debug("Request to save PartyType : {}", partyTypeDTO);
        PartyType partyType = partyTypeMapper.toEntity(partyTypeDTO);
        partyType = partyTypeRepository.save(partyType);
        return partyTypeMapper.toDto(partyType);
    }

    /**
     * Get all the partyTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyTypeDTO> findAll() {
        log.debug("Request to get all PartyTypes");
        return partyTypeRepository.findAll().stream()
            .map(partyTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partyType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyTypeDTO> findOne(Long id) {
        log.debug("Request to get PartyType : {}", id);
        return partyTypeRepository.findById(id)
            .map(partyTypeMapper::toDto);
    }

    /**
     * Delete the partyType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyType : {}", id);
        partyTypeRepository.deleteById(id);
    }
}
