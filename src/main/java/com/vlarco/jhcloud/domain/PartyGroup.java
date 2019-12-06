package com.vlarco.jhcloud.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PartyGroup.
 */
@Entity
@Table(name = "party_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartyGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "group_name_local")
    private String groupNameLocal;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "office")
    private String office;

    @Column(name = "comments")
    private String comments;

    @Column(name = "logo_image_url")
    private String logoImageUrl;

    @OneToOne
    @JoinColumn(unique = true)
    private Party party;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public PartyGroup groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNameLocal() {
        return groupNameLocal;
    }

    public PartyGroup groupNameLocal(String groupNameLocal) {
        this.groupNameLocal = groupNameLocal;
        return this;
    }

    public void setGroupNameLocal(String groupNameLocal) {
        this.groupNameLocal = groupNameLocal;
    }

    public String getSiteName() {
        return siteName;
    }

    public PartyGroup siteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOffice() {
        return office;
    }

    public PartyGroup office(String office) {
        this.office = office;
        return this;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getComments() {
        return comments;
    }

    public PartyGroup comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public PartyGroup logoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
        return this;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public Party getParty() {
        return party;
    }

    public PartyGroup party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyGroup)) {
            return false;
        }
        return id != null && id.equals(((PartyGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PartyGroup{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", groupNameLocal='" + getGroupNameLocal() + "'" +
            ", siteName='" + getSiteName() + "'" +
            ", office='" + getOffice() + "'" +
            ", comments='" + getComments() + "'" +
            ", logoImageUrl='" + getLogoImageUrl() + "'" +
            "}";
    }
}
