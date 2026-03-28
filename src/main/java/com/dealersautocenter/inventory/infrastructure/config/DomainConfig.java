package com.dealersautocenter.inventory.infrastructure.config;

import com.dealersautocenter.inventory.application.port.in.DealerUseCase;
import com.dealersautocenter.inventory.application.port.in.VehicleUseCase;
import com.dealersautocenter.inventory.application.port.out.DealerRepositoryPort;
import com.dealersautocenter.inventory.application.port.out.TenantPort;
import com.dealersautocenter.inventory.application.port.out.VehicleRepositoryPort;
import com.dealersautocenter.inventory.application.service.DealerService;
import com.dealersautocenter.inventory.application.service.VehicleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public DealerUseCase dealerUseCase(DealerRepositoryPort dealerRepositoryPort, TenantPort tenantPort) {
        return new DealerService(dealerRepositoryPort, tenantPort);
    }

    @Bean
    public VehicleUseCase vehicleUseCase(VehicleRepositoryPort vehicleRepositoryPort, DealerUseCase dealerUseCase, TenantPort tenantPort) {
        return new VehicleService(vehicleRepositoryPort, dealerUseCase, tenantPort);
    }
}
