package com.phoebe.pbsub.web.api;

import com.phoebe.pbsub.entity.Client;
import com.phoebe.pbsub.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Client REST API
 */
@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client Management", description = "API for managing clients")
public class ClientApi {
    
    private final ClientService clientService;
    
    public ClientApi(ClientService clientService) {
        this.clientService = clientService;
    }
    
    /**
     * Get all clients with pagination
     */
    @GetMapping
    @Operation(summary = "Get all clients", description = "Retrieve a paginated list of all clients")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list of clients")
    })
    public ResponseEntity<Page<Client>> getAllClients(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<Client> clients = clientService.findAll(pageable);
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID", description = "Retrieve a specific client by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved client"),
        @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Client> getClient(
            @Parameter(description = "Client ID") @PathVariable Long id) {
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
    @Operation(summary = "Create new client", description = "Create a new client with validation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Client created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Client> createClient(
            @Parameter(description = "Client data") @Valid @RequestBody Client client) {
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
     * Search clients with pagination
     */
    @GetMapping("/search")
    @Operation(summary = "Search clients", description = "Search clients by name with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved search results")
    })
    public ResponseEntity<Page<Client>> searchClients(
            @RequestParam String name,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<Client> clients = clientService.searchByName(name, pageable);
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