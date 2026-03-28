package com.dealersautocenter.inventory.infrastructure.persistence.mapper;

import com.dealersautocenter.inventory.domain.model.Vehicle;
import com.dealersautocenter.inventory.infrastructure.persistence.entity.VehicleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class VehiclePersistenceMapper {

    private final DealerPersistenceMapper dealerPersistenceMapper;

    public VehiclePersistenceMapper(DealerPersistenceMapper dealerPersistenceMapper) {
        this.dealerPersistenceMapper = dealerPersistenceMapper;
    }

    public VehicleJpaEntity toJpaEntity(Vehicle domain) {
        if (domain == null) return null;
        return VehicleJpaEntity.builder()
                .id(domain.getId())
                .tenantId(domain.getTenantId())
                .dealer(dealerPersistenceMapper.toJpaEntity(domain.getDealer()))
                .model(domain.getModel())
                .price(domain.getPrice())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public Vehicle toDomain(VehicleJpaEntity entity) {
        if (entity == null) return null;
        return new Vehicle(
                entity.getId(),
                entity.getTenantId(),
                dealerPersistenceMapper.toDomain(entity.getDealer()),
                entity.getModel(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
