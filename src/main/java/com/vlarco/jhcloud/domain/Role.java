package com.vlarco.jhcloud.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "role")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyRole> partyRoles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Role description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isStatus() {
        return status;
    }

    public Role status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<PartyRole> getPartyRoles() {
        return partyRoles;
    }

    public Role partyRoles(Set<PartyRole> partyRoles) {
        this.partyRoles = partyRoles;
        return this;
    }

    public Role addPartyRole(PartyRole partyRole) {
        this.partyRoles.add(partyRole);
        partyRole.setRole(this);
        return this;
    }

    public Role removePartyRole(PartyRole partyRole) {
        this.partyRoles.remove(partyRole);
        partyRole.setRole(null);
        return this;
    }

    public void setPartyRoles(Set<PartyRole> partyRoles) {
        this.partyRoles = partyRoles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
