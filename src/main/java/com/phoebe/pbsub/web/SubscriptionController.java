package com.phoebe.pbsub.web;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.domain.enums.*;
import com.phoebe.pbsub.service.ClientService;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

/**
 * Subscription Controller (Thymeleaf)
 */
@Controller
@RequestMapping("/clients/{clientId}/subscriptions")
public class SubscriptionController {
    
    private final ReportSubscriptionService subscriptionService;
    private final ClientService clientService;
    
    public SubscriptionController(ReportSubscriptionService subscriptionService, 
                                ClientService clientService) {
        this.subscriptionService = subscriptionService;
        this.clientService = clientService;
    }
    
    /**
     * Show create subscription form
     */
    @GetMapping("/new")
    public String createForm(@PathVariable Long clientId, Model model) {
        Client client = clientService.get(clientId);
        ReportSubscription subscription = new ReportSubscription();
        subscription.setClient(client);
        
        model.addAttribute("client", client);
        model.addAttribute("subscription", subscription);
        model.addAttribute("reportTypes", ReportType.values());
        model.addAttribute("frequencies", Frequency.values());
        model.addAttribute("formats", ReportFormat.values());
        model.addAttribute("deliveryMethods", DeliveryMethod.values());
        
        return "subscription-form";
    }
    
    /**
     * Create new subscription
     */
    @PostMapping
    public String create(@PathVariable Long clientId,
                        @Valid @ModelAttribute("subscription") ReportSubscription subscription,
                        BindingResult result,
                        @RequestParam(value = "deliveryMethods", required = false) String[] deliveryMethodStrings,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        
        if (result.hasErrors()) {
            Client client = clientService.get(clientId);
            model.addAttribute("client", client);
            model.addAttribute("reportTypes", ReportType.values());
            model.addAttribute("frequencies", Frequency.values());
            model.addAttribute("formats", ReportFormat.values());
            model.addAttribute("deliveryMethods", DeliveryMethod.values());
            return "subscription-form";
        }
        
        try {
            // Set client
            Client client = clientService.get(clientId);
            subscription.setClient(client);
            
            // Handle delivery methods
            if (deliveryMethodStrings != null && deliveryMethodStrings.length > 0) {
                Set<DeliveryMethod> deliveryMethods = new HashSet<>();
                for (String method : deliveryMethodStrings) {
                    deliveryMethods.add(DeliveryMethod.valueOf(method));
                }
                subscription.setDeliveryMethods(deliveryMethods);
            }
            
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
     */
    @GetMapping("/{subscriptionId}/edit")
    public String editForm(@PathVariable Long clientId, 
                          @PathVariable Long subscriptionId, 
                          Model model) {
        Client client = clientService.get(clientId);
        ReportSubscription subscription = subscriptionService.get(subscriptionId);
        
        model.addAttribute("client", client);
        model.addAttribute("subscription", subscription);
        model.addAttribute("reportTypes", ReportType.values());
        model.addAttribute("frequencies", Frequency.values());
        model.addAttribute("formats", ReportFormat.values());
        model.addAttribute("deliveryMethods", DeliveryMethod.values());
        
        return "subscription-form";
    }
    
    /**
     * Update subscription
     */
    @PostMapping("/{subscriptionId}")
    public String update(@PathVariable Long clientId,
                        @PathVariable Long subscriptionId,
                        @Valid @ModelAttribute("subscription") ReportSubscription subscription,
                        BindingResult result,
                        @RequestParam(value = "deliveryMethods", required = false) String[] deliveryMethodStrings,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        
        if (result.hasErrors()) {
            Client client = clientService.get(clientId);
            model.addAttribute("client", client);
            model.addAttribute("reportTypes", ReportType.values());
            model.addAttribute("frequencies", Frequency.values());
            model.addAttribute("formats", ReportFormat.values());
            model.addAttribute("deliveryMethods", DeliveryMethod.values());
            return "subscription-form";
        }
        
        try {
            // Set ID and client
            subscription.setId(subscriptionId);
            Client client = clientService.get(clientId);
            subscription.setClient(client);
            
            // Handle delivery methods
            if (deliveryMethodStrings != null && deliveryMethodStrings.length > 0) {
                Set<DeliveryMethod> deliveryMethods = new HashSet<>();
                for (String method : deliveryMethodStrings) {
                    deliveryMethods.add(DeliveryMethod.valueOf(method));
                }
                subscription.setDeliveryMethods(deliveryMethods);
            }
            
            subscriptionService.save(subscription);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription updated successfully!");
            return "redirect:/clients/" + clientId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update subscription: " + e.getMessage());
            return "redirect:/clients/" + clientId + "/subscriptions/" + subscriptionId + "/edit";
        }
    }
    
    /**
     * Delete subscription
     */
    @PostMapping("/{subscriptionId}/delete")
    public String delete(@PathVariable Long clientId, 
                        @PathVariable Long subscriptionId, 
                        RedirectAttributes redirectAttributes) {
        try {
            subscriptionService.delete(subscriptionId);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete subscription: " + e.getMessage());
        }
        return "redirect:/clients/" + clientId;
    }
    
    /**
     * Toggle subscription status (activate/deactivate)
     */
    @PostMapping("/{subscriptionId}/toggle")
    public String toggleActive(@PathVariable Long clientId, 
                              @PathVariable Long subscriptionId, 
                              RedirectAttributes redirectAttributes) {
        try {
            subscriptionService.toggleActive(subscriptionId);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update subscription status: " + e.getMessage());
        }
        return "redirect:/clients/" + clientId;
    }
}
