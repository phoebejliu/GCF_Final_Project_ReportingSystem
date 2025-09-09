package com.phoebe.pbsub.repo;

import com.phoebe.pbsub.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Simplified Client Repository - Beginner Friendly
 * 简化的客户数据访问层 - 初学者友好版本
 * 
 * 这个接口展示了Spring Data JPA的基本用法：
 * 1. 继承JpaRepository - 自动获得基本的CRUD操作
 * 2. 方法命名约定 - Spring会根据方法名自动生成查询
 * 3. @Query注解 - 可以自定义复杂的查询语句
 * 4. 泛型参数 - <Client, Long> 表示实体类型和主键类型
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

