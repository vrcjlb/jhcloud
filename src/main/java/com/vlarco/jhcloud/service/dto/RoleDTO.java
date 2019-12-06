package com.vlarco.jhcloud.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.Role} entity.
 */
public class RoleDTO implements Serializable {

    private Long id;

    private String description;

    private Boolean status;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoleDTO roleDTO = (RoleDTO) o;
        if (roleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
