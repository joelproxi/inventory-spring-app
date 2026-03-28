package com.dealersautocenter.inventory.application.port.in;

import com.dealersautocenter.inventory.domain.model.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface DealerUseCase {
    Dealer createDealer(Dealer dealer);
    Dealer getDealerById(UUID id);
    Page<Dealer> getAllDealers(Pageable pageable);
    Dealer updateDealer(UUID id, Dealer dealer);
    void deleteDealer(UUID id);
    Dealer getDealerEntity(UUID dealerId);
    Map<String, Long> countBySubscription();
}
