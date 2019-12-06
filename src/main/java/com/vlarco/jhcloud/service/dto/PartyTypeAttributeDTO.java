package com.vlarco.jhcloud.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.PartyTypeAttribute} entity.
 */
public class PartyTypeAttributeDTO implements Serializable {

    private Long id;

    private String description;


    private Long partyTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPartyTypeId() {
        return partyTypeId;
    }

    public void setPartyTypeId(Long partyTypeId) {
        this.partyTypeId = partyTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartyTypeAttributeDTO partyTypeAttributeDTO = (PartyTypeAttributeDTO) o;
        if (partyTypeAttributeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyTypeAttributeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyTypeAttributeDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", partyType=" + getPartyTypeId() +
            "}";
    }
}
