package com.phoebe.pbsub.repo;

import com.phoebe.pbsub.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Simplified Client Repository
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    /**
     * Find clients by name (fuzzy search)
     */
    List<Client> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find client with subscriptions by ID
     */
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.subscriptions WHERE c.id = :clientId")
    Optional<Client> findByIdWithSubscriptions(@Param("clientId") Long clientId);
}

