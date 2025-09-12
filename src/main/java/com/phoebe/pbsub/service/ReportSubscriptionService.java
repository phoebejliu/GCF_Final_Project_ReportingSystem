package com.phoebe.pbsub.service;

import com.phoebe.pbsub.entity.ReportSubscription;
import com.phoebe.pbsub.exception.DuplicateSubscriptionException;
import com.phoebe.pbsub.exception.SubscriptionNotFoundException;
import com.phoebe.pbsub.repository.ReportSubscriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Report Subscription Service
 * 
 * This class demonstrates the core concepts of Service layer in Spring Boot:
 * 1. Business logic encapsulation - encapsulate complex business rules in Service layer
 * 2. Transaction management - use @Transactional to ensure data consistency
 * 3. Exception handling - provide friendly error messages
 * 4. Data validation - perform business rule checks before saving
 */
@Service
@Transactional
public class ReportSubscriptionService {
    
    private final ReportSubscriptionRepository subscriptionRepository;
    
    public ReportSubscriptionService(ReportSubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
    
    /**
     * Find all subscriptions with pagination
     */
    @Transactional(readOnly = true)
    public Page<ReportSubscription> findAll(Pageable pageable) {
        return subscriptionRepository.findAll(pageable);
    }
    
    /**
     * Find all subscriptions - legacy method for backward compatibility
     */
    @Transactional(readOnly = true)
    public List<ReportSubscription> findAll() {
        return subscriptionRepository.findAll();
    }
    
    /**
     * Find all subscriptions by client ID with pagination
     */
    @Transactional(readOnly = true)
    public Page<ReportSubscription> findByClientId(Long clientId, Pageable pageable) {
        return subscriptionRepository.findByClientId(clientId, pageable);
    }
    
    /**
     * Find all subscriptions by client ID - legacy method for backward compatibility
     */
    @Transactional(readOnly = true)
    public List<ReportSubscription> findByClientId(Long clientId) {
        return subscriptionRepository.findByClientId(clientId);
    }
    
    
    /**
     * Find subscription by ID
     */
    @Transactional(readOnly = true)
    public ReportSubscription get(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException(id));
    }
    
    /**
     * Save subscription with business logic validation
     */
    public ReportSubscription save(ReportSubscription subscription) {
        // Check for duplicate subscription
        if (subscription.getId() == null) {
            List<ReportSubscription> existingSubscriptions = subscriptionRepository
                    .findByClientIdAndReportType(subscription.getClient().getId(), subscription.getReportType());
            if (!existingSubscriptions.isEmpty()) {
                throw new DuplicateSubscriptionException(
                    subscription.getClient().getId(), 
                    subscription.getReportType()
                );
            }
        }
        return subscriptionRepository.save(subscription);
    }
    
    /**
     * Delete subscription
     */
    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new SubscriptionNotFoundException(id);
        }
        subscriptionRepository.deleteById(id);
    }
    
}

