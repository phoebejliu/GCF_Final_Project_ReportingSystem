package com.phoebe.pbsub.service;

import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.repo.ReportSubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Simplified Report Subscription Service - Beginner Friendly
 * 简化的报告订阅服务层 - 初学者友好版本
 * 
 * 这个类展示了Spring Boot中Service层的核心概念：
 * 1. 业务逻辑封装 - 将复杂的业务规则封装在Service层
 * 2. 事务管理 - 使用@Transactional确保数据一致性
 * 3. 异常处理 - 提供友好的错误信息
 * 4. 数据验证 - 在保存前进行业务规则检查
 */
@Service
@Transactional
public class ReportSubscriptionService {
    
    private final ReportSubscriptionRepository subscriptionRepository;
    
    public ReportSubscriptionService(ReportSubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
    
    /**
     * 根据客户ID查找所有订阅
     * Find all subscriptions by client ID
     */
    @Transactional(readOnly = true)
    public List<ReportSubscription> findByClientId(Long clientId) {
        return subscriptionRepository.findByClientId(clientId);
    }
    
    /**
     * 根据客户ID查找活跃的订阅
     * Find active subscriptions by client ID
     */
    @Transactional(readOnly = true)
    public List<ReportSubscription> findActiveByClientId(Long clientId) {
        return subscriptionRepository.findByClientIdAndActiveTrue(clientId);
    }
    
    /**
     * 根据ID查找订阅
     * Find subscription by ID
     */
    @Transactional(readOnly = true)
    public ReportSubscription get(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found, ID: " + id));
    }
    
    /**
     * 保存订阅
     * Save subscription
     */
    public ReportSubscription save(ReportSubscription subscription) {
        return subscriptionRepository.save(subscription);
    }
    
    /**
     * 删除订阅
     * Delete subscription
     */
    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new IllegalArgumentException("Subscription not found, ID: " + id);
        }
        subscriptionRepository.deleteById(id);
    }
    
    /**
     * 切换订阅激活状态
     * Toggle subscription active status
     */
    public ReportSubscription toggleActive(Long id) {
        ReportSubscription subscription = get(id);
        subscription.setActive(!subscription.isActive());
        return subscriptionRepository.save(subscription);
    }
}

