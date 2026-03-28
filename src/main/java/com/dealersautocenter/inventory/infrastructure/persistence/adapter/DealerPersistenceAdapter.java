package com.dealersautocenter.inventory.infrastructure.persistence.adapter;

import com.dealersautocenter.inventory.application.port.out.DealerRepositoryPort;
import com.dealersautocenter.inventory.domain.model.Dealer;
import com.dealersautocenter.inventory.infrastructure.persistence.entity.DealerJpaEntity;
import com.dealersautocenter.inventory.infrastructure.persistence.mapper.DealerPersistenceMapper;
import com.dealersautocenter.inventory.infrastructure.persistence.repository.SpringDataDealerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DealerPersistenceAdapter implements DealerRepositoryPort {

    private final SpringDataDealerRepository repository;
    private final DealerPersistenceMapper mapper;

    public DealerPersistenceAdapter(SpringDataDealerRepository repository, DealerPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Dealer save(Dealer dealer) {
        DealerJpaEntity entity = mapper.toJpaEntity(dealer);
        DealerJpaEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId) {
        return repository.findByIdAndTenantId(id, tenantId).map(mapper::toDomain);
    }

    @Override
    public Page<Dealer> findAllByTenantId(String tenantId, Pageable pageable) {
        return repository.findAllByTenantId(tenantId, pageable).map(mapper::toDomain);
    }

    @Override
    public boolean existsByIdAndTenantId(UUID id, String tenantId) {
        return repository.existsByIdAndTenantId(id, tenantId);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteByIdAndTenantId(UUID id, String tenantId) {
        repository.deleteByIdAndTenantId(id, tenantId);
    }

    @Override
    public List<Object[]> countBySubscriptionTypeGlobal() {
        return repository.countBySubscriptionTypeGlobal();
    }
}
