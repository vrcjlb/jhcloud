package com.vlarco.jhcloud.repository;
import com.vlarco.jhcloud.domain.Party;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Party entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

}
