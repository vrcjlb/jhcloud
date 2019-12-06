package com.vlarco.jhcloud.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vlarco.jhcloud.domain.PartyGroup} entity.
 */
public class PartyGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String groupName;

    private String groupNameLocal;

    private String siteName;

    private String office;

    private String comments;

    private String logoImageUrl;


    private Long partyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNameLocal() {
        return groupNameLocal;
    }

    public void setGroupNameLocal(String groupNameLocal) {
        this.groupNameLocal = groupNameLocal;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
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

        PartyGroupDTO partyGroupDTO = (PartyGroupDTO) o;
        if (partyGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyGroupDTO{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", groupNameLocal='" + getGroupNameLocal() + "'" +
            ", siteName='" + getSiteName() + "'" +
            ", office='" + getOffice() + "'" +
            ", comments='" + getComments() + "'" +
            ", logoImageUrl='" + getLogoImageUrl() + "'" +
            ", party=" + getPartyId() +
            "}";
    }
}
