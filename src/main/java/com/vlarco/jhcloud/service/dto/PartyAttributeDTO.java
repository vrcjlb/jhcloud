package com.vlarco.jhcloud.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.PartyAttribute} entity.
 */
public class PartyAttributeDTO implements Serializable {

    private Long id;

    private String valueAttribute;


    private Long partyTypeAttributeId;

    private Long partyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueAttribute() {
        return valueAttribute;
    }

    public void setValueAttribute(String valueAttribute) {
        this.valueAttribute = valueAttribute;
    }

    public Long getPartyTypeAttributeId() {
        return partyTypeAttributeId;
    }

    public void setPartyTypeAttributeId(Long partyTypeAttributeId) {
        this.partyTypeAttributeId = partyTypeAttributeId;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartyAttributeDTO partyAttributeDTO = (PartyAttributeDTO) o;
        if (partyAttributeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyAttributeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyAttributeDTO{" +
            "id=" + getId() +
            ", valueAttribute='" + getValueAttribute() + "'" +
            ", partyTypeAttribute=" + getPartyTypeAttributeId() +
            ", party=" + getPartyId() +
            "}";
    }
}
