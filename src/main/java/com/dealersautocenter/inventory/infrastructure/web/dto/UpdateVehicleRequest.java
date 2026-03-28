package com.dealersautocenter.inventory.infrastructure.web.dto;

import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateVehicleRequest {
    private String model;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be > 0")
    private BigDecimal price;

    private VehicleStatus status;
}
