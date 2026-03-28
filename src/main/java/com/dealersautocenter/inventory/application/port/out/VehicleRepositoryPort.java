package com.dealersautocenter.inventory.application.port.out;

import com.dealersautocenter.inventory.domain.model.Vehicle;
import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepositoryPort {
    Vehicle save(Vehicle vehicle);
    Optional<Vehicle> findByIdAndTenantId(UUID id, String tenantId);
    boolean existsByIdAndTenantId(UUID id, String tenantId);
    void deleteByIdAndTenantId(UUID id, String tenantId);
    Page<Vehicle> findByTenantIdAndDealerSubscriptionType(String tenantId, SubscriptionType subType, Pageable pageable);
    Page<Vehicle> findAllWithFilters(String tenantId, String model, VehicleStatus status, BigDecimal priceMin, BigDecimal priceMax, Pageable pageable);
}
