package com.vlarco.jhcloud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PartyMapperTest {

    private PartyMapper partyMapper;

    @BeforeEach
    public void setUp() {
        partyMapper = new PartyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(partyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partyMapper.fromId(null)).isNull();
    }
}
