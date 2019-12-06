package com.vlarco.jhcloud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PartyRelationshipMapperTest {

    private PartyRelationshipMapper partyRelationshipMapper;

    @BeforeEach
    public void setUp() {
        partyRelationshipMapper = new PartyRelationshipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(partyRelationshipMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partyRelationshipMapper.fromId(null)).isNull();
    }
}
