package com.dealersautocenter.inventory.mapper;

import com.dealersautocenter.inventory.domain.entity.Dealer;
import com.dealersautocenter.inventory.dto.DealerResponse;
import org.springframework.stereotype.Component;

@Component
public class DealerMapper {

    public DealerResponse toResponse(Dealer d) {
        return DealerResponse.builder()
                .id(d.getId())
                .tenantId(d.getTenantId())
                .name(d.getName())
                .email(d.getEmail())
                .subscriptionType(d.getSubscriptionType())
                .createdAt(d.getCreatedAt())
                .updatedAt(d.getUpdatedAt())
                .build();
    }
}
