package com.dealersautocenter.inventory.domain.model;

import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import java.time.LocalDateTime;
import java.util.UUID;

public class Dealer {

    private UUID id;
    private String tenantId;
    private String name;
    private String email;
    private SubscriptionType subscriptionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Dealer() {}

    public Dealer(UUID id, String tenantId, String name, String email, SubscriptionType subscriptionType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.tenantId = tenantId;
        this.name = name;
        this.email = email;
        this.subscriptionType = subscriptionType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public SubscriptionType getSubscriptionType() { return subscriptionType; }
    public void setSubscriptionType(SubscriptionType subscriptionType) { this.subscriptionType = subscriptionType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
