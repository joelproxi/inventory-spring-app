package com.dealersautocenter.inventory.infrastructure.web.controller;

import com.dealersautocenter.inventory.application.port.in.DealerUseCase;
import com.dealersautocenter.inventory.domain.model.Dealer;
import com.dealersautocenter.inventory.infrastructure.web.dto.CreateDealerRequest;
import com.dealersautocenter.inventory.infrastructure.web.dto.DealerResponse;
import com.dealersautocenter.inventory.infrastructure.web.dto.UpdateDealerRequest;
import com.dealersautocenter.inventory.infrastructure.web.mapper.DealerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
@Tag(name = "Dealers", description = "Operations related to dealers")
public class DealerController {

    private final DealerUseCase dealerUseCase;
    private final DealerMapper dealerMapper;

    @PostMapping
    public ResponseEntity<DealerResponse> create(@Valid @RequestBody CreateDealerRequest req) {
        Dealer dealer = dealerMapper.toDomain(req);
        Dealer saved = dealerUseCase.createDealer(dealer);
        return ResponseEntity.status(HttpStatus.CREATED).body(dealerMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerResponse> getById(@PathVariable UUID id) {
        Dealer dealer = dealerUseCase.getDealerById(id);
        return ResponseEntity.ok(dealerMapper.toResponse(dealer));
    }

    @GetMapping
    public ResponseEntity<Page<DealerResponse>> getAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        Page<Dealer> dealers = dealerUseCase.getAllDealers(pageable);
        return ResponseEntity.ok(dealers.map(dealerMapper::toResponse));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DealerResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDealerRequest req) {
        Dealer dealer = dealerMapper.toDomain(req);
        Dealer updated = dealerUseCase.updateDealer(id, dealer);
        return ResponseEntity.ok(dealerMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        dealerUseCase.deleteDealer(id);
        return ResponseEntity.noContent().build();
    }
}
