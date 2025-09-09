package com.phoebe.pbsub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Simplified Report Subscription Entity - Beginner Friendly
 * 简化的报告订阅实体 - 初学者友好版本
 */
@Entity
@Table(name = "report_subscriptions")
public class ReportSubscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 关联到客户 - 多对一关系
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private Client client;

    // 报告类型 - 简单字符串，不使用复杂枚举
    @NotBlank(message = "Report type cannot be empty")
    @Column(name = "report_type", nullable = false)
    private String reportType;

    // 频率 - 简单字符串
    @NotBlank(message = "Frequency cannot be empty")
    @Column(nullable = false)
    private String frequency;

    // 格式 - 简单字符串
    @NotBlank(message = "Format cannot be empty")
    @Column(nullable = false)
    private String format;

    // 发送方式 - 简化为单个字符串
    @NotBlank(message = "Delivery method cannot be empty")
    @Column(name = "delivery_method", nullable = false)
    private String deliveryMethod;


    // 构造函数 - 初学者友好的简单构造函数
    public ReportSubscription() {}

    public ReportSubscription(Client client, String reportType, String frequency, 
                            String format, String deliveryMethod) {
        this.client = client;
        this.reportType = reportType;
        this.frequency = frequency;
        this.format = format;
        this.deliveryMethod = deliveryMethod;
    }

    // Getter和Setter方法 - 标准的Java Bean模式
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }


    // 简单的toString方法 - 用于调试
    @Override
    public String toString() {
        return "ReportSubscription{" +
                "id=" + id +
                ", reportType='" + reportType + '\'' +
                ", frequency='" + frequency + '\'' +
                ", format='" + format + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                '}';
    }
}

