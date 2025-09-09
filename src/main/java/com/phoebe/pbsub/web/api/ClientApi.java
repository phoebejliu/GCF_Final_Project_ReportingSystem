package com.phoebe.pbsub.web.api;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Client REST API - Beginner Friendly Version
 */
@RestController
@RequestMapping("/api/clients")
public class ClientApi {
    
    private final ClientService clientService;
    
    public ClientApi(ClientService clientService) {
        this.clientService = clientService;
    }
    
    /**
     * Get all clients
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(
            @PathVariable Long id) {
        Client client = clientService.get(id);
        return ResponseEntity.ok(client);
    }
    
    /**
     * Get client with subscriptions by ID
     */
    @GetMapping("/{id}/with-subscriptions")
    public ResponseEntity<Client> getClientWithSubscriptions(
            @PathVariable Long id) {
        Client client = clientService.getWithSubscriptions(id);
        return ResponseEntity.ok(client);
    }
    
    /**
     * Create new client
     */
    @PostMapping
    public ResponseEntity<Client> createClient(
            @Valid @RequestBody Client client) {
        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }
    
    /**
     * Update client information
     */
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody Client client) {
        client.setId(id);
        Client updatedClient = clientService.save(client);
        return ResponseEntity.ok(updatedClient);
    }
    
    /**
     * Delete client
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Search clients
     */
    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(
            @RequestParam String name) {
        List<Client> clients = clientService.searchByName(name);
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getClientStats() {
        Map<String, Object> stats = Map.of(
                "totalClients", clientService.count()
        );
        return ResponseEntity.ok(stats);
    }
}