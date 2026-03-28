package com.dealersautocenter.inventory.service;

import com.dealersautocenter.inventory.domain.entity.Dealer;
import com.dealersautocenter.inventory.dto.CreateDealerRequest;
import com.dealersautocenter.inventory.dto.DealerResponse;
import com.dealersautocenter.inventory.dto.UpdateDealerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface DealerService {
    DealerResponse create(CreateDealerRequest req);
    DealerResponse getById(UUID id);
    Page<DealerResponse> getAll(Pageable pageable);
    DealerResponse update(UUID id, UpdateDealerRequest req);
    void delete(UUID id);
    Dealer getDealerEntity(UUID dealerId, String tenantId);
    Map<String, Long> countBySubscription();
}
