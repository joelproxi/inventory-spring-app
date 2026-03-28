package com.dealersautocenter.inventory.dto;

import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDealerRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Subscription type is required")
    private SubscriptionType subscriptionType;
}
