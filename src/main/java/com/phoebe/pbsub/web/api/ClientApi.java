package com.phoebe.pbsub.web.api;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Client REST API
 */
@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client Management", description = "Client related APIs")
public class ClientApi {
    
    private final ClientService clientService;
    
    public ClientApi(ClientService clientService) {
        this.clientService = clientService;
    }
    
    /**
     * Get all clients
     */
    @GetMapping
    @Operation(summary = "Get all clients", description = "Returns a list of all clients in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved client list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID", description = "Get detailed client information by client ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved client information"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Client> getClient(
            @Parameter(description = "Client ID", required = true) @PathVariable Long id) {
        Client client = clientService.get(id);
        return ResponseEntity.ok(client);
    }
    
    /**
     * Get client with subscriptions by ID
     */
    @GetMapping("/{id}/with-subscriptions")
    @Operation(summary = "Get client with subscriptions", description = "Get detailed client information with all subscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved client and subscription information"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Client> getClientWithSubscriptions(
            @Parameter(description = "Client ID", required = true) @PathVariable Long id) {
        Client client = clientService.getWithSubscriptions(id);
        return ResponseEntity.ok(client);
    }
    
    /**
     * Create new client
     */
    @PostMapping
    @Operation(summary = "Create new client", description = "Create a new client record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Client> createClient(
            @Parameter(description = "Client information", required = true) @Valid @RequestBody Client client) {
        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }
    
    /**
     * Update client information
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update client information", description = "Update information for specified client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client information updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Client> updateClient(
            @Parameter(description = "Client ID", required = true) @PathVariable Long id,
            @Parameter(description = "Client information", required = true) @Valid @RequestBody Client client) {
        client.setId(id);
        Client updatedClient = clientService.save(client);
        return ResponseEntity.ok(updatedClient);
    }
    
    /**
     * Delete client
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client", description = "Delete specified client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "Client ID", required = true) @PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Search clients
     */
    @GetMapping("/search")
    @Operation(summary = "Search clients", description = "Search clients by client name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Client>> searchClients(
            @Parameter(description = "Client name keyword", required = true) @RequestParam String name) {
        List<Client> clients = clientService.searchByName(name);
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client statistics
     */
    @GetMapping("/stats")
    @Operation(summary = "Get client statistics", description = "Get client statistics such as total count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, Object>> getClientStats() {
        Map<String, Object> stats = Map.of(
                "totalClients", clientService.count()
        );
        return ResponseEntity.ok(stats);
    }
}
