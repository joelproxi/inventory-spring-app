package com.dealersautocenter.inventory.application.port.out;

import com.dealersautocenter.inventory.domain.model.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface DealerRepositoryPort {
    Dealer save(Dealer dealer);
    Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId);
    Page<Dealer> findAllByTenantId(String tenantId, Pageable pageable);
    boolean existsByIdAndTenantId(UUID id, String tenantId);
    boolean existsById(UUID id);
    void deleteByIdAndTenantId(UUID id, String tenantId);
    List<Object[]> countBySubscriptionTypeGlobal();
}
