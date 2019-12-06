package com.vlarco.jhcloud.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.Party} entity.
 */
public class PartyDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean status;

    private String description;


    private Long partyTypeId;

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

        PartyDTO partyDTO = (PartyDTO) o;
        if (partyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyDTO{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", partyType=" + getPartyTypeId() +
            "}";
    }
}
