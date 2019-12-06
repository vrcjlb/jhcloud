package com.vlarco.jhcloud.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyGroup.class);
        PartyGroup partyGroup1 = new PartyGroup();
        partyGroup1.setId(1L);
        PartyGroup partyGroup2 = new PartyGroup();
        partyGroup2.setId(partyGroup1.getId());
        assertThat(partyGroup1).isEqualTo(partyGroup2);
        partyGroup2.setId(2L);
        assertThat(partyGroup1).isNotEqualTo(partyGroup2);
        partyGroup1.setId(null);
        assertThat(partyGroup1).isNotEqualTo(partyGroup2);
    }
}
