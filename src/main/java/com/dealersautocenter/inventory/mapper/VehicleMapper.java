package com.dealersautocenter.inventory.mapper;

import com.dealersautocenter.inventory.domain.entity.Vehicle;
import com.dealersautocenter.inventory.dto.VehicleResponse;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleResponse toResponse(Vehicle v) {
        return VehicleResponse.builder()
                .id(v.getId())
                .tenantId(v.getTenantId())
                .dealerId(v.getDealer().getId())
                .dealerName(v.getDealer().getName())
                .model(v.getModel())
                .price(v.getPrice())
                .status(v.getStatus())
                .createdAt(v.getCreatedAt())
                .updatedAt(v.getUpdatedAt())
                .build();
    }
}
