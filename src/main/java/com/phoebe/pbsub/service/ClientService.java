package com.phoebe.pbsub.service;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.repo.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Simplified Client Service - Beginner Friendly
 * 简化的客户服务层 - 初学者友好版本
 * 
 * 这个类展示了Spring Boot中Service层的基本用法：
 * 1. @Service注解 - 告诉Spring这是一个服务组件
 * 2. @Transactional注解 - 自动管理数据库事务
 * 3. 依赖注入 - 通过构造函数注入Repository
 * 4. 基本的CRUD操作 - 创建、读取、更新、删除
 */
@Service
@Transactional
public class ClientService {
    
    private final ClientRepository clientRepository;
    
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    /**
     * Find all clients
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
                .orElseThrow(() -> new IllegalArgumentException("Client not found, ID: " + id));
    }
    
    /**
     * Find client with subscriptions by ID
     */
    @Transactional(readOnly = true)
    public Client getWithSubscriptions(Long id) {
        return clientRepository.findByIdWithSubscriptions(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found, ID: " + id));
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
            throw new IllegalArgumentException("Client not found, ID: " + id);
        }
        clientRepository.deleteById(id);
    }
    
    /**
     * Search clients by name
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

