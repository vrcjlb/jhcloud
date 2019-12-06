package com.vlarco.jhcloud.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.PartyType} entity.
 */
public class PartyTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;


    private Long parentIdId;

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

    public Long getParentIdId() {
        return parentIdId;
    }

    public void setParentIdId(Long partyTypeId) {
        this.parentIdId = partyTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartyTypeDTO partyTypeDTO = (PartyTypeDTO) o;
        if (partyTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyTypeDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", parentId=" + getParentIdId() +
            "}";
    }
}
