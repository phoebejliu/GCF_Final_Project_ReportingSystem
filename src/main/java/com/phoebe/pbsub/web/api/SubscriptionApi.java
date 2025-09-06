package com.phoebe.pbsub.web.api;

import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Simplified Subscription REST API
 */
@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscription Management", description = "Subscription related APIs")
public class SubscriptionApi {
    
    private final ReportSubscriptionService subscriptionService;
    
    public SubscriptionApi(ReportSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    
    /**
     * Get all subscriptions for a client
     */
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get client subscriptions", description = "Get all subscriptions for a specific client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscriptions"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ReportSubscription>> getClientSubscriptions(
            @Parameter(description = "Client ID", required = true) @PathVariable Long clientId) {
        List<ReportSubscription> subscriptions = subscriptionService.findByClientId(clientId);
        return ResponseEntity.ok(subscriptions);
    }
    
    /**
     * Get active subscriptions for a client
     */
    @GetMapping("/client/{clientId}/active")
    @Operation(summary = "Get active client subscriptions", description = "Get all active subscriptions for a specific client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved active subscriptions"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ReportSubscription>> getActiveClientSubscriptions(
            @Parameter(description = "Client ID", required = true) @PathVariable Long clientId) {
        List<ReportSubscription> subscriptions = subscriptionService.findActiveByClientId(clientId);
        return ResponseEntity.ok(subscriptions);
    }
    
    /**
     * Get subscription by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get subscription by ID", description = "Get detailed subscription information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscription"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ReportSubscription> getSubscription(
            @Parameter(description = "Subscription ID", required = true) @PathVariable Long id) {
        ReportSubscription subscription = subscriptionService.get(id);
        return ResponseEntity.ok(subscription);
    }
    
    /**
     * Create new subscription
     */
    @PostMapping
    @Operation(summary = "Create new subscription", description = "Create a new subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ReportSubscription> createSubscription(
            @Parameter(description = "Subscription information", required = true) @Valid @RequestBody ReportSubscription subscription) {
        ReportSubscription savedSubscription = subscriptionService.save(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubscription);
    }
    
    /**
     * Update subscription
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update subscription", description = "Update information for specified subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ReportSubscription> updateSubscription(
            @Parameter(description = "Subscription ID", required = true) @PathVariable Long id,
            @Parameter(description = "Subscription information", required = true) @Valid @RequestBody ReportSubscription subscription) {
        subscription.setId(id);
        ReportSubscription updatedSubscription = subscriptionService.save(subscription);
        return ResponseEntity.ok(updatedSubscription);
    }
    
    /**
     * Delete subscription
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete subscription", description = "Delete specified subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subscription deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteSubscription(
            @Parameter(description = "Subscription ID", required = true) @PathVariable Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Toggle subscription active status
     */
    @PostMapping("/{id}/toggle")
    @Operation(summary = "Toggle subscription status", description = "Activate or deactivate a subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ReportSubscription> toggleSubscriptionStatus(
            @Parameter(description = "Subscription ID", required = true) @PathVariable Long id) {
        ReportSubscription subscription = subscriptionService.toggleActive(id);
        return ResponseEntity.ok(subscription);
    }
}