package com.vlarco.jhcloud.service.mapper;

import com.vlarco.jhcloud.domain.*;
import com.vlarco.jhcloud.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartyMapper.class})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    @Mapping(source = "party.id", target = "partyId")
    PersonDTO toDto(Person person);

    @Mapping(source = "partyId", target = "party")
    Person toEntity(PersonDTO personDTO);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
