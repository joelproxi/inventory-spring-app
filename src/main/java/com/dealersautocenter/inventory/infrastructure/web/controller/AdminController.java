package com.dealersautocenter.inventory.infrastructure.web.controller;

import com.dealersautocenter.inventory.application.port.in.DealerUseCase;
import com.dealersautocenter.inventory.infrastructure.security.RequireRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DealerUseCase dealerUseCase;

    @GetMapping("/stats/subscriptions")
    @RequireRole("GLOBAL_ADMIN")
    public ResponseEntity<Map<String, Long>> getSubscriptionStats() {
        return ResponseEntity.ok(dealerUseCase.countBySubscription());
    }
}
