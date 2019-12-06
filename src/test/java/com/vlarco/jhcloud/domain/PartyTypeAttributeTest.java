package com.vlarco.jhcloud.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vlarco.jhcloud.web.rest.TestUtil;

public class PartyTypeAttributeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyTypeAttribute.class);
        PartyTypeAttribute partyTypeAttribute1 = new PartyTypeAttribute();
        partyTypeAttribute1.setId(1L);
        PartyTypeAttribute partyTypeAttribute2 = new PartyTypeAttribute();
        partyTypeAttribute2.setId(partyTypeAttribute1.getId());
        assertThat(partyTypeAttribute1).isEqualTo(partyTypeAttribute2);
        partyTypeAttribute2.setId(2L);
        assertThat(partyTypeAttribute1).isNotEqualTo(partyTypeAttribute2);
        partyTypeAttribute1.setId(null);
        assertThat(partyTypeAttribute1).isNotEqualTo(partyTypeAttribute2);
    }
}
