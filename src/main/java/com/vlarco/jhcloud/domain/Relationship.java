package com.vlarco.jhcloud.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Relationship.
 */
@Entity
@Table(name = "relationship")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Relationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "relationship")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyRelationship> partyRelationships = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("relationships")
    private Relationship parentId;

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

    public Relationship description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isStatus() {
        return status;
    }

    public Relationship status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<PartyRelationship> getPartyRelationships() {
        return partyRelationships;
    }

    public Relationship partyRelationships(Set<PartyRelationship> partyRelationships) {
        this.partyRelationships = partyRelationships;
        return this;
    }

    public Relationship addPartyRelationship(PartyRelationship partyRelationship) {
        this.partyRelationships.add(partyRelationship);
        partyRelationship.setRelationship(this);
        return this;
    }

    public Relationship removePartyRelationship(PartyRelationship partyRelationship) {
        this.partyRelationships.remove(partyRelationship);
        partyRelationship.setRelationship(null);
        return this;
    }

    public void setPartyRelationships(Set<PartyRelationship> partyRelationships) {
        this.partyRelationships = partyRelationships;
    }

    public Relationship getParentId() {
        return parentId;
    }

    public Relationship parentId(Relationship relationship) {
        this.parentId = relationship;
        return this;
    }

    public void setParentId(Relationship relationship) {
        this.parentId = relationship;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relationship)) {
            return false;
        }
        return id != null && id.equals(((Relationship) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Relationship{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
