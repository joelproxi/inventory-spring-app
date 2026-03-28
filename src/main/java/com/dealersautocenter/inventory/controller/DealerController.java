package com.dealersautocenter.inventory.controller;

import com.dealersautocenter.inventory.dto.*;
import com.dealersautocenter.inventory.service.DealerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;

    @PostMapping
    public ResponseEntity<DealerResponse> create(
            @Valid @RequestBody CreateDealerRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dealerService.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(dealerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<DealerResponse>> getAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(dealerService.getAll(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DealerResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDealerRequest req) {
        return ResponseEntity.ok(dealerService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        dealerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
