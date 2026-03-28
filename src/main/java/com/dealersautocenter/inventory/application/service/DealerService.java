package com.dealersautocenter.inventory.application.service;

import com.dealersautocenter.inventory.application.port.in.DealerUseCase;
import com.dealersautocenter.inventory.application.port.out.DealerRepositoryPort;
import com.dealersautocenter.inventory.application.port.out.TenantPort;
import com.dealersautocenter.inventory.domain.exception.CrossTenantAccessException;
import com.dealersautocenter.inventory.domain.exception.ResourceNotFoundException;
import com.dealersautocenter.inventory.domain.model.Dealer;
import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DealerService implements DealerUseCase {

    private final DealerRepositoryPort dealerRepositoryPort;
    private final TenantPort tenantPort;

    public DealerService(DealerRepositoryPort dealerRepositoryPort, TenantPort tenantPort) {
        this.dealerRepositoryPort = dealerRepositoryPort;
        this.tenantPort = tenantPort;
    }

    @Override
    public Dealer createDealer(Dealer dealer) {
        String tenantId = tenantPort.getTenantId();
        dealer.setTenantId(tenantId);
        return dealerRepositoryPort.save(dealer);
    }

    @Override
    public Dealer getDealerById(UUID id) {
        String tenantId = tenantPort.getTenantId();
        return dealerRepositoryPort.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Dealer not found: " + id));
    }

    @Override
    public Page<Dealer> getAllDealers(Pageable pageable) {
        String tenantId = tenantPort.getTenantId();
        return dealerRepositoryPort.findAllByTenantId(tenantId, pageable);
    }

    @Override
    public Dealer updateDealer(UUID id, Dealer updatedDealer) {
        String tenantId = tenantPort.getTenantId();
        Dealer existing = dealerRepositoryPort.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Dealer not found: " + id));

        if (updatedDealer.getName() != null) existing.setName(updatedDealer.getName());
        if (updatedDealer.getEmail() != null) existing.setEmail(updatedDealer.getEmail());
        if (updatedDealer.getSubscriptionType() != null) existing.setSubscriptionType(updatedDealer.getSubscriptionType());

        return dealerRepositoryPort.save(existing);
    }

    @Override
    public void deleteDealer(UUID id) {
        String tenantId = tenantPort.getTenantId();
        if (!dealerRepositoryPort.existsByIdAndTenantId(id, tenantId)) {
            throw new ResourceNotFoundException("Dealer not found: " + id);
        }
        dealerRepositoryPort.deleteByIdAndTenantId(id, tenantId);
    }

    @Override
    public Dealer getDealerEntity(UUID dealerId) {
        String tenantId = tenantPort.getTenantId();
        return dealerRepositoryPort.findByIdAndTenantId(dealerId, tenantId)
                .orElseThrow(() -> {
                    if (dealerRepositoryPort.existsById(dealerId)) {
                        return new CrossTenantAccessException("Cross-tenant access blocked for dealer: " + dealerId);
                    }
                    return new ResourceNotFoundException("Dealer not found: " + dealerId);
                });
    }

    @Override
    public Map<String, Long> countBySubscription() {
        List<Object[]> rows = dealerRepositoryPort.countBySubscriptionTypeGlobal();
        Map<String, Long> result = new LinkedHashMap<>();
        result.put("BASIC", 0L);
        result.put("PREMIUM", 0L);
        for (Object[] row : rows) {
            SubscriptionType type = (SubscriptionType) row[0];
            Long count = (Long) row[1];
            result.put(type.name(), count);
        }
        return result;
    }
}
