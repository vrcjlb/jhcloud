package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.PartyAttributeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartyAttribute} and its DTO {@link PartyAttributeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartyTypeAttributeMapper.class, PartyMapper.class})
public interface PartyAttributeMapper extends EntityMapper<PartyAttributeDTO, PartyAttribute> {

    @Mapping(source = "partyTypeAttribute.id", target = "partyTypeAttributeId")
    @Mapping(source = "party.id", target = "partyId")
    PartyAttributeDTO toDto(PartyAttribute partyAttribute);

    @Mapping(source = "partyTypeAttributeId", target = "partyTypeAttribute")
    @Mapping(source = "partyId", target = "party")
    PartyAttribute toEntity(PartyAttributeDTO partyAttributeDTO);

    default PartyAttribute fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartyAttribute partyAttribute = new PartyAttribute();
        partyAttribute.setId(id);
        return partyAttribute;
    }
}
