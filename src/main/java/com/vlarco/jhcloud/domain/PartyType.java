package com.vlarco.jhcloud.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PartyType.
 */
@Entity
@Table(name = "party_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "partyType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyTypeAttribute> partyTypeAttributes = new HashSet<>();

    @OneToMany(mappedBy = "partyType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Party> parties = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("partyTypes")
    private PartyType parentId;

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

    public PartyType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PartyTypeAttribute> getPartyTypeAttributes() {
        return partyTypeAttributes;
    }

    public PartyType partyTypeAttributes(Set<PartyTypeAttribute> partyTypeAttributes) {
        this.partyTypeAttributes = partyTypeAttributes;
        return this;
    }

    public PartyType addPartyTypeAttribute(PartyTypeAttribute partyTypeAttribute) {
        this.partyTypeAttributes.add(partyTypeAttribute);
        partyTypeAttribute.setPartyType(this);
        return this;
    }

    public PartyType removePartyTypeAttribute(PartyTypeAttribute partyTypeAttribute) {
        this.partyTypeAttributes.remove(partyTypeAttribute);
        partyTypeAttribute.setPartyType(null);
        return this;
    }

    public void setPartyTypeAttributes(Set<PartyTypeAttribute> partyTypeAttributes) {
        this.partyTypeAttributes = partyTypeAttributes;
    }

    public Set<Party> getParties() {
        return parties;
    }

    public PartyType parties(Set<Party> parties) {
        this.parties = parties;
        return this;
    }

    public PartyType addParty(Party party) {
        this.parties.add(party);
        party.setPartyType(this);
        return this;
    }

    public PartyType removeParty(Party party) {
        this.parties.remove(party);
        party.setPartyType(null);
        return this;
    }

    public void setParties(Set<Party> parties) {
        this.parties = parties;
    }

    public PartyType getParentId() {
        return parentId;
    }

    public PartyType parentId(PartyType partyType) {
        this.parentId = partyType;
        return this;
    }

    public void setParentId(PartyType partyType) {
        this.parentId = partyType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyType)) {
            return false;
        }
        return id != null && id.equals(((PartyType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PartyType{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
