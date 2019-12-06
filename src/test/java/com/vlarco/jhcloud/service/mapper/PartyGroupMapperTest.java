package com.vlarco.jhcloud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PartyGroupMapperTest {

    private PartyGroupMapper partyGroupMapper;

    @BeforeEach
    public void setUp() {
        partyGroupMapper = new PartyGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(partyGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partyGroupMapper.fromId(null)).isNull();
    }
}
