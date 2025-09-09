package com.phoebe.pbsub.repo;

import com.phoebe.pbsub.domain.ReportSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Simplified Report Subscription Repository - Beginner Friendly
 * 简化的报告订阅数据访问层 - 初学者友好版本
 */
@Repository
public interface ReportSubscriptionRepository extends JpaRepository<ReportSubscription, Long> {
    
    /**
     * 根据客户ID查找所有订阅
     * Find all subscriptions by client ID
     */
    List<ReportSubscription> findByClientId(Long clientId);
    
}

