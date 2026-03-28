package com.dealersautocenter.inventory.dto;

import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DealerResponse {
    private UUID id;
    private String tenantId;
    private String name;
    private String email;
    private SubscriptionType subscriptionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
