package com.dealersautocenter.inventory.repository;

import com.dealersautocenter.inventory.domain.entity.Vehicle;
import com.dealersautocenter.inventory.domain.enums.VehicleStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class VehicleSpecification {

    private VehicleSpecification() {
    }

    public static Specification<Vehicle> withFilters(String tenantId,
            String model,
            VehicleStatus status,
            BigDecimal priceMin,
            BigDecimal priceMax) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("tenantId"), tenantId));

            if (model != null && !model.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("model")),
                        "%" + model.toLowerCase() + "%"));
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