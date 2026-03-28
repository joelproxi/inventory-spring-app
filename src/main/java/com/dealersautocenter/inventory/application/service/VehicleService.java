package com.dealersautocenter.inventory.application.service;

import com.dealersautocenter.inventory.application.port.in.DealerUseCase;
import com.dealersautocenter.inventory.application.port.in.VehicleUseCase;
import com.dealersautocenter.inventory.application.port.out.TenantPort;
import com.dealersautocenter.inventory.application.port.out.VehicleRepositoryPort;
import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import com.dealersautocenter.inventory.domain.exception.ResourceNotFoundException;
import com.dealersautocenter.inventory.domain.model.Dealer;
import com.dealersautocenter.inventory.domain.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public class VehicleService implements VehicleUseCase {

    private final VehicleRepositoryPort vehicleRepositoryPort;
    private final DealerUseCase dealerUseCase;
    private final TenantPort tenantPort;

    public VehicleService(VehicleRepositoryPort vehicleRepositoryPort, DealerUseCase dealerUseCase, TenantPort tenantPort) {
        this.vehicleRepositoryPort = vehicleRepositoryPort;
        this.dealerUseCase = dealerUseCase;
        this.tenantPort = tenantPort;
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle, UUID dealerId) {
        String tenantId = tenantPort.getTenantId();
        Dealer dealer = dealerUseCase.getDealerEntity(dealerId);

        vehicle.setTenantId(tenantId);
        vehicle.setDealer(dealer);
        return vehicleRepositoryPort.save(vehicle);
    }

    @Override
    public Vehicle getVehicleById(UUID id) {
        String tenantId = tenantPort.getTenantId();
        return vehicleRepositoryPort.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
    }

    @Override
    public Page<Vehicle> getAllVehicles(String model, VehicleStatus status, BigDecimal priceMin, BigDecimal priceMax, SubscriptionType subscription, Pageable pageable) {
        String tenantId = tenantPort.getTenantId();
        if (subscription != null) {
            return vehicleRepositoryPort.findByTenantIdAndDealerSubscriptionType(tenantId, subscription, pageable);
        }
        return vehicleRepositoryPort.findAllWithFilters(tenantId, model, status, priceMin, priceMax, pageable);
    }

    @Override
    public Vehicle updateVehicle(UUID id, Vehicle updatedVehicle) {
        String tenantId = tenantPort.getTenantId();
        Vehicle existing = vehicleRepositoryPort.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));

        if (updatedVehicle.getModel() != null) existing.setModel(updatedVehicle.getModel());
        if (updatedVehicle.getPrice() != null) existing.setPrice(updatedVehicle.getPrice());
        if (updatedVehicle.getStatus() != null) existing.setStatus(updatedVehicle.getStatus());

        return vehicleRepositoryPort.save(existing);
    }

    @Override
    public void deleteVehicle(UUID id) {
        String tenantId = tenantPort.getTenantId();
        if (!vehicleRepositoryPort.existsByIdAndTenantId(id, tenantId)) {
            throw new ResourceNotFoundException("Vehicle not found: " + id);
        }
        vehicleRepositoryPort.deleteByIdAndTenantId(id, tenantId);
    }
}
