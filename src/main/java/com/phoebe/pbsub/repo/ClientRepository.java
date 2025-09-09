package com.phoebe.pbsub.repo;

import com.phoebe.pbsub.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Simplified Client Repository - Beginner Friendly
 * 
 * This interface demonstrates the basic usage of Spring Data JPA:
 * 1. Extends JpaRepository - automatically provides basic CRUD operations
 * 2. Method naming conventions - Spring generates queries based on method names
 * 3. @Query annotation - allows custom complex query statements
 * 4. Generic parameters - <Client, Long> represents entity type and primary key type
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
    java.util.Optional<Client> findByIdWithSubscriptions(@Param("clientId") Long clientId);
}

