package com.phoebe.pbsub.web.api;

import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Subscription REST API - Beginner Friendly Version
 */
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionApi {
    
    private final ReportSubscriptionService subscriptionService;
    
    public SubscriptionApi(ReportSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    
    /**
     * Get all subscriptions
     */
    @GetMapping
    public ResponseEntity<List<ReportSubscription>> getAllSubscriptions() {
        List<ReportSubscription> subscriptions = subscriptionService.findAll();
        return ResponseEntity.ok(subscriptions);
    }
    
    /**
     * Get subscriptions by client ID
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReportSubscription>> getSubscriptionsByClient(
            @PathVariable Long clientId) {
        List<ReportSubscription> subscriptions = subscriptionService.findByClientId(clientId);
        return ResponseEntity.ok(subscriptions);
    }
    
    /**
     * Get subscription by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReportSubscription> getSubscription(
            @PathVariable Long id) {
        ReportSubscription subscription = subscriptionService.get(id);
        return ResponseEntity.ok(subscription);
    }
    
    
    /**
     * Create new subscription
     */
    @PostMapping
    public ResponseEntity<ReportSubscription> createSubscription(
            @Valid @RequestBody ReportSubscription subscription) {
        ReportSubscription savedSubscription = subscriptionService.save(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubscription);
    }
    
    /**
     * Update subscription
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReportSubscription> updateSubscription(
            @PathVariable Long id,
            @Valid @RequestBody ReportSubscription subscription) {
        subscription.setId(id);
        ReportSubscription updatedSubscription = subscriptionService.save(subscription);
        return ResponseEntity.ok(updatedSubscription);
    }
    
    /**
     * Delete subscription
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
}