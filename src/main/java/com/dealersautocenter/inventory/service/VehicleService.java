package com.dealersautocenter.inventory.service;

import com.dealersautocenter.inventory.domain.entity.Dealer;
import com.dealersautocenter.inventory.domain.entity.Vehicle;
import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import com.dealersautocenter.inventory.dto.*;
import com.dealersautocenter.inventory.exception.ResourceNotFoundException;
import com.dealersautocenter.inventory.mapper.VehicleMapper;
import com.dealersautocenter.inventory.repository.VehicleRepository;
import com.dealersautocenter.inventory.repository.VehicleSpecification;
import com.dealersautocenter.inventory.security.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DealerService dealerService;
    private final VehicleMapper vehicleMapper;

    public VehicleResponse create(CreateVehicleRequest req) {
        String tenantId = TenantContext.getTenantId();

        // Vérifie que le dealer existe et appartient au même tenant
        Dealer dealer = dealerService.getDealerEntity(req.getDealerId(), tenantId);

        Vehicle vehicle = Vehicle.builder()
                .tenantId(tenantId)
                .dealer(dealer)
                .model(req.getModel())
                .price(req.getPrice())
                .status(req.getStatus())
                .build();

        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Transactional(readOnly = true)
    public VehicleResponse getById(UUID id) {
        String tenantId = TenantContext.getTenantId();
        Vehicle vehicle = vehicleRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehicle not found: " + id));
        return vehicleMapper.toResponse(vehicle);
    }

    @Transactional(readOnly = true)
    public Page<VehicleResponse> getAll(String model,
                                         VehicleStatus status,
                                         BigDecimal priceMin,
                                         BigDecimal priceMax,
                                         SubscriptionType subscription,
                                         Pageable pageable) {
        String tenantId = TenantContext.getTenantId();

        // Filtre par subscription du dealer
        if (subscription != null) {
            return vehicleRepository
                    .findByTenantIdAndDealerSubscriptionType(
                            tenantId, subscription, pageable)
                    .map(vehicleMapper::toResponse);
        }

        // Filtres dynamiques
        return vehicleRepository
                .findAll(VehicleSpecification.withFilters(
                        tenantId, model, status, priceMin, priceMax), pageable)
                .map(vehicleMapper::toResponse);
    }

    public VehicleResponse update(UUID id, UpdateVehicleRequest req) {
        String tenantId = TenantContext.getTenantId();

        Vehicle vehicle = vehicleRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehicle not found: " + id));

        if (req.getModel() != null)  vehicle.setModel(req.getModel());
        if (req.getPrice() != null)  vehicle.setPrice(req.getPrice());
        if (req.getStatus() != null) vehicle.setStatus(req.getStatus());

        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    public void delete(UUID id) {
        String tenantId = TenantContext.getTenantId();
        if (!vehicleRepository.existsByIdAndTenantId(id, tenantId)) {
            throw new ResourceNotFoundException("Vehicle not found: " + id);
        }
        vehicleRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
