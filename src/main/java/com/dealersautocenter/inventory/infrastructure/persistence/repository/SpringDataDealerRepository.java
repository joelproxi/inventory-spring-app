package com.dealersautocenter.inventory.infrastructure.persistence.repository;

import com.dealersautocenter.inventory.infrastructure.persistence.entity.DealerJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataDealerRepository extends JpaRepository<DealerJpaEntity, UUID> {
    Optional<DealerJpaEntity> findByIdAndTenantId(UUID id, String tenantId);
    Page<DealerJpaEntity> findAllByTenantId(String tenantId, Pageable pageable);
    boolean existsByIdAndTenantId(UUID id, String tenantId);
    void deleteByIdAndTenantId(UUID id, String tenantId);
    
    @Query("SELECT d.subscriptionType, COUNT(d) FROM DealerJpaEntity d GROUP BY d.subscriptionType")
    List<Object[]> countBySubscriptionTypeGlobal();
}
