package com.dealersautocenter.inventory.infrastructure.persistence.repository;

import com.dealersautocenter.inventory.domain.enums.SubscriptionType;
import com.dealersautocenter.inventory.infrastructure.persistence.entity.VehicleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataVehicleRepository extends JpaRepository<VehicleJpaEntity, UUID>, JpaSpecificationExecutor<VehicleJpaEntity> {
    Optional<VehicleJpaEntity> findByIdAndTenantId(UUID id, String tenantId);
    boolean existsByIdAndTenantId(UUID id, String tenantId);
    void deleteByIdAndTenantId(UUID id, String tenantId);

    @Query("""
           SELECT v FROM VehicleJpaEntity v JOIN v.dealer d
           WHERE v.tenantId = :tenantId
             AND d.subscriptionType = :subType
           """)
    Page<VehicleJpaEntity> findByTenantIdAndDealerSubscriptionType(
            @Param("tenantId") String tenantId,
            @Param("subType") SubscriptionType subType,
            Pageable pageable);
}
