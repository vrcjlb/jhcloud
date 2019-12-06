package com.vlarco.jhcloud.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyDTO.class);
        PartyDTO partyDTO1 = new PartyDTO();
        partyDTO1.setId(1L);
        PartyDTO partyDTO2 = new PartyDTO();
        assertThat(partyDTO1).isNotEqualTo(partyDTO2);
        partyDTO2.setId(partyDTO1.getId());
        assertThat(partyDTO1).isEqualTo(partyDTO2);
        partyDTO2.setId(2L);
        assertThat(partyDTO1).isNotEqualTo(partyDTO2);
        partyDTO1.setId(null);
        assertThat(partyDTO1).isNotEqualTo(partyDTO2);
    }
}
