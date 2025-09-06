package com.phoebe.pbsub.service;

import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.domain.enums.ReportType;
import com.phoebe.pbsub.repo.ReportSubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Simplified Report Subscription Service
 */
@Service
@Transactional
public class ReportSubscriptionService {
    
    private final ReportSubscriptionRepository subscriptionRepository;
    
    public ReportSubscriptionService(ReportSubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
    
    /**
     * Find all subscriptions by client ID
     */
    @Transactional(readOnly = true)
    public List<ReportSubscription> findByClientId(Long clientId) {
        return subscriptionRepository.findByClientId(clientId);
    }
    
    /**
     * Find active subscriptions by client ID
     */
    @Transactional(readOnly = true)
    public List<ReportSubscription> findActiveByClientId(Long clientId) {
        return subscriptionRepository.findByClientIdAndActiveTrue(clientId);
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
        // Check if subscription with same type already exists
        Optional<ReportSubscription> existing = subscriptionRepository
                .findByClientIdAndReportType(subscription.getClient().getId(), subscription.getReportType());
        
        if (existing.isPresent() && !existing.get().getId().equals(subscription.getId())) {
            throw new IllegalArgumentException("Client already has a subscription for this report type");
        }
        
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
    
    /**
     * Toggle subscription active status
     */
    public ReportSubscription toggleActive(Long id) {
        ReportSubscription subscription = get(id);
        subscription.setActive(!subscription.isActive());
        return subscriptionRepository.save(subscription);
    }
}

