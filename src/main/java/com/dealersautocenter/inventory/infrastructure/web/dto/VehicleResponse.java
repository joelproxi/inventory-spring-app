package com.dealersautocenter.inventory.infrastructure.web.dto;

import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VehicleResponse {
    private UUID id;
    private String tenantId;
    private UUID dealerId;
    private String dealerName;
    private String model;
    private BigDecimal price;
    private VehicleStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
