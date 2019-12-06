package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.PartyRelationshipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartyRelationship} and its DTO {@link PartyRelationshipDTO}.
 */
@Mapper(componentModel = "spring", uses = {RelationshipMapper.class, PartyMapper.class})
public interface PartyRelationshipMapper extends EntityMapper<PartyRelationshipDTO, PartyRelationship> {

    @Mapping(source = "relationship.id", target = "relationshipId")
    @Mapping(source = "fromPartyId.id", target = "fromPartyIdId")
    @Mapping(source = "toPartyId.id", target = "toPartyIdId")
    PartyRelationshipDTO toDto(PartyRelationship partyRelationship);

    @Mapping(source = "relationshipId", target = "relationship")
    @Mapping(source = "fromPartyIdId", target = "fromPartyId")
    @Mapping(source = "toPartyIdId", target = "toPartyId")
    PartyRelationship toEntity(PartyRelationshipDTO partyRelationshipDTO);

    default PartyRelationship fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartyRelationship partyRelationship = new PartyRelationship();
        partyRelationship.setId(id);
        return partyRelationship;
    }
}
