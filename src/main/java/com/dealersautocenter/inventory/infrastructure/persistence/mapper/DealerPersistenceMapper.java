package com.dealersautocenter.inventory.infrastructure.persistence.mapper;

import com.dealersautocenter.inventory.domain.model.Dealer;
import com.dealersautocenter.inventory.infrastructure.persistence.entity.DealerJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class DealerPersistenceMapper {

    public DealerJpaEntity toJpaEntity(Dealer domain) {
        if (domain == null) return null;
        return DealerJpaEntity.builder()
                .id(domain.getId())
                .tenantId(domain.getTenantId())
                .name(domain.getName())
                .email(domain.getEmail())
                .subscriptionType(domain.getSubscriptionType())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public Dealer toDomain(DealerJpaEntity entity) {
        if (entity == null) return null;
        return new Dealer(
                entity.getId(),
                entity.getTenantId(),
                entity.getName(),
                entity.getEmail(),
                entity.getSubscriptionType(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
