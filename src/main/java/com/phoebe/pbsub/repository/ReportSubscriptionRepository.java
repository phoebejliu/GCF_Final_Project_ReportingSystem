package com.phoebe.pbsub.repository;

import com.phoebe.pbsub.entity.ReportSubscription;
import com.phoebe.pbsub.entity.enums.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Report Subscription Repository
 */
@Repository
public interface ReportSubscriptionRepository extends JpaRepository<ReportSubscription, Long> {
    
    /**
     * Find all subscriptions by client ID with pagination
     */
    Page<ReportSubscription> findByClientId(Long clientId, Pageable pageable);
    
    /**
     * Find all subscriptions by client ID - legacy method for backward compatibility
     */
    List<ReportSubscription> findByClientId(Long clientId);
    
    /**
     * Find subscriptions by client ID and report type
     */
    List<ReportSubscription> findByClientIdAndReportType(Long clientId, ReportType reportType);
    
}

