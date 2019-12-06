package com.vlarco.jhcloud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PartyTypeAttributeMapperTest {

    private PartyTypeAttributeMapper partyTypeAttributeMapper;

    @BeforeEach
    public void setUp() {
        partyTypeAttributeMapper = new PartyTypeAttributeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(partyTypeAttributeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partyTypeAttributeMapper.fromId(null)).isNull();
    }
}
