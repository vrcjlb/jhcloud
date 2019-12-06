package com.vlarco.jhcloud.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Party.
 */
@Entity
@Table(name = "party")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "party")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyRole> partyRoles = new HashSet<>();

    @OneToMany(mappedBy = "party")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyAttribute> partyAttributes = new HashSet<>();

    @OneToMany(mappedBy = "fromPartyId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyRelationship> fromPartyIds = new HashSet<>();

    @OneToMany(mappedBy = "toPartyId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyRelationship> toPartyIds = new HashSet<>();

    @OneToOne(mappedBy = "party")
    @JsonIgnore
    private Person person;

    @OneToOne(mappedBy = "party")
    @JsonIgnore
    private PartyGroup partyGroup;

    @ManyToOne
    @JsonIgnoreProperties("parties")
    private PartyType partyType;

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

    public Party status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public Party description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PartyRole> getPartyRoles() {
        return partyRoles;
    }

    public Party partyRoles(Set<PartyRole> partyRoles) {
        this.partyRoles = partyRoles;
        return this;
    }

    public Party addPartyRole(PartyRole partyRole) {
        this.partyRoles.add(partyRole);
        partyRole.setParty(this);
        return this;
    }

    public Party removePartyRole(PartyRole partyRole) {
        this.partyRoles.remove(partyRole);
        partyRole.setParty(null);
        return this;
    }

    public void setPartyRoles(Set<PartyRole> partyRoles) {
        this.partyRoles = partyRoles;
    }

    public Set<PartyAttribute> getPartyAttributes() {
        return partyAttributes;
    }

    public Party partyAttributes(Set<PartyAttribute> partyAttributes) {
        this.partyAttributes = partyAttributes;
        return this;
    }

    public Party addPartyAttribute(PartyAttribute partyAttribute) {
        this.partyAttributes.add(partyAttribute);
        partyAttribute.setParty(this);
        return this;
    }

    public Party removePartyAttribute(PartyAttribute partyAttribute) {
        this.partyAttributes.remove(partyAttribute);
        partyAttribute.setParty(null);
        return this;
    }

    public void setPartyAttributes(Set<PartyAttribute> partyAttributes) {
        this.partyAttributes = partyAttributes;
    }

    public Set<PartyRelationship> getFromPartyIds() {
        return fromPartyIds;
    }

    public Party fromPartyIds(Set<PartyRelationship> partyRelationships) {
        this.fromPartyIds = partyRelationships;
        return this;
    }

    public Party addFromPartyId(PartyRelationship partyRelationship) {
        this.fromPartyIds.add(partyRelationship);
        partyRelationship.setFromPartyId(this);
        return this;
    }

    public Party removeFromPartyId(PartyRelationship partyRelationship) {
        this.fromPartyIds.remove(partyRelationship);
        partyRelationship.setFromPartyId(null);
        return this;
    }

    public void setFromPartyIds(Set<PartyRelationship> partyRelationships) {
        this.fromPartyIds = partyRelationships;
    }

    public Set<PartyRelationship> getToPartyIds() {
        return toPartyIds;
    }

    public Party toPartyIds(Set<PartyRelationship> partyRelationships) {
        this.toPartyIds = partyRelationships;
        return this;
    }

    public Party addToPartyId(PartyRelationship partyRelationship) {
        this.toPartyIds.add(partyRelationship);
        partyRelationship.setToPartyId(this);
        return this;
    }

    public Party removeToPartyId(PartyRelationship partyRelationship) {
        this.toPartyIds.remove(partyRelationship);
        partyRelationship.setToPartyId(null);
        return this;
    }

    public void setToPartyIds(Set<PartyRelationship> partyRelationships) {
        this.toPartyIds = partyRelationships;
    }

    public Person getPerson() {
        return person;
    }

    public Party person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PartyGroup getPartyGroup() {
        return partyGroup;
    }

    public Party partyGroup(PartyGroup partyGroup) {
        this.partyGroup = partyGroup;
        return this;
    }

    public void setPartyGroup(PartyGroup partyGroup) {
        this.partyGroup = partyGroup;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public Party partyType(PartyType partyType) {
        this.partyType = partyType;
        return this;
    }

    public void setPartyType(PartyType partyType) {
        this.partyType = partyType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Party)) {
            return false;
        }
        return id != null && id.equals(((Party) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Party{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
