package com.phoebe.pbsub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Client Entity Class
 */
@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Client name cannot be empty")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    @Column(name = "primary_contact_email")
    private String primaryContactEmail;

    @Column(name = "ftp_path")
    private String ftpPath;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReportSubscription> subscriptions = new ArrayList<>();

    // Constructors
    public Client() {}

    public Client(String name, String primaryContactEmail, String ftpPath) {
        this.name = name;
        this.primaryContactEmail = primaryContactEmail;
        this.ftpPath = ftpPath;
    }

    // Getters and Setters
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

    public String getPrimaryContactEmail() {
        return primaryContactEmail;
    }

    public void setPrimaryContactEmail(String primaryContactEmail) {
        this.primaryContactEmail = primaryContactEmail;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", primaryContactEmail='" + primaryContactEmail + '\'' +
                ", ftpPath='" + ftpPath + '\'' +
                '}';
    }
}

