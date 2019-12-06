package com.vlarco.jhcloud.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PartyTypeAttribute.
 */
@Entity
@Table(name = "party_type_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartyTypeAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "partyTypeAttribute")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyAttribute> partyAttributes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("partyTypeAttributes")
    private PartyType partyType;

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

    public PartyTypeAttribute description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PartyAttribute> getPartyAttributes() {
        return partyAttributes;
    }

    public PartyTypeAttribute partyAttributes(Set<PartyAttribute> partyAttributes) {
        this.partyAttributes = partyAttributes;
        return this;
    }

    public PartyTypeAttribute addPartyAttribute(PartyAttribute partyAttribute) {
        this.partyAttributes.add(partyAttribute);
        partyAttribute.setPartyTypeAttribute(this);
        return this;
    }

    public PartyTypeAttribute removePartyAttribute(PartyAttribute partyAttribute) {
        this.partyAttributes.remove(partyAttribute);
        partyAttribute.setPartyTypeAttribute(null);
        return this;
    }

    public void setPartyAttributes(Set<PartyAttribute> partyAttributes) {
        this.partyAttributes = partyAttributes;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public PartyTypeAttribute partyType(PartyType partyType) {
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
        if (!(o instanceof PartyTypeAttribute)) {
            return false;
        }
        return id != null && id.equals(((PartyTypeAttribute) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PartyTypeAttribute{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
