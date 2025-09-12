package com.phoebe.pbsub.web.api;

import com.phoebe.pbsub.entity.ReportSubscription;
import com.phoebe.pbsub.entity.enums.ReportType;
import com.phoebe.pbsub.dto.ReportSubscriptionRequest;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import com.phoebe.pbsub.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Subscription REST API
 */
@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscription Management", description = "API for managing report subscriptions")
public class SubscriptionApi {
    
    private final ReportSubscriptionService subscriptionService;
    private final ClientService clientService;
    
    public SubscriptionApi(ReportSubscriptionService subscriptionService, ClientService clientService) {
        this.subscriptionService = subscriptionService;
        this.clientService = clientService;
    }
    
    /**
     * Get all subscriptions with pagination
     */
    @GetMapping
    @Operation(summary = "Get all subscriptions", description = "Retrieve a paginated list of all subscriptions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list of subscriptions")
    })
    public ResponseEntity<Page<ReportSubscription>> getAllSubscriptions(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<ReportSubscription> subscriptions = subscriptionService.findAll(pageable);
        return ResponseEntity.ok(subscriptions);
    }
    
    /**
     * Get subscriptions by client ID with pagination
     */
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get subscriptions by client ID", description = "Retrieve paginated subscriptions for a specific client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved client subscriptions")
    })
    public ResponseEntity<Page<ReportSubscription>> getSubscriptionsByClient(
            @PathVariable Long clientId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<ReportSubscription> subscriptions = subscriptionService.findByClientId(clientId, pageable);
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
    @Operation(summary = "Create new subscription", description = "Create a new report subscription with validation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Duplicate subscription")
    })
    public ResponseEntity<ReportSubscription> createSubscription(
            @Parameter(description = "Subscription data") @Valid @RequestBody ReportSubscriptionRequest request) {
        
        // Convert DTO to entity
        ReportSubscription subscription = new ReportSubscription();
        subscription.setClient(clientService.get(request.getClientId()));
        subscription.setReportType(ReportType.valueOf(request.getReportType()));
        subscription.setFrequency(request.getFrequency());
        subscription.setFormat(request.getFormat());
        subscription.setDeliveryMethod(request.getDeliveryMethod());
        
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