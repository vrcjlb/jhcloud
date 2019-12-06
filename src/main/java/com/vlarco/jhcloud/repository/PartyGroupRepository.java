package com.vlarco.jhcloud.repository;
import com.vlarco.jhcloud.domain.PartyGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartyGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyGroupRepository extends JpaRepository<PartyGroup, Long> {

}
