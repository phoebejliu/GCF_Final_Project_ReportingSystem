package com.phoebe.pbsub.repo;

import com.phoebe.pbsub.domain.ReportSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Simplified Report Subscription Repository - Beginner Friendly
 */
@Repository
public interface ReportSubscriptionRepository extends JpaRepository<ReportSubscription, Long> {
    
    /**
     * Find all subscriptions by client ID
     */
    List<ReportSubscription> findByClientId(Long clientId);
    
}

