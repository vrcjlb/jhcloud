package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.PartyTypeAttributeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartyTypeAttribute} and its DTO {@link PartyTypeAttributeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartyTypeMapper.class})
public interface PartyTypeAttributeMapper extends EntityMapper<PartyTypeAttributeDTO, PartyTypeAttribute> {

    @Mapping(source = "partyType.id", target = "partyTypeId")
    PartyTypeAttributeDTO toDto(PartyTypeAttribute partyTypeAttribute);

    @Mapping(target = "partyAttributes", ignore = true)
    @Mapping(target = "removePartyAttribute", ignore = true)
    @Mapping(source = "partyTypeId", target = "partyType")
    PartyTypeAttribute toEntity(PartyTypeAttributeDTO partyTypeAttributeDTO);

    default PartyTypeAttribute fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartyTypeAttribute partyTypeAttribute = new PartyTypeAttribute();
        partyTypeAttribute.setId(id);
        return partyTypeAttribute;
    }
}
