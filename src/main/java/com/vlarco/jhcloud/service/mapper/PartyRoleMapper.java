package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.PartyRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartyRole} and its DTO {@link PartyRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class, PartyMapper.class})
public interface PartyRoleMapper extends EntityMapper<PartyRoleDTO, PartyRole> {

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "party.id", target = "partyId")
    PartyRoleDTO toDto(PartyRole partyRole);

    @Mapping(source = "roleId", target = "role")
    @Mapping(source = "partyId", target = "party")
    PartyRole toEntity(PartyRoleDTO partyRoleDTO);

    default PartyRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartyRole partyRole = new PartyRole();
        partyRole.setId(id);
        return partyRole;
    }
}
