package com.vlarco.jhcloud.repository;
import com.vlarco.jhcloud.domain.PartyRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartyRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRelationshipRepository extends JpaRepository<PartyRelationship, Long> {

}
