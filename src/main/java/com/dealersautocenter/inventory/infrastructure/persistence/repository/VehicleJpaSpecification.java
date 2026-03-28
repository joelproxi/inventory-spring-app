package com.dealersautocenter.inventory.infrastructure.persistence.repository;

import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import com.dealersautocenter.inventory.infrastructure.persistence.entity.VehicleJpaEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class VehicleJpaSpecification {

    private VehicleJpaSpecification() {}

    public static Specification<VehicleJpaEntity> withFilters(String tenantId,
                                                              String model,
                                                              VehicleStatus status,
                                                              BigDecimal priceMin,
                                                              BigDecimal priceMax) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("tenantId"), tenantId));

            if (model != null && !model.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (priceMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), priceMin));
            }
            if (priceMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), priceMax));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
