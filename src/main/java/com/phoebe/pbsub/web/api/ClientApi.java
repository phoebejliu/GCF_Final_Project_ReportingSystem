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
 * Client REST API - Beginner Friendly Version
 * 客户REST API - 初学者友好版本
 */
@RestController
@RequestMapping("/api/clients")
@Tag(name = "客户管理", description = "客户信息的CRUD操作API")
public class ClientApi {
    
    private final ClientService clientService;
    
    public ClientApi(ClientService clientService) {
        this.clientService = clientService;
    }
    
    /**
     * Get all clients
     * 获取所有客户
     */
    @Operation(summary = "获取所有客户", description = "返回系统中所有客户的信息列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取客户列表")
    })
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client by ID
     * 根据ID获取客户
     */
    @Operation(summary = "根据ID获取客户", description = "通过客户ID获取特定客户的详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取客户信息"),
            @ApiResponse(responseCode = "404", description = "客户不存在")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(
            @Parameter(description = "客户ID", required = true) @PathVariable Long id) {
        Client client = clientService.get(id);
        return ResponseEntity.ok(client);
    }
    
    /**
     * Get client with subscriptions by ID
     * 根据ID获取客户及其订阅信息
     */
    @Operation(summary = "获取客户及其订阅信息", description = "获取客户详细信息，包括其所有报告订阅")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取客户及订阅信息"),
            @ApiResponse(responseCode = "404", description = "客户不存在")
    })
    @GetMapping("/{id}/with-subscriptions")
    public ResponseEntity<Client> getClientWithSubscriptions(
            @Parameter(description = "客户ID", required = true) @PathVariable Long id) {
        Client client = clientService.getWithSubscriptions(id);
        return ResponseEntity.ok(client);
    }
    
    /**
     * Create new client
     * 创建新客户
     */
    @Operation(summary = "创建新客户", description = "在系统中创建新的客户记录")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "客户创建成功"),
            @ApiResponse(responseCode = "400", description = "请求数据无效")
    })
    @PostMapping
    public ResponseEntity<Client> createClient(
            @Parameter(description = "客户信息", required = true) @Valid @RequestBody Client client) {
        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }
    
    /**
     * Update client information
     * 更新客户信息
     */
    @Operation(summary = "更新客户信息", description = "更新指定客户的详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "客户信息更新成功"),
            @ApiResponse(responseCode = "400", description = "请求数据无效"),
            @ApiResponse(responseCode = "404", description = "客户不存在")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(
            @Parameter(description = "客户ID", required = true) @PathVariable Long id,
            @Parameter(description = "更新的客户信息", required = true) @Valid @RequestBody Client client) {
        client.setId(id);
        Client updatedClient = clientService.save(client);
        return ResponseEntity.ok(updatedClient);
    }
    
    /**
     * Delete client
     * 删除客户
     */
    @Operation(summary = "删除客户", description = "从系统中删除指定的客户记录")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "客户删除成功"),
            @ApiResponse(responseCode = "404", description = "客户不存在")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "客户ID", required = true) @PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Search clients
     * 搜索客户
     */
    @Operation(summary = "搜索客户", description = "根据客户名称搜索客户")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "搜索成功")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(
            @Parameter(description = "客户名称关键字", required = true) @RequestParam String name) {
        List<Client> clients = clientService.searchByName(name);
        return ResponseEntity.ok(clients);
    }
    
    /**
     * Get client statistics
     * 获取客户统计信息
     */
    @Operation(summary = "获取客户统计信息", description = "获取系统中客户的统计信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取统计信息")
    })
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getClientStats() {
        Map<String, Object> stats = Map.of(
                "totalClients", clientService.count()
        );
        return ResponseEntity.ok(stats);
    }
}