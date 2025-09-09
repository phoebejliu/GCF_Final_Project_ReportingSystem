package com.phoebe.pbsub.config;

import com.phoebe.pbsub.domain.Client;
import com.phoebe.pbsub.domain.ReportSubscription;
import com.phoebe.pbsub.service.ClientService;
import com.phoebe.pbsub.service.ReportSubscriptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Simplified Data Initializer - Beginner Friendly
 * 简化的数据初始化器 - 初学者友好版本
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
        // 检查数据是否已存在
        if (clientService.count() > 0) {
            System.out.println("📊 Database already contains data, skipping initialization");
            return;
        }
        
        System.out.println("🚀 Starting demo data initialization...");
        
        // 创建演示客户 - 使用简化的构造函数
        Client client1 = new Client("Goldman Sachs Investment Management", "gsim@gs.com");
        Client client2 = new Client("JPMorgan Asset Management", "jpmam@jpmorgan.com");
        Client client3 = new Client("BlackRock Funds", "blackrock@blackrock.com");
        Client client4 = new Client("Bridgewater Associates", "bridgewater@bridgewater.com");
        
        client1 = clientService.save(client1);
        client2 = clientService.save(client2);
        client3 = clientService.save(client3);
        client4 = clientService.save(client4);
        
        // 为客户1创建订阅 - 使用简化的字符串字段
        ReportSubscription sub1 = new ReportSubscription(client1, "Trade Confirmation", "Daily", "PDF", "Email");
        subscriptionService.save(sub1);
        
        ReportSubscription sub2 = new ReportSubscription(client1, "Daily P&L", "Daily", "CSV", "Email");
        subscriptionService.save(sub2);
        
        // 为客户2创建订阅
        ReportSubscription sub3 = new ReportSubscription(client2, "Monthly Statement", "Monthly", "PDF", "Email");
        subscriptionService.save(sub3);
        
        ReportSubscription sub4 = new ReportSubscription(client2, "Options Expiry", "Weekly", "CSV", "FTP");
        subscriptionService.save(sub4);
        
        // 为客户3创建订阅
        ReportSubscription sub5 = new ReportSubscription(client3, "Margin Call", "Daily", "PDF", "Email");
        subscriptionService.save(sub5);
        
        // 为客户4创建订阅（非激活状态）
        ReportSubscription sub6 = new ReportSubscription(client4, "Trade Confirmation", "Daily", "PDF", "Email");
        sub6.setActive(false); // 设置为非激活状态
        subscriptionService.save(sub6);
        
        System.out.println("✅ Demo data initialization completed!");
        System.out.println("   📊 Created 4 clients");
        System.out.println("   📋 Created 6 subscriptions");
        System.out.println("   🌐 Visit http://localhost:8080/clients to view data");
    }
}
