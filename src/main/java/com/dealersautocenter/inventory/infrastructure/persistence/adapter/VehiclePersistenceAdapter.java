package com.dealersautocenter.inventory.infrastructure.persistence.adapter;

import com.dealersautocenter.inventory.application.port.out.VehicleRepositoryPort;
import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import com.dealersautocenter.inventory.domain.model.Vehicle;
import com.dealersautocenter.inventory.infrastructure.persistence.entity.VehicleJpaEntity;
import com.dealersautocenter.inventory.infrastructure.persistence.mapper.VehiclePersistenceMapper;
import com.dealersautocenter.inventory.infrastructure.persistence.repository.SpringDataVehicleRepository;
import com.dealersautocenter.inventory.infrastructure.persistence.repository.VehicleJpaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class VehiclePersistenceAdapter implements VehicleRepositoryPort {

    private final SpringDataVehicleRepository repository;
    private final VehiclePersistenceMapper mapper;

    public VehiclePersistenceAdapter(SpringDataVehicleRepository repository, VehiclePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleJpaEntity entity = mapper.toJpaEntity(vehicle);
        VehicleJpaEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Vehicle> findByIdAndTenantId(UUID id, String tenantId) {
        return repository.findByIdAndTenantId(id, tenantId).map(mapper::toDomain);
    }

    @Override
    public boolean existsByIdAndTenantId(UUID id, String tenantId) {
        return repository.existsByIdAndTenantId(id, tenantId);
    }

    @Override
    public void deleteByIdAndTenantId(UUID id, String tenantId) {
        repository.deleteByIdAndTenantId(id, tenantId);
    }

    @Override
    public Page<Vehicle> findByTenantIdAndDealerSubscriptionType(String tenantId, SubscriptionType subType, Pageable pageable) {
        return repository.findByTenantIdAndDealerSubscriptionType(tenantId, subType, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Vehicle> findAllWithFilters(String tenantId, String model, VehicleStatus status, BigDecimal priceMin, BigDecimal priceMax, Pageable pageable) {
        Specification<VehicleJpaEntity> spec = VehicleJpaSpecification.withFilters(tenantId, model, status, priceMin, priceMax);
        return repository.findAll(spec, pageable).map(mapper::toDomain);
    }
}
