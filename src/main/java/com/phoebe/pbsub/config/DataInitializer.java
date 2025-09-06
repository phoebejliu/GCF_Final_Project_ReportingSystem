package com.phoebe.pbsub.config;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.domain.enums.*;
import com.phoebe.pbsub.service.ClientService;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 数据初始化器 - 用于创建演示数据
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
        Client client1 = new Client("Goldman Sachs Investment Management", "gsim@gs.com", "/ftp/gsim");
        Client client2 = new Client("JPMorgan Asset Management", "jpmam@jpmorgan.com", "/ftp/jpmam");
        Client client3 = new Client("BlackRock Funds", "blackrock@blackrock.com", "/ftp/blackrock");
        Client client4 = new Client("Bridgewater Associates", "bridgewater@bridgewater.com", null);
        
        client1 = clientService.save(client1);
        client2 = clientService.save(client2);
        client3 = clientService.save(client3);
        client4 = clientService.save(client4);
        
        // Create subscriptions for client1
        ReportSubscription sub1 = new ReportSubscription();
        sub1.setClient(client1);
        sub1.setReportType(ReportType.TRADE_CONFIRM);
        sub1.setFrequency(Frequency.DAILY);
        sub1.setFormat(ReportFormat.PDF);
        sub1.setDeliveryMethods(Set.of(DeliveryMethod.EMAIL, DeliveryMethod.FTP));
        sub1.setActive(true);
        subscriptionService.save(sub1);
        
        ReportSubscription sub2 = new ReportSubscription();
        sub2.setClient(client1);
        sub2.setReportType(ReportType.DAILY_PNL);
        sub2.setFrequency(Frequency.DAILY);
        sub2.setFormat(ReportFormat.CSV);
        sub2.setDeliveryMethods(Set.of(DeliveryMethod.EMAIL));
        sub2.setActive(true);
        subscriptionService.save(sub2);
        
        // Create subscriptions for client2
        ReportSubscription sub3 = new ReportSubscription();
        sub3.setClient(client2);
        sub3.setReportType(ReportType.STATEMENT);
        sub3.setFrequency(Frequency.MONTHLY);
        sub3.setFormat(ReportFormat.PDF);
        sub3.setDeliveryMethods(Set.of(DeliveryMethod.EMAIL, DeliveryMethod.UI));
        sub3.setActive(true);
        subscriptionService.save(sub3);
        
        ReportSubscription sub4 = new ReportSubscription();
        sub4.setClient(client2);
        sub4.setReportType(ReportType.OPTIONS_EXPIRY);
        sub4.setFrequency(Frequency.WEEKLY);
        sub4.setFormat(ReportFormat.CSV);
        sub4.setDeliveryMethods(Set.of(DeliveryMethod.FTP));
        sub4.setOverrideFtpPath("/custom/jpmam/options");
        sub4.setActive(true);
        subscriptionService.save(sub4);
        
        // Create subscriptions for client3
        ReportSubscription sub5 = new ReportSubscription();
        sub5.setClient(client3);
        sub5.setReportType(ReportType.MARGIN_CALL);
        sub5.setFrequency(Frequency.DAILY);
        sub5.setFormat(ReportFormat.PDF);
        sub5.setDeliveryMethods(Set.of(DeliveryMethod.EMAIL, DeliveryMethod.UI));
        sub5.setOverrideEmail("risk@blackrock.com");
        sub5.setActive(true);
        subscriptionService.save(sub5);
        
        // Create subscription for client4 (inactive status)
        ReportSubscription sub6 = new ReportSubscription();
        sub6.setClient(client4);
        sub6.setReportType(ReportType.TRADE_CONFIRM);
        sub6.setFrequency(Frequency.DAILY);
        sub6.setFormat(ReportFormat.PDF);
        sub6.setDeliveryMethods(Set.of(DeliveryMethod.EMAIL));
        sub6.setActive(false);
        subscriptionService.save(sub6);
        
        System.out.println("✅ Demo data initialization completed!");
        System.out.println("   📊 Created 4 clients");
        System.out.println("   📋 Created 6 subscriptions");
        System.out.println("   🌐 Visit http://localhost:8080/clients to view data");
    }
}
