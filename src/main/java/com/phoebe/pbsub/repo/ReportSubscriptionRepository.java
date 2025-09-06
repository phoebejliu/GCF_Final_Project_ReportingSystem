package com.phoebe.pbsub.repo;

import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.domain.enums.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Simplified Report Subscription Repository
 */
@Repository
public interface ReportSubscriptionRepository extends JpaRepository<ReportSubscription, Long> {
    
    /**
     * Find all subscriptions by client ID
     */
    List<ReportSubscription> findByClientId(Long clientId);
    
    /**
     * Find active subscriptions by client ID
     */
    List<ReportSubscription> findByClientIdAndActiveTrue(Long clientId);
    
    /**
     * Find subscription by client ID and report type (for duplicate check)
     */
    Optional<ReportSubscription> findByClientIdAndReportType(Long clientId, ReportType reportType);
}

