package com.vlarco.jhcloud.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PartyAttribute.
 */
@Entity
@Table(name = "party_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartyAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value_attribute")
    private String valueAttribute;

    @ManyToOne
    @JsonIgnoreProperties("partyAttributes")
    private PartyTypeAttribute partyTypeAttribute;

    @ManyToOne
    @JsonIgnoreProperties("partyAttributes")
    private Party party;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueAttribute() {
        return valueAttribute;
    }

    public PartyAttribute valueAttribute(String valueAttribute) {
        this.valueAttribute = valueAttribute;
        return this;
    }

    public void setValueAttribute(String valueAttribute) {
        this.valueAttribute = valueAttribute;
    }

    public PartyTypeAttribute getPartyTypeAttribute() {
        return partyTypeAttribute;
    }

    public PartyAttribute partyTypeAttribute(PartyTypeAttribute partyTypeAttribute) {
        this.partyTypeAttribute = partyTypeAttribute;
        return this;
    }

    public void setPartyTypeAttribute(PartyTypeAttribute partyTypeAttribute) {
        this.partyTypeAttribute = partyTypeAttribute;
    }

    public Party getParty() {
        return party;
    }

    public PartyAttribute party(Party party) {
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
        if (!(o instanceof PartyAttribute)) {
            return false;
        }
        return id != null && id.equals(((PartyAttribute) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PartyAttribute{" +
            "id=" + getId() +
            ", valueAttribute='" + getValueAttribute() + "'" +
            "}";
    }
}
