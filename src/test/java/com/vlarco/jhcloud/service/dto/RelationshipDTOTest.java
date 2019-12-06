package com.vlarco.jhcloud.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class RelationshipDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipDTO.class);
        RelationshipDTO relationshipDTO1 = new RelationshipDTO();
        relationshipDTO1.setId(1L);
        RelationshipDTO relationshipDTO2 = new RelationshipDTO();
        assertThat(relationshipDTO1).isNotEqualTo(relationshipDTO2);
        relationshipDTO2.setId(relationshipDTO1.getId());
        assertThat(relationshipDTO1).isEqualTo(relationshipDTO2);
        relationshipDTO2.setId(2L);
        assertThat(relationshipDTO1).isNotEqualTo(relationshipDTO2);
        relationshipDTO1.setId(null);
        assertThat(relationshipDTO1).isNotEqualTo(relationshipDTO2);
    }
}
