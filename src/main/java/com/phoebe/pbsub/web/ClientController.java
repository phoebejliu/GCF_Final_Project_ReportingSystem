package com.phoebe.pbsub.web;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Client Controller (Thymeleaf)
 */
@Controller
@RequestMapping("/clients")
public class ClientController {
    
    private final ClientService clientService;
    
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    /**
     * Client list page
     */
    @GetMapping
    public String list(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("totalClients", clientService.count());
        return "clients";
    }
    
    /**
     * Show create client form
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }
    
    /**
     * Create new client
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("client") Client client, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "client-form";
        }
        
        try {
            clientService.save(client);
            redirectAttributes.addFlashAttribute("successMessage", "Client created successfully!");
            return "redirect:/clients";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create client: " + e.getMessage());
            return "redirect:/clients/new";
        }
    }
    
    /**
     * Client detail page
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Client client = clientService.getWithSubscriptions(id);
        model.addAttribute("client", client);
        model.addAttribute("subscriptionCount", client.getSubscriptions().size());
        return "client-detail";
    }
    
    /**
     * Show edit client form
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Client client = clientService.get(id);
        model.addAttribute("client", client);
        return "client-form";
    }
    
    /**
     * Update client information
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, 
                        @Valid @ModelAttribute("client") Client client, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "client-form";
        }
        
        try {
            client.setId(id);
            clientService.save(client);
            redirectAttributes.addFlashAttribute("successMessage", "Client information updated successfully!");
            return "redirect:/clients/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update client: " + e.getMessage());
            return "redirect:/clients/" + id + "/edit";
        }
    }
    
    /**
     * Delete client
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clientService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Client deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete client: " + e.getMessage());
        }
        return "redirect:/clients";
    }
    
    /**
     * Search clients
     */
    @GetMapping("/search")
    public String search(@RequestParam String name, Model model) {
        List<Client> clients = clientService.searchByName(name);
        model.addAttribute("clients", clients);
        model.addAttribute("searchTerm", name);
        model.addAttribute("totalClients", clients.size());
        return "clients";
    }
}
