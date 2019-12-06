package com.vlarco.jhcloud.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyGroupDTO.class);
        PartyGroupDTO partyGroupDTO1 = new PartyGroupDTO();
        partyGroupDTO1.setId(1L);
        PartyGroupDTO partyGroupDTO2 = new PartyGroupDTO();
        assertThat(partyGroupDTO1).isNotEqualTo(partyGroupDTO2);
        partyGroupDTO2.setId(partyGroupDTO1.getId());
        assertThat(partyGroupDTO1).isEqualTo(partyGroupDTO2);
        partyGroupDTO2.setId(2L);
        assertThat(partyGroupDTO1).isNotEqualTo(partyGroupDTO2);
        partyGroupDTO1.setId(null);
        assertThat(partyGroupDTO1).isNotEqualTo(partyGroupDTO2);
    }
}
