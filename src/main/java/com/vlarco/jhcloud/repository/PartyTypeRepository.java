package com.vlarco.jhcloud.repository;
import com.vlarco.jhcloud.domain.PartyType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartyType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyTypeRepository extends JpaRepository<PartyType, Long> {

}
