package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.PartyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Party} and its DTO {@link PartyDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartyTypeMapper.class})
public interface PartyMapper extends EntityMapper<PartyDTO, Party> {

    @Mapping(source = "partyType.id", target = "partyTypeId")
    PartyDTO toDto(Party party);

    @Mapping(target = "partyRoles", ignore = true)
    @Mapping(target = "removePartyRole", ignore = true)
    @Mapping(target = "partyAttributes", ignore = true)
    @Mapping(target = "removePartyAttribute", ignore = true)
    @Mapping(target = "fromPartyIds", ignore = true)
    @Mapping(target = "removeFromPartyId", ignore = true)
    @Mapping(target = "toPartyIds", ignore = true)
    @Mapping(target = "removeToPartyId", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "partyGroup", ignore = true)
    @Mapping(source = "partyTypeId", target = "partyType")
    Party toEntity(PartyDTO partyDTO);

    default Party fromId(Long id) {
        if (id == null) {
            return null;
        }
        Party party = new Party();
        party.setId(id);
        return party;
    }
}
