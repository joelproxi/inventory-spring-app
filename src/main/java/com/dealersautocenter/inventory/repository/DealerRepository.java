package com.dealersautocenter.inventory.repository;

import com.dealersautocenter.inventory.domain.entity.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, UUID> {

    Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId);

    Page<Dealer> findAllByTenantId(String tenantId, Pageable pageable);

    boolean existsByIdAndTenantId(UUID id, String tenantId);

    void deleteByIdAndTenantId(UUID id, String tenantId);

    @Query("SELECT d.subscriptionType, COUNT(d) FROM Dealer d GROUP BY d.subscriptionType")
    List<Object[]> countBySubscriptionTypeGlobal();
}