package com.dealersautocenter.inventory.dto;

import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateVehicleRequest {
    @NotNull(message = "Dealer ID is required")
    private UUID dealerId;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be > 0")
    private BigDecimal price;

    @NotNull(message = "Status is required")
    private VehicleStatus status;
}
