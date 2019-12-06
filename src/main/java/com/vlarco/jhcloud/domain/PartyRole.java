package com.vlarco.jhcloud.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PartyRole.
 */
@Entity
@Table(name = "party_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartyRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonIgnoreProperties("partyRoles")
    private Role role;

    @ManyToOne
    @JsonIgnoreProperties("partyRoles")
    private Party party;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return status;
    }

    public PartyRole status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public PartyRole role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Party getParty() {
        return party;
    }

    public PartyRole party(Party party) {
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
        if (!(o instanceof PartyRole)) {
            return false;
        }
        return id != null && id.equals(((PartyRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PartyRole{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
