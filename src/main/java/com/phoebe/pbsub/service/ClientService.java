package com.phoebe.pbsub.service;

import com.phoebe.pbsub.entity.Client;
import com.phoebe.pbsub.exception.ClientNotFoundException;
import com.phoebe.pbsub.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Client Service
 * 
 * This class demonstrates the basic usage of Service layer in Spring Boot:
 * 1. @Service annotation - tells Spring this is a service component
 * 2. @Transactional annotation - automatically manages database transactions
 * 3. Dependency injection - inject Repository through constructor
 * 4. Basic CRUD operations - Create, Read, Update, Delete
 */
@Service
@Transactional
public class ClientService {
    
    private final ClientRepository clientRepository;
    
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    /**
     * Find all clients with pagination
     */
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }
    
    /**
     * Find all clients - legacy method for backward compatibility
     */
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
    
    /**
     * Find client by ID
     */
    @Transactional(readOnly = true)
    public Client get(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
    
    /**
     * Find client with subscriptions by ID
     */
    @Transactional(readOnly = true)
    public Client getWithSubscriptions(Long id) {
        return clientRepository.findByIdWithSubscriptions(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
    
    /**
     * Save client
     */
    public Client save(Client client) {
        return clientRepository.save(client);
    }
    
    /**
     * Delete client
     */
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }
    
    /**
     * Search clients by name with pagination
     */
    @Transactional(readOnly = true)
    public Page<Client> searchByName(String name, Pageable pageable) {
        return clientRepository.findByNameContainingIgnoreCase(name, pageable);
    }
    
    /**
     * Search clients by name - legacy method for backward compatibility
     */
    @Transactional(readOnly = true)
    public List<Client> searchByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Count total clients
     */
    @Transactional(readOnly = true)
    public long count() {
        return clientRepository.count();
    }
}

