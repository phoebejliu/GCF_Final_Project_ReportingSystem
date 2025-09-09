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
 * 客户REST API - 初学者友好版本
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
     * 获取所有客户
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client by ID
     * 根据ID获取客户
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(
            @PathVariable Long id) {
        Client client = clientService.get(id);
        return ResponseEntity.ok(client);
    }
    
    /**
     * Get client with subscriptions by ID
     * 根据ID获取客户及其订阅信息
     */
    @GetMapping("/{id}/with-subscriptions")
    public ResponseEntity<Client> getClientWithSubscriptions(
            @PathVariable Long id) {
        Client client = clientService.getWithSubscriptions(id);
        return ResponseEntity.ok(client);
    }
    
    /**
     * Create new client
     * 创建新客户
     */
    @PostMapping
    public ResponseEntity<Client> createClient(
            @Valid @RequestBody Client client) {
        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }
    
    /**
     * Update client information
     * 更新客户信息
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
     * 删除客户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Search clients
     * 搜索客户
     */
    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(
            @RequestParam String name) {
        List<Client> clients = clientService.searchByName(name);
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client statistics
     * 获取客户统计信息
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getClientStats() {
        Map<String, Object> stats = Map.of(
                "totalClients", clientService.count()
        );
        return ResponseEntity.ok(stats);
    }
}