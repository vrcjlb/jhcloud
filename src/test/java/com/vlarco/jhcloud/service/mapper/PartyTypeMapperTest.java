package com.vlarco.jhcloud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PartyTypeMapperTest {

    private PartyTypeMapper partyTypeMapper;

    @BeforeEach
    public void setUp() {
        partyTypeMapper = new PartyTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(partyTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partyTypeMapper.fromId(null)).isNull();
    }
}
