package com.phoebe.pbsub.config;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.service.ClientService;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Simplified Data Initializer - Beginner Friendly
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    private final ClientService clientService;
    private final ReportSubscriptionService subscriptionService;
    
    public DataInitializer(ClientService clientService, ReportSubscriptionService subscriptionService) {
        this.clientService = clientService;
        this.subscriptionService = subscriptionService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (clientService.count() > 0) {
            System.out.println("📊 Database already contains data, skipping initialization");
            return;
        }
        
        System.out.println("🚀 Starting demo data initialization...");
        
        // Create demo clients - using simplified constructors
        Client client1 = new Client("Goldman Sachs Investment Management", "gsim@gs.com");
        Client client2 = new Client("JPMorgan Asset Management", "jpmam@jpmorgan.com");
        Client client3 = new Client("BlackRock Funds", "blackrock@blackrock.com");
        Client client4 = new Client("Bridgewater Associates", "bridgewater@bridgewater.com");
        
        client1 = clientService.save(client1);
        client2 = clientService.save(client2);
        client3 = clientService.save(client3);
        client4 = clientService.save(client4);
        
        // Create subscriptions for client1 - using simplified string fields
        ReportSubscription sub1 = new ReportSubscription(client1, "Trade Confirmation", "Daily", "PDF", "Email");
        subscriptionService.save(sub1);
        
        ReportSubscription sub2 = new ReportSubscription(client1, "Daily P&L", "Daily", "CSV", "Email");
        subscriptionService.save(sub2);
        
        // Create subscriptions for client2
        ReportSubscription sub3 = new ReportSubscription(client2, "Monthly Statement", "Monthly", "PDF", "Email");
        subscriptionService.save(sub3);
        
        ReportSubscription sub4 = new ReportSubscription(client2, "Options Expiry", "Weekly", "CSV", "FTP");
        subscriptionService.save(sub4);
        
        // Create subscriptions for client3
        ReportSubscription sub5 = new ReportSubscription(client3, "Margin Call", "Daily", "PDF", "Email");
        subscriptionService.save(sub5);
        
        // Create subscriptions for client4
        ReportSubscription sub6 = new ReportSubscription(client4, "Trade Confirmation", "Daily", "PDF", "Email");
        subscriptionService.save(sub6);
        
        System.out.println("✅ Demo data initialization completed!");
        System.out.println("   📊 Created 4 clients");
        System.out.println("   📋 Created 6 subscriptions");
        System.out.println("   🌐 Visit http://localhost:8080/clients to view data");
    }
}
