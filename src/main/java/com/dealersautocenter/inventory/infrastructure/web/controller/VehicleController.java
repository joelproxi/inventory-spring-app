package com.dealersautocenter.inventory.infrastructure.web.controller;

import com.dealersautocenter.inventory.application.port.in.VehicleUseCase;
import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import com.dealersautocenter.inventory.domain.model.Vehicle;
import com.dealersautocenter.inventory.infrastructure.web.dto.CreateVehicleRequest;
import com.dealersautocenter.inventory.infrastructure.web.dto.UpdateVehicleRequest;
import com.dealersautocenter.inventory.infrastructure.web.dto.VehicleResponse;
import com.dealersautocenter.inventory.infrastructure.web.mapper.VehicleMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicles", description = "Operations related to vehicles and inventory")
public class VehicleController {

    private final VehicleUseCase vehicleUseCase;
    private final VehicleMapper vehicleMapper;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody CreateVehicleRequest req) {
        Vehicle vehicle = vehicleMapper.toDomain(req);
        Vehicle saved = vehicleUseCase.createVehicle(vehicle, req.getDealerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getById(@PathVariable UUID id) {
        Vehicle vehicle = vehicleUseCase.getVehicleById(id);
        return ResponseEntity.ok(vehicleMapper.toResponse(vehicle));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getAll(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) SubscriptionType subscription,
            @PageableDefault(size = 20, sort = "createdAt,desc") Pageable pageable) {
        
        Page<Vehicle> vehicles = vehicleUseCase.getAllVehicles(model, status, priceMin, priceMax, subscription, pageable);
        return ResponseEntity.ok(vehicles.map(vehicleMapper::toResponse));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleRequest req) {
        Vehicle vehicle = vehicleMapper.toDomain(req);
        Vehicle updated = vehicleUseCase.updateVehicle(id, vehicle);
        return ResponseEntity.ok(vehicleMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        vehicleUseCase.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
