package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.PartyTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartyType} and its DTO {@link PartyTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PartyTypeMapper extends EntityMapper<PartyTypeDTO, PartyType> {

    @Mapping(source = "parentId.id", target = "parentIdId")
    PartyTypeDTO toDto(PartyType partyType);

    @Mapping(target = "partyTypeAttributes", ignore = true)
    @Mapping(target = "removePartyTypeAttribute", ignore = true)
    @Mapping(target = "parties", ignore = true)
    @Mapping(target = "removeParty", ignore = true)
    @Mapping(source = "parentIdId", target = "parentId")
    PartyType toEntity(PartyTypeDTO partyTypeDTO);

    default PartyType fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartyType partyType = new PartyType();
        partyType.setId(id);
        return partyType;
    }
}
