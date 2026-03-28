package com.dealersautocenter.inventory.dto;

import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDealerRequest {
    private String name;

    @Email(message = "Email must be valid")
    private String email;

    private SubscriptionType subscriptionType;
}
