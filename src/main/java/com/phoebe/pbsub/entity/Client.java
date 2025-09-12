package com.phoebe.pbsub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Client name - required field
    @NotBlank(message = "Client name cannot be empty")
    @Column(nullable = false)
    private String name;

    // Contact email - optional field, but if provided must be valid email format
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReportSubscription> subscriptions = new ArrayList<>();

    // Constructors
    public Client() {}

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getter and Setter methods - standard Java Bean pattern
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

    // toString method for debugging
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

