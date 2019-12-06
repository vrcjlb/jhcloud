package com.vlarco.jhcloud.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyTypeAttributeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyTypeAttributeDTO.class);
        PartyTypeAttributeDTO partyTypeAttributeDTO1 = new PartyTypeAttributeDTO();
        partyTypeAttributeDTO1.setId(1L);
        PartyTypeAttributeDTO partyTypeAttributeDTO2 = new PartyTypeAttributeDTO();
        assertThat(partyTypeAttributeDTO1).isNotEqualTo(partyTypeAttributeDTO2);
        partyTypeAttributeDTO2.setId(partyTypeAttributeDTO1.getId());
        assertThat(partyTypeAttributeDTO1).isEqualTo(partyTypeAttributeDTO2);
        partyTypeAttributeDTO2.setId(2L);
        assertThat(partyTypeAttributeDTO1).isNotEqualTo(partyTypeAttributeDTO2);
        partyTypeAttributeDTO1.setId(null);
        assertThat(partyTypeAttributeDTO1).isNotEqualTo(partyTypeAttributeDTO2);
    }
}
