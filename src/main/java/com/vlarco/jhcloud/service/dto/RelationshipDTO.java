package com.vlarco.jhcloud.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.Relationship} entity.
 */
public class RelationshipDTO implements Serializable {

    private Long id;

    private String description;

    private Boolean status;


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

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getParentIdId() {
        return parentIdId;
    }

    public void setParentIdId(Long relationshipId) {
        this.parentIdId = relationshipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RelationshipDTO relationshipDTO = (RelationshipDTO) o;
        if (relationshipDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), relationshipDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RelationshipDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + isStatus() + "'" +
            ", parentId=" + getParentIdId() +
            "}";
    }
}
