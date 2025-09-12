package com.phoebe.pbsub.config;

import com.phoebe.pbsub.entity.Client;
import com.phoebe.pbsub.entity.ReportSubscription;
import com.phoebe.pbsub.entity.enums.ReportType;
import com.phoebe.pbsub.entity.enums.Frequency;
import com.phoebe.pbsub.entity.enums.ReportFormat;
import com.phoebe.pbsub.entity.enums.DeliveryMethod;
import com.phoebe.pbsub.service.ClientService;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Data Initializer
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
        
        // Create demo clients
        Client client1 = new Client("Goldman Sachs Investment Management", "gsim@gs.com");
        Client client2 = new Client("JPMorgan Asset Management", "jpmam@jpmorgan.com");
        Client client3 = new Client("BlackRock Funds", "blackrock@blackrock.com");
        Client client4 = new Client("Bridgewater Associates", "bridgewater@bridgewater.com");
        
        client1 = clientService.save(client1);
        client2 = clientService.save(client2);
        client3 = clientService.save(client3);
        client4 = clientService.save(client4);
        
        // Create subscriptions for client1 - using enum types for type safety
        ReportSubscription sub1 = new ReportSubscription(client1, ReportType.TRADE_CONFIRM, Frequency.DAILY, ReportFormat.PDF, DeliveryMethod.EMAIL);
        subscriptionService.save(sub1);
        
        ReportSubscription sub2 = new ReportSubscription(client1, ReportType.DAILY_PNL, Frequency.DAILY, ReportFormat.CSV, DeliveryMethod.EMAIL);
        subscriptionService.save(sub2);
        
        // Create subscriptions for client2
        ReportSubscription sub3 = new ReportSubscription(client2, ReportType.STATEMENT, Frequency.MONTHLY, ReportFormat.PDF, DeliveryMethod.EMAIL);
        subscriptionService.save(sub3);
        
        ReportSubscription sub4 = new ReportSubscription(client2, ReportType.OPTIONS_EXPIRY, Frequency.WEEKLY, ReportFormat.CSV, DeliveryMethod.FTP);
        subscriptionService.save(sub4);
        
        // Create subscriptions for client3
        ReportSubscription sub5 = new ReportSubscription(client3, ReportType.MARGIN_CALL, Frequency.DAILY, ReportFormat.PDF, DeliveryMethod.EMAIL);
        subscriptionService.save(sub5);
        
        // Create subscriptions for client4
        ReportSubscription sub6 = new ReportSubscription(client4, ReportType.TRADE_CONFIRM, Frequency.DAILY, ReportFormat.PDF, DeliveryMethod.EMAIL);
        subscriptionService.save(sub6);
        
        System.out.println("Demo data initialization completed!");
        System.out.println("Created 4 clients");
        System.out.println("Created 6 subscriptions");
        System.out.println("Visit http://localhost:8080/clients to view data");
    }
}
