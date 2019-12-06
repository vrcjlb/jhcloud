package com.vlarco.jhcloud.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyTypeDTO.class);
        PartyTypeDTO partyTypeDTO1 = new PartyTypeDTO();
        partyTypeDTO1.setId(1L);
        PartyTypeDTO partyTypeDTO2 = new PartyTypeDTO();
        assertThat(partyTypeDTO1).isNotEqualTo(partyTypeDTO2);
        partyTypeDTO2.setId(partyTypeDTO1.getId());
        assertThat(partyTypeDTO1).isEqualTo(partyTypeDTO2);
        partyTypeDTO2.setId(2L);
        assertThat(partyTypeDTO1).isNotEqualTo(partyTypeDTO2);
        partyTypeDTO1.setId(null);
        assertThat(partyTypeDTO1).isNotEqualTo(partyTypeDTO2);
    }
}
