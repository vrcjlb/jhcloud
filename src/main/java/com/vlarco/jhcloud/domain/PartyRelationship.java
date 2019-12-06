package com.vlarco.jhcloud.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PartyRelationship.
 */
@Entity
@Table(name = "party_relationship")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartyRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonIgnoreProperties("partyRelationships")
    private Relationship relationship;

    @ManyToOne
    @JsonIgnoreProperties("fromPartyIds")
    private Party fromPartyId;

    @ManyToOne
    @JsonIgnoreProperties("toPartyIds")
    private Party toPartyId;

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

    public PartyRelationship status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public PartyRelationship relationship(Relationship relationship) {
        this.relationship = relationship;
        return this;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public Party getFromPartyId() {
        return fromPartyId;
    }

    public PartyRelationship fromPartyId(Party party) {
        this.fromPartyId = party;
        return this;
    }

    public void setFromPartyId(Party party) {
        this.fromPartyId = party;
    }

    public Party getToPartyId() {
        return toPartyId;
    }

    public PartyRelationship toPartyId(Party party) {
        this.toPartyId = party;
        return this;
    }

    public void setToPartyId(Party party) {
        this.toPartyId = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyRelationship)) {
            return false;
        }
        return id != null && id.equals(((PartyRelationship) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PartyRelationship{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
