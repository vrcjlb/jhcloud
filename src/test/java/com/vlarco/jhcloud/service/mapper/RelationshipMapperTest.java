package com.vlarco.jhcloud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RelationshipMapperTest {

    private RelationshipMapper relationshipMapper;

    @BeforeEach
    public void setUp() {
        relationshipMapper = new RelationshipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(relationshipMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(relationshipMapper.fromId(null)).isNull();
    }
}
