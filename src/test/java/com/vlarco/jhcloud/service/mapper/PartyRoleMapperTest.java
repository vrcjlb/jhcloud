package com.vlarco.jhcloud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PartyRoleMapperTest {

    private PartyRoleMapper partyRoleMapper;

    @BeforeEach
    public void setUp() {
        partyRoleMapper = new PartyRoleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(partyRoleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partyRoleMapper.fromId(null)).isNull();
    }
}
