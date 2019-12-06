package com.vlarco.jhcloud.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyAttributeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyAttributeDTO.class);
        PartyAttributeDTO partyAttributeDTO1 = new PartyAttributeDTO();
        partyAttributeDTO1.setId(1L);
        PartyAttributeDTO partyAttributeDTO2 = new PartyAttributeDTO();
        assertThat(partyAttributeDTO1).isNotEqualTo(partyAttributeDTO2);
        partyAttributeDTO2.setId(partyAttributeDTO1.getId());
        assertThat(partyAttributeDTO1).isEqualTo(partyAttributeDTO2);
        partyAttributeDTO2.setId(2L);
        assertThat(partyAttributeDTO1).isNotEqualTo(partyAttributeDTO2);
        partyAttributeDTO1.setId(null);
        assertThat(partyAttributeDTO1).isNotEqualTo(partyAttributeDTO2);
    }
}
