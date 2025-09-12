package com.phoebe.pbsub.repository;

import com.phoebe.pbsub.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Client Repository
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
     * Find clients by name (fuzzy search) with pagination
     */
    Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    /**
     * Find clients by name (fuzzy search) - legacy method for backward compatibility
     */
    List<Client> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find client with subscriptions by ID
     */
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.subscriptions WHERE c.id = :clientId")
    java.util.Optional<Client> findByIdWithSubscriptions(@Param("clientId") Long clientId);
}

