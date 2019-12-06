package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.PartyGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartyGroup} and its DTO {@link PartyGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartyMapper.class})
public interface PartyGroupMapper extends EntityMapper<PartyGroupDTO, PartyGroup> {

    @Mapping(source = "party.id", target = "partyId")
    PartyGroupDTO toDto(PartyGroup partyGroup);

    @Mapping(source = "partyId", target = "party")
    PartyGroup toEntity(PartyGroupDTO partyGroupDTO);

    default PartyGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartyGroup partyGroup = new PartyGroup();
        partyGroup.setId(id);
        return partyGroup;
    }
}
