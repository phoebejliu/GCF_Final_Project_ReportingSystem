package com.phoebe.pbsub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Simplified Client Entity - Beginner Friendly
 * 简化的客户实体 - 初学者友好版本
 */
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 客户名称 - 必填字段
    @NotBlank(message = "Client name cannot be empty")
    @Column(nullable = false)
    private String name;

    // 联系邮箱 - 可选字段，但如果有值必须是有效邮箱格式
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReportSubscription> subscriptions = new ArrayList<>();

    // 构造函数 - 初学者友好的简单构造函数
    public Client() {}

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getter和Setter方法 - 标准的Java Bean模式
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ReportSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<ReportSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    // Helper methods
    public void addSubscription(ReportSubscription subscription) {
        subscriptions.add(subscription);
        subscription.setClient(this);
    }

    public void removeSubscription(ReportSubscription subscription) {
        subscriptions.remove(subscription);
        subscription.setClient(null);
    }

    // 简单的toString方法 - 用于调试
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

