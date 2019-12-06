package com.vlarco.jhcloud.repository;
import com.vlarco.jhcloud.domain.PartyTypeAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartyTypeAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyTypeAttributeRepository extends JpaRepository<PartyTypeAttribute, Long> {

}
