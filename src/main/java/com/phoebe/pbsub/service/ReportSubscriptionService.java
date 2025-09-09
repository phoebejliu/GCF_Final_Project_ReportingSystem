package com.phoebe.pbsub.service;

import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.repo.ReportSubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Simplified Report Subscription Service - Beginner Friendly
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
     * Find all subscriptions
     */
    @Transactional(readOnly = true)
    public List<ReportSubscription> findAll() {
        return subscriptionRepository.findAll();
    }
    
    /**
     * Find all subscriptions by client ID
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
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found, ID: " + id));
    }
    
    /**
     * Save subscription
     */
    public ReportSubscription save(ReportSubscription subscription) {
        return subscriptionRepository.save(subscription);
    }
    
    /**
     * Delete subscription
     */
    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new IllegalArgumentException("Subscription not found, ID: " + id);
        }
        subscriptionRepository.deleteById(id);
    }
    
}

