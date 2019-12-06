package com.vlarco.jhcloud.service.impl;

import com.vlarco.jhcloud.service.RelationshipService;
import com.vlarco.jhcloud.domain.Relationship;
import com.vlarco.jhcloud.repository.RelationshipRepository;
import com.vlarco.jhcloud.service.dto.RelationshipDTO;
import com.vlarco.jhcloud.service.mapper.RelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Relationship}.
 */
@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService {

    private final Logger log = LoggerFactory.getLogger(RelationshipServiceImpl.class);

    private final RelationshipRepository relationshipRepository;

    private final RelationshipMapper relationshipMapper;

    public RelationshipServiceImpl(RelationshipRepository relationshipRepository, RelationshipMapper relationshipMapper) {
        this.relationshipRepository = relationshipRepository;
        this.relationshipMapper = relationshipMapper;
    }

    /**
     * Save a relationship.
     *
     * @param relationshipDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RelationshipDTO save(RelationshipDTO relationshipDTO) {
        log.debug("Request to save Relationship : {}", relationshipDTO);
        Relationship relationship = relationshipMapper.toEntity(relationshipDTO);
        relationship = relationshipRepository.save(relationship);
        return relationshipMapper.toDto(relationship);
    }

    /**
     * Get all the relationships.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RelationshipDTO> findAll() {
        log.debug("Request to get all Relationships");
        return relationshipRepository.findAll().stream()
            .map(relationshipMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one relationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RelationshipDTO> findOne(Long id) {
        log.debug("Request to get Relationship : {}", id);
        return relationshipRepository.findById(id)
            .map(relationshipMapper::toDto);
    }

    /**
     * Delete the relationship by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Relationship : {}", id);
        relationshipRepository.deleteById(id);
    }
}
