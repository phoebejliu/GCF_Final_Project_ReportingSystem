package com.phoebe.pbsub.web;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.service.ClientService;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Simplified Subscription Controller - Beginner Friendly
 * 简化的订阅控制器 - 初学者友好版本
 */
@Controller
@RequestMapping("/clients/{clientId}/subscriptions")
public class SubscriptionController {
    
    private final ClientService clientService;
    private final ReportSubscriptionService subscriptionService;
    
    public SubscriptionController(ClientService clientService, ReportSubscriptionService subscriptionService) {
        this.clientService = clientService;
        this.subscriptionService = subscriptionService;
    }
    
    /**
     * Show new subscription form
     * 显示新建订阅表单
     */
    @GetMapping("/new")
    public String showNewSubscriptionForm(@PathVariable Long clientId, Model model) {
        Client client = clientService.get(clientId);
        ReportSubscription subscription = new ReportSubscription();
        subscription.setClient(client);
        
        model.addAttribute("client", client);
        model.addAttribute("subscription", subscription);
        return "subscription-form";
    }
    
    /**
     * Create new subscription
     * 创建新订阅
     */
    @PostMapping
    public String createSubscription(@PathVariable Long clientId, 
                                   @ModelAttribute ReportSubscription subscription,
                                   RedirectAttributes redirectAttributes) {
        try {
            Client client = clientService.get(clientId);
            subscription.setClient(client);
            
            subscriptionService.save(subscription);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription created successfully!");
            return "redirect:/clients/" + clientId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create subscription: " + e.getMessage());
            return "redirect:/clients/" + clientId + "/subscriptions/new";
        }
    }
    
    /**
     * Show edit subscription form
     * 显示编辑订阅表单
     */
    @GetMapping("/{id}/edit")
    public String showEditSubscriptionForm(@PathVariable Long clientId, 
                                         @PathVariable Long id, 
                                         Model model) {
        Client client = clientService.get(clientId);
        ReportSubscription subscription = subscriptionService.get(id);
        
        model.addAttribute("client", client);
        model.addAttribute("subscription", subscription);
        return "subscription-form";
    }
    
    /**
     * Update subscription
     * 更新订阅
     */
    @PostMapping("/{id}")
    public String updateSubscription(@PathVariable Long clientId, 
                                   @PathVariable Long id,
                                   @ModelAttribute ReportSubscription subscription,
                                   RedirectAttributes redirectAttributes) {
        try {
            Client client = clientService.get(clientId);
            subscription.setId(id);
            subscription.setClient(client);
            
            subscriptionService.save(subscription);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription updated successfully!");
            return "redirect:/clients/" + clientId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update subscription: " + e.getMessage());
            return "redirect:/clients/" + clientId + "/subscriptions/" + id + "/edit";
        }
    }
    
    /**
     * Delete subscription
     * 删除订阅
     */
    @PostMapping("/{id}/delete")
    public String deleteSubscription(@PathVariable Long clientId, 
                                   @PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            subscriptionService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete subscription: " + e.getMessage());
        }
        return "redirect:/clients/" + clientId;
    }
    
    /**
     * Toggle subscription active status
     * 切换订阅激活状态
     */
    @PostMapping("/{id}/toggle")
    public String toggleSubscriptionStatus(@PathVariable Long clientId, 
                                         @PathVariable Long id,
                                         RedirectAttributes redirectAttributes) {
        try {
            subscriptionService.toggleActive(id);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update subscription status: " + e.getMessage());
        }
        return "redirect:/clients/" + clientId;
    }
}