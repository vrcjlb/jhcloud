package com.vlarco.jhcloud.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.PartyRole} entity.
 */
public class PartyRoleDTO implements Serializable {

    private Long id;

    private Boolean status;


    private Long roleId;

    private Long partyId;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

        PartyRoleDTO partyRoleDTO = (PartyRoleDTO) o;
        if (partyRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyRoleDTO{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", role=" + getRoleId() +
            ", party=" + getPartyId() +
            "}";
    }
}
