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

/**
 * Subscription REST API - Beginner Friendly Version
 * 订阅REST API - 初学者友好版本
 */
@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "订阅管理", description = "报告订阅的CRUD操作API")
public class SubscriptionApi {
    
    private final ReportSubscriptionService subscriptionService;
    
    public SubscriptionApi(ReportSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    
    /**
     * Get subscriptions by client ID
     * 根据客户ID获取订阅
     */
    @Operation(summary = "根据客户ID获取订阅", description = "获取指定客户的所有报告订阅")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取订阅列表")
    })
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReportSubscription>> getSubscriptionsByClient(
            @Parameter(description = "客户ID", required = true) @PathVariable Long clientId) {
        List<ReportSubscription> subscriptions = subscriptionService.findByClientId(clientId);
        return ResponseEntity.ok(subscriptions);
    }
    
    /**
     * Get subscription by ID
     * 根据ID获取订阅
     */
    @Operation(summary = "根据ID获取订阅", description = "通过订阅ID获取特定订阅的详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取订阅信息"),
            @ApiResponse(responseCode = "404", description = "订阅不存在")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReportSubscription> getSubscription(
            @Parameter(description = "订阅ID", required = true) @PathVariable Long id) {
        ReportSubscription subscription = subscriptionService.get(id);
        return ResponseEntity.ok(subscription);
    }
    
    
    /**
     * Create new subscription
     * 创建新订阅
     */
    @Operation(summary = "创建新订阅", description = "为客户创建新的报告订阅")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "订阅创建成功"),
            @ApiResponse(responseCode = "400", description = "请求数据无效")
    })
    @PostMapping
    public ResponseEntity<ReportSubscription> createSubscription(
            @Parameter(description = "订阅信息", required = true) @Valid @RequestBody ReportSubscription subscription) {
        ReportSubscription savedSubscription = subscriptionService.save(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubscription);
    }
    
    /**
     * Update subscription
     * 更新订阅
     */
    @Operation(summary = "更新订阅", description = "更新指定订阅的详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "订阅更新成功"),
            @ApiResponse(responseCode = "400", description = "请求数据无效"),
            @ApiResponse(responseCode = "404", description = "订阅不存在")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReportSubscription> updateSubscription(
            @Parameter(description = "订阅ID", required = true) @PathVariable Long id,
            @Parameter(description = "更新的订阅信息", required = true) @Valid @RequestBody ReportSubscription subscription) {
        subscription.setId(id);
        ReportSubscription updatedSubscription = subscriptionService.save(subscription);
        return ResponseEntity.ok(updatedSubscription);
    }
    
    /**
     * Delete subscription
     * 删除订阅
     */
    @Operation(summary = "删除订阅", description = "从系统中删除指定的订阅记录")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "订阅删除成功"),
            @ApiResponse(responseCode = "404", description = "订阅不存在")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(
            @Parameter(description = "订阅ID", required = true) @PathVariable Long id) {
        subscriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Toggle subscription active status
     * 切换订阅激活状态
     */
    @Operation(summary = "切换订阅状态", description = "激活或停用指定的订阅")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "订阅状态切换成功"),
            @ApiResponse(responseCode = "404", description = "订阅不存在")
    })
    @PostMapping("/{id}/toggle")
    public ResponseEntity<ReportSubscription> toggleSubscriptionStatus(
            @Parameter(description = "订阅ID", required = true) @PathVariable Long id) {
        subscriptionService.toggleActive(id);
        ReportSubscription subscription = subscriptionService.get(id);
        return ResponseEntity.ok(subscription);
    }
    
}