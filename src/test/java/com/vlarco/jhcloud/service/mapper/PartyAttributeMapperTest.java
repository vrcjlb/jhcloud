package com.vlarco.jhcloud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PartyAttributeMapperTest {

    private PartyAttributeMapper partyAttributeMapper;

    @BeforeEach
    public void setUp() {
        partyAttributeMapper = new PartyAttributeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(partyAttributeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partyAttributeMapper.fromId(null)).isNull();
    }
}
