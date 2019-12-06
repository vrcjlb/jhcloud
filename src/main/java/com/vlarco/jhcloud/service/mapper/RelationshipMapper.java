package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.RelationshipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Relationship} and its DTO {@link RelationshipDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RelationshipMapper extends EntityMapper<RelationshipDTO, Relationship> {

    @Mapping(source = "parentId.id", target = "parentIdId")
    RelationshipDTO toDto(Relationship relationship);

    @Mapping(target = "partyRelationships", ignore = true)
    @Mapping(target = "removePartyRelationship", ignore = true)
    @Mapping(source = "parentIdId", target = "parentId")
    Relationship toEntity(RelationshipDTO relationshipDTO);

    default Relationship fromId(Long id) {
        if (id == null) {
            return null;
        }
        Relationship relationship = new Relationship();
        relationship.setId(id);
        return relationship;
    }
}
