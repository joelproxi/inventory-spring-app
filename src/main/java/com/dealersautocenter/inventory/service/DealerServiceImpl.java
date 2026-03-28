package com.dealersautocenter.inventory.service;

import com.dealersautocenter.inventory.domain.entity.Dealer;
import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.dto.*;
import com.dealersautocenter.inventory.exception.CrossTenantAccessException;
import com.dealersautocenter.inventory.exception.ResourceNotFoundException;
import com.dealersautocenter.inventory.mapper.DealerMapper;
import com.dealersautocenter.inventory.repository.DealerRepository;
import com.dealersautocenter.inventory.security.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DealerServiceImpl implements DealerService {

    private final DealerRepository dealerRepository;
    private final DealerMapper dealerMapper;

    public DealerResponse create(CreateDealerRequest req) {
        String tenantId = TenantContext.getTenantId();

        Dealer dealer = Dealer.builder()
                .tenantId(tenantId)
                .name(req.getName())
                .email(req.getEmail())
                .subscriptionType(req.getSubscriptionType())
                .build();

        return dealerMapper.toResponse(dealerRepository.save(dealer));
    }

    @Transactional(readOnly = true)
    public DealerResponse getById(UUID id) {
        String tenantId = TenantContext.getTenantId();
        Dealer dealer = dealerRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dealer not found: " + id));
        return dealerMapper.toResponse(dealer);
    }

    @Transactional(readOnly = true)
    public Page<DealerResponse> getAll(Pageable pageable) {
        String tenantId = TenantContext.getTenantId();
        return dealerRepository.findAllByTenantId(tenantId, pageable)
                .map(dealerMapper::toResponse);
    }

    public DealerResponse update(UUID id, UpdateDealerRequest req) {
        String tenantId = TenantContext.getTenantId();

        Dealer dealer = dealerRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dealer not found: " + id));

        if (req.getName() != null)
            dealer.setName(req.getName());
        if (req.getEmail() != null)
            dealer.setEmail(req.getEmail());
        if (req.getSubscriptionType() != null)
            dealer.setSubscriptionType(req.getSubscriptionType());

        return dealerMapper.toResponse(dealerRepository.save(dealer));
    }

    public void delete(UUID id) {
        String tenantId = TenantContext.getTenantId();
        if (!dealerRepository.existsByIdAndTenantId(id, tenantId)) {
            throw new ResourceNotFoundException("Dealer not found: " + id);
        }
        dealerRepository.deleteByIdAndTenantId(id, tenantId);
    }

    /**
     * Retrieves a Dealer while verifying the tenant.
     * Used by VehicleService when creating a vehicle.
     */
    @Transactional(readOnly = true)
    public Dealer getDealerEntity(UUID dealerId, String tenantId) {
        return dealerRepository.findByIdAndTenantId(dealerId, tenantId)
                .orElseThrow(() -> {
                    if (dealerRepository.existsById(dealerId)) {
                        return new CrossTenantAccessException(
                                "Cross-tenant access blocked for dealer: " + dealerId);
                    }
                    return new ResourceNotFoundException(
                            "Dealer not found: " + dealerId);
                });
    }

    /**
     * Global count (all tenants) by subscription type.
     * Reserved for GLOBAL_ADMIN.
     */
    @Transactional(readOnly = true)
    public Map<String, Long> countBySubscription() {
        List<Object[]> rows = dealerRepository.countBySubscriptionTypeGlobal();
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
