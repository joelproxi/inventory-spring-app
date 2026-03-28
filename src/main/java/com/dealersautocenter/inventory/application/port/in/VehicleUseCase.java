package com.dealersautocenter.inventory.application.port.in;

import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import com.dealersautocenter.inventory.domain.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface VehicleUseCase {
    Vehicle createVehicle(Vehicle vehicle, UUID dealerId);
    Vehicle getVehicleById(UUID id);
    Page<Vehicle> getAllVehicles(String model, VehicleStatus status, BigDecimal priceMin, BigDecimal priceMax, SubscriptionType subscription, Pageable pageable);
    Vehicle updateVehicle(UUID id, Vehicle vehicle);
    void deleteVehicle(UUID id);
}
