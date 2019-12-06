package com.vlarco.jhcloud.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.PartyRelationship} entity.
 */
public class PartyRelationshipDTO implements Serializable {

    private Long id;

    private Boolean status;


    private Long relationshipId;

    private Long fromPartyIdId;

    private Long toPartyIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Long getFromPartyIdId() {
        return fromPartyIdId;
    }

    public void setFromPartyIdId(Long partyId) {
        this.fromPartyIdId = partyId;
    }

    public Long getToPartyIdId() {
        return toPartyIdId;
    }

    public void setToPartyIdId(Long partyId) {
        this.toPartyIdId = partyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartyRelationshipDTO partyRelationshipDTO = (PartyRelationshipDTO) o;
        if (partyRelationshipDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyRelationshipDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyRelationshipDTO{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", relationship=" + getRelationshipId() +
            ", fromPartyId=" + getFromPartyIdId() +
            ", toPartyId=" + getToPartyIdId() +
            "}";
    }
}
