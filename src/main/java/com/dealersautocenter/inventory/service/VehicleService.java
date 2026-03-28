package com.dealersautocenter.inventory.service;

import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import com.dealersautocenter.inventory.dto.CreateVehicleRequest;
import com.dealersautocenter.inventory.dto.UpdateVehicleRequest;
import com.dealersautocenter.inventory.dto.VehicleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface VehicleService {
    VehicleResponse create(CreateVehicleRequest req);

    VehicleResponse getById(UUID id);

    Page<VehicleResponse> getAll(String model, VehicleStatus status, BigDecimal priceMin, BigDecimal priceMax,
            SubscriptionType subscription, Pageable pageable);

    VehicleResponse update(UUID id, UpdateVehicleRequest req);

    void delete(UUID id);
}
