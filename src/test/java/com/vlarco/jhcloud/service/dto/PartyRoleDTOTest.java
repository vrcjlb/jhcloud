package com.vlarco.jhcloud.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyRoleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyRoleDTO.class);
        PartyRoleDTO partyRoleDTO1 = new PartyRoleDTO();
        partyRoleDTO1.setId(1L);
        PartyRoleDTO partyRoleDTO2 = new PartyRoleDTO();
        assertThat(partyRoleDTO1).isNotEqualTo(partyRoleDTO2);
        partyRoleDTO2.setId(partyRoleDTO1.getId());
        assertThat(partyRoleDTO1).isEqualTo(partyRoleDTO2);
        partyRoleDTO2.setId(2L);
        assertThat(partyRoleDTO1).isNotEqualTo(partyRoleDTO2);
        partyRoleDTO1.setId(null);
        assertThat(partyRoleDTO1).isNotEqualTo(partyRoleDTO2);
    }
}
