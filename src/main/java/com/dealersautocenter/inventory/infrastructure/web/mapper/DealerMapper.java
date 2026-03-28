package com.dealersautocenter.inventory.infrastructure.web.mapper;

import com.dealersautocenter.inventory.domain.model.Dealer;
import com.dealersautocenter.inventory.infrastructure.web.dto.CreateDealerRequest;
import com.dealersautocenter.inventory.infrastructure.web.dto.DealerResponse;
import com.dealersautocenter.inventory.infrastructure.web.dto.UpdateDealerRequest;
import org.springframework.stereotype.Component;

@Component
public class DealerMapper {

    public Dealer toDomain(CreateDealerRequest req) {
        if (req == null) return null;
        Dealer d = new Dealer();
        d.setName(req.getName());
        d.setEmail(req.getEmail());
        d.setSubscriptionType(req.getSubscriptionType());
        return d;
    }

    public Dealer toDomain(UpdateDealerRequest req) {
        if (req == null) return null;
        Dealer d = new Dealer();
        d.setName(req.getName());
        d.setEmail(req.getEmail());
        d.setSubscriptionType(req.getSubscriptionType());
        return d;
    }

    public DealerResponse toResponse(Dealer d) {
        if (d == null) return null;
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
