package com.dealersautocenter.inventory.controller;

import com.dealersautocenter.inventory.security.RequireRole;
import com.dealersautocenter.inventory.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Admin endpoints — GLOBAL_ADMIN only.
 *
 * GET /admin/dealers/countBySubscription
 * Returns an OVERALL (all tenants) count of dealers grouped by subscription type.
 * This endpoint is for system administrators who need a global overview.
 * The X-Tenant-Id header is still required by the filter but the count
 * is NOT scoped to a single tenant.
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DealerService dealerService;

    @GetMapping("/dealers/countBySubscription")
    @RequireRole("GLOBAL_ADMIN")
    public ResponseEntity<Map<String, Long>> countBySubscription() {
        return ResponseEntity.ok(dealerService.countBySubscription());
    }
}
