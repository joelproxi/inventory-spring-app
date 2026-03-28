package com.dealersautocenter.inventory.infrastructure.web.mapper;

import com.dealersautocenter.inventory.domain.model.Vehicle;
import com.dealersautocenter.inventory.infrastructure.web.dto.CreateVehicleRequest;
import com.dealersautocenter.inventory.infrastructure.web.dto.UpdateVehicleRequest;
import com.dealersautocenter.inventory.infrastructure.web.dto.VehicleResponse;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toDomain(CreateVehicleRequest req) {
        if (req == null) return null;
        Vehicle v = new Vehicle();
        v.setModel(req.getModel());
        v.setPrice(req.getPrice());
        v.setStatus(req.getStatus());
        return v;
    }

    public Vehicle toDomain(UpdateVehicleRequest req) {
        if (req == null) return null;
        Vehicle v = new Vehicle();
        v.setModel(req.getModel());
        v.setPrice(req.getPrice());
        v.setStatus(req.getStatus());
        return v;
    }

    public VehicleResponse toResponse(Vehicle v) {
        if (v == null) return null;
        return VehicleResponse.builder()
                .id(v.getId())
                .tenantId(v.getTenantId())
                .dealerId(v.getDealer() != null ? v.getDealer().getId() : null)
                .dealerName(v.getDealer() != null ? v.getDealer().getName() : null)
                .model(v.getModel())
                .price(v.getPrice())
                .status(v.getStatus())
                .createdAt(v.getCreatedAt())
                .updatedAt(v.getUpdatedAt())
                .build();
    }
}
