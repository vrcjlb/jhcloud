package com.vlarco.jhcloud.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyRelationshipDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyRelationshipDTO.class);
        PartyRelationshipDTO partyRelationshipDTO1 = new PartyRelationshipDTO();
        partyRelationshipDTO1.setId(1L);
        PartyRelationshipDTO partyRelationshipDTO2 = new PartyRelationshipDTO();
        assertThat(partyRelationshipDTO1).isNotEqualTo(partyRelationshipDTO2);
        partyRelationshipDTO2.setId(partyRelationshipDTO1.getId());
        assertThat(partyRelationshipDTO1).isEqualTo(partyRelationshipDTO2);
        partyRelationshipDTO2.setId(2L);
        assertThat(partyRelationshipDTO1).isNotEqualTo(partyRelationshipDTO2);
        partyRelationshipDTO1.setId(null);
        assertThat(partyRelationshipDTO1).isNotEqualTo(partyRelationshipDTO2);
    }
}
