package com.vlarco.jhcloud.repository;
import com.vlarco.jhcloud.domain.PartyAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartyAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyAttributeRepository extends JpaRepository<PartyAttribute, Long> {

}
