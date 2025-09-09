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
 * 订阅REST API - 初学者友好版本
 */
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionApi {
    
    private final ReportSubscriptionService subscriptionService;
    
    public SubscriptionApi(ReportSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    
    /**
     * Get subscriptions by client ID
     * 根据客户ID获取订阅
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReportSubscription>> getSubscriptionsByClient(
            @PathVariable Long clientId) {
        List<ReportSubscription> subscriptions = subscriptionService.findByClientId(clientId);
        return ResponseEntity.ok(subscriptions);
    }
    
    /**
     * Get subscription by ID
     * 根据ID获取订阅
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReportSubscription> getSubscription(
            @PathVariable Long id) {
        ReportSubscription subscription = subscriptionService.get(id);
        return ResponseEntity.ok(subscription);
    }
    
    
    /**
     * Create new subscription
     * 创建新订阅
     */
    @PostMapping
    public ResponseEntity<ReportSubscription> createSubscription(
            @Valid @RequestBody ReportSubscription subscription) {
        ReportSubscription savedSubscription = subscriptionService.save(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubscription);
    }
    
    /**
     * Update subscription
     * 更新订阅
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
     * 删除订阅
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
}